/*
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * Copyright © 2024 Jaxydog
 *
 * This file is part of Lodestone.
 *
 * Lodestone is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Lodestone is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Lodestone. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.jaxydog.lodestone.impl;

import com.google.common.collect.ImmutableSet;
import dev.jaxydog.lodestone.api.Loaded;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Retains information about all registered loader environments.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
public final class LoaderEnvironmentRegistry {

    /**
     * The environment registry's logger.
     *
     * @since 1.1.0
     */
    private static final Logger LOGGER = LoggerFactory.getLogger("Lodestone/Registry");
    /**
     * Tracks whether Lodestone has finished initializing bundled environments and should forbid new bundled interfaces
     * from being loaded.
     *
     * @since 1.6.0
     */
    public static final AtomicBoolean FORBID_BUNDLED = new AtomicBoolean(false);

    /**
     * The environment's entries mapped to their associated {@link Loaded} interfaces.
     *
     * @since 1.0.0
     */
    private final Map<Class<? extends Loaded>, Entry<? extends Loaded>> entries;

    /**
     * Creates a new {@link LoaderEnvironmentRegistry}.
     *
     * @param map The inner map.
     *
     * @since 1.0.0
     */
    private LoaderEnvironmentRegistry(Map<Class<? extends Loaded>, Entry<? extends Loaded>> map) {
        this.entries = map;
    }

    /**
     * Creates a new, empty {@link LoaderEnvironmentRegistry} instance.
     *
     * @return A new {@link LoaderEnvironmentRegistry} instance.
     *
     * @since 1.0.0
     */
    public static LoaderEnvironmentRegistry create() {
        return new LoaderEnvironmentRegistry(new Object2ObjectOpenHashMap<>());
    }

    /**
     * Registers the given {@link LoaderEnvironment}.
     *
     * @param environment The environment instance.
     * @param <T> The type of the associated {@link Loaded} interface.
     *
     * @throws IllegalArgumentException If the given environment's associated {@link Loaded} interface has already been
     * registered, or if the given environment's associated {@link Loaded} type is not an interface. This may also be
     * thrown when attempting to register a new "bundled" environment.
     * @throws NullPointerException If the given environment is null.
     * @since 1.0.0
     */
    public <T extends Loaded> void register(LoaderEnvironment<T> environment) throws IllegalArgumentException, NullPointerException {
        final Class<? extends T> type = Objects.requireNonNull(environment).getInterface();

        if (!type.isInterface()) {
            throw new IllegalArgumentException("The environment's associated type should be an interface");
        } else if (this.entries.containsKey(type)) {
            throw new IllegalArgumentException("An environment has already been registered for '%s'".formatted(type.getSimpleName()));
        }

        this.entries.put(type, new Entry<>(environment));

        if (environment.isBundled()) {
            if (FORBID_BUNDLED.get()) {
                throw new IllegalArgumentException("The environment must not be using a bundled interface.");
            }

            LOGGER.debug("Added bundled loader environment: {}", type.getSimpleName());
        } else {
            LOGGER.debug("Added modded loader environment: {}", type.getSimpleName());
        }
    }

    /**
     * Returns whether a {@link LoaderEnvironment} for the given {@link Loaded} interface was previously registered.
     *
     * @param type The expected {@link Loaded} interface.
     * @param <T> The type of the associated {@link Loaded} interface.
     *
     * @return Whether a {@link LoaderEnvironment} was previously registered.
     *
     * @since 1.0.0
     */
    public <T extends Loaded> boolean has(Class<? extends T> type) {
        return this.entries.containsKey(type);
    }

    /**
     * Returns a set containing all registered interface instances.
     *
     * @return A set containing all registered interface instances.
     *
     * @since 0.1.0
     */
    public Set<Class<? extends Loaded>> getInterfaces() {
        return ImmutableSet.copyOf(this.entries.keySet());
    }

    /**
     * Adds an entrypoint to the {@link LoaderEnvironmentRegistry} instance associated with the given {@link Loaded}
     * interface.
     *
     * @param type The expected {@link Loaded} interface.
     * @param entrypoint The entrypoint.
     * @param <T> The type of the associated {@link Loaded} interface.
     *
     * @throws IllegalArgumentException If the given {@link Loaded} interface does not have a registered
     * {@link LoaderEnvironment}.
     * @throws NullPointerException If the given entrypoint is null.
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public <T extends Loaded> void addEntrypoint(
        Class<? extends T> type, T entrypoint
    ) throws IllegalArgumentException, NullPointerException {
        Objects.requireNonNull(entrypoint);

        if (this.has(type)) {
            final String modId = entrypoint.getLoaderId().getNamespace();
            final Set<T> set = (Set<T>) this.entries.get(type)
                .entrypoints()
                .computeIfAbsent(modId, i -> new ObjectArraySet<>());

            set.add(entrypoint);
        } else {
            throw new IllegalArgumentException("An environment has not been registered for '%s'".formatted(type.getSimpleName()));
        }
    }

    /**
     * Loads the {@link LoaderEnvironment} instance associated with the given {@link Loaded} interface.
     * <p>
     * If the defined {@link LoaderEnvironment#loadValue(Loaded)} method throws, the thrown error will be bubbled up.
     *
     * @param type The expected {@link Loaded} interface.
     * @param modId The loaded mod's identifier.
     * @param <T> The type of the associated {@link Loaded} interface.
     *
     * @throws IllegalArgumentException If the given {@link Loaded} interface does not have a registered
     * {@link LoaderEnvironment}.
     * @since 1.0.0
     */
    public <T extends Loaded> void loadEntrypoints(
        Class<? extends T> type, String modId
    ) throws IllegalArgumentException {
        if (this.has(type)) {
            this.entries.get(type).loadEntrypoints(modId);

            LOGGER.debug("Loaded all '{}' entrypoints for '{}'", type.getSimpleName(), modId);
        } else {
            throw new IllegalArgumentException("An environment has not been registered for '%s'".formatted(type.getSimpleName()));
        }
    }

    /**
     * A loader environment entry.
     *
     * @param environment The environment instance.
     * @param entrypoints The environment's registered entrypoints.
     * @param <T> The type of the associated {@link Loaded} interface.
     *
     * @author Jaxydog
     * @since 1.0.0
     */
    private record Entry<T extends Loaded>(LoaderEnvironment<T> environment, Map<String, Set<T>> entrypoints) {

        /**
         * Creates a new, empty {@link Entry}.
         *
         * @param environment The environment instance.
         *
         * @since 1.0.0
         */
        public Entry(LoaderEnvironment<T> environment) {
            this(environment, new Object2ObjectOpenHashMap<>());
        }

        /**
         * Loads all associated entrypoints for the given mod identifier.
         * <p>
         * If the defined {@link LoaderEnvironment#loadValue(Loaded)} method throws, the error will be bubbled up.
         *
         * @param modId The loaded mod's identifier.
         *
         * @since 1.0.0
         */
        public void loadEntrypoints(String modId) {
            if (!this.entrypoints().containsKey(modId)) return;

            for (final T entrypoint : this.entrypoints().get(modId)) {
                this.environment().loadValue(entrypoint);
            }

            this.entrypoints().get(modId).clear();
        }

    }

}
