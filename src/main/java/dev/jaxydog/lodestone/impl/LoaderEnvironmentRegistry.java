/*
 * SPDX-License-Identifier: AGPL-3.0-or-later
 *
 * Copyright © 2024 Jaxydog
 *
 * This file is part of Lodestone.
 *
 * Lodestone is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * Lodestone is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License along with Lodestone. If not, see <https://www.gnu.org/licenses/>.
 */

package dev.jaxydog.lodestone.impl;

import dev.jaxydog.lodestone.api.Loaded;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Retains information about all registered loader environments.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
@Internal
public final class LoaderEnvironmentRegistry {

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
     * registered, or if the given environment's associated {@link Loaded} type is not an interface.
     * @since 1.0.0
     */
    public <T extends Loaded> void register(LoaderEnvironment<T> environment) throws IllegalArgumentException {
        final Class<? extends T> type = environment.getInterface();

        if (!type.isInterface()) {
            throw new IllegalArgumentException("The environment's associated type should be an interface");
        } else if (this.entries.containsKey(type)) {
            throw new IllegalArgumentException("An environment has already been registered for '%s'".formatted(type.getSimpleName()));
        }

        this.entries.put(type, new Entry<>(environment));
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
     * Returns the {@link LoaderEnvironment} instance associated with the given {@link Loaded} interface, if it was
     * previously registered.
     *
     * @param type The expected {@link Loaded} interface.
     * @param <T> The type of the associated {@link Loaded} interface.
     *
     * @return The {@link LoaderEnvironment} instance, if it exists.
     *
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    public <T extends Loaded> Optional<LoaderEnvironment<T>> get(Class<? extends T> type) {
        if (!this.has(type)) return Optional.empty();

        return Optional.of((LoaderEnvironment<T>) this.entries.get(type).environment());
    }

    /**
     * Loads the {@link LoaderEnvironment} instance associated with the given {@link Loaded} interface.
     * <p>
     * If the defined {@link LoaderEnvironment#loadValue(Loaded)} method throws, the thrown error will be bubbled up.
     *
     * @param type The expected {@link Loaded} interface.
     * @param <T> The type of the associated {@link Loaded} interface.
     *
     * @throws IllegalArgumentException If the given {@link Loaded} interface does not have a registered
     * {@link LoaderEnvironment}.
     * @since 1.0.0
     */
    public <T extends Loaded> void load(Class<? extends T> type) throws IllegalArgumentException {
        if (this.has(type)) {
            this.entries.get(type).loadEntrypoints();
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
    private record Entry<T extends Loaded>(LoaderEnvironment<T> environment, Set<T> entrypoints) {

        /**
         * Creates a new, empty {@link Entry}.
         *
         * @param environment The environment instance.
         *
         * @since 1.0.0
         */
        public Entry(LoaderEnvironment<T> environment) {
            this(environment, new ObjectArraySet<>());
        }

        /**
         * Loads all associated entrypoints.
         * <p>
         * If the defined {@link LoaderEnvironment#loadValue(Loaded)} method throws, the error will be bubbled up.
         *
         * @since 1.0.0
         */
        public void loadEntrypoints() {
            for (final T entrypoint : this.entrypoints()) {
                this.environment().loadValue(entrypoint);
            }
        }

    }

}
