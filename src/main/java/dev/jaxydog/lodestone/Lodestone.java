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

package dev.jaxydog.lodestone;

import dev.jaxydog.lodestone.api.Loaded;
import dev.jaxydog.lodestone.impl.LoaderEnvironment;
import dev.jaxydog.lodestone.impl.LoaderEnvironmentRegistry;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Lodestone's common entrypoint.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
public final class Lodestone implements ModInitializer {

    /**
     * Lodestone's mod identifier.
     *
     * @since 1.0.0
     */
    static final String MOD_ID = "lodestone";
    /**
     * Lodestone's primary logging instance.
     *
     * @since 1.0.0
     */
    static final Logger LOGGER = LoggerFactory.getLogger("Lodestone");

    /**
     * The mod's loader environment registry instance.
     *
     * @since 1.0.0
     */
    private static final LoaderEnvironmentRegistry REGISTRY = LoaderEnvironmentRegistry.create();

    /**
     * Creates and registers a new environment for the given {@link Loaded} interface.
     * <p>
     * This is used to implement custom {@link Loaded} interfaces.
     *
     * @param type The {@link Loaded} interface.
     * @param load A method that loads the given value.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    public static <T extends Loaded> void createEnvironment(Class<? extends T> type, Consumer<? super T> load) {
        try {
            REGISTRY.register(new LoaderEnvironment<>(type, load));
        } catch (IllegalArgumentException exception) {
            LOGGER.error(exception.getLocalizedMessage());
        } catch (NullPointerException exception) {
            LOGGER.error("Attempted to register a null environment");
        }
    }

    /**
     * Returns a set containing all registered interface instances.
     *
     * @return A set containing all registered interface instances.
     *
     * @since 0.1.0
     */
    @Internal
    public static Set<Class<? extends Loaded>> getInterfaces() {
        return REGISTRY.getInterfaces();
    }

    /**
     * Registers a value for automatic registration.
     *
     * @param type The {@link Loaded} interface.
     * @param value The value to be registered.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    public static <T extends Loaded> void register(Class<? extends T> type, T value) {
        try {
            REGISTRY.addEntrypoint(type, value);
        } catch (IllegalArgumentException exception) {
            LOGGER.error(exception.getLocalizedMessage());
        } catch (NullPointerException exception) {
            LOGGER.error("Attempted to register a null value");
        }
    }

    /**
     * Registers the given values for automatic registration.
     *
     * @param type The {@link Loaded} interface.
     * @param values The values to be registered.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    @SafeVarargs
    public static <T extends Loaded> void register(Class<? extends T> type, T... values) {
        for (final T value : values) register(type, value);
    }

    /**
     * Registers the given values for automatic registration.
     *
     * @param type The {@link Loaded} interface.
     * @param values The values to be registered.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    public static <T extends Loaded> void register(Class<? extends T> type, Iterator<? extends T> values) {
        values.forEachRemaining(value -> register(type, value));
    }

    /**
     * Registers the given values for automatic registration.
     *
     * @param type The {@link Loaded} interface.
     * @param values The values to be registered.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    public static <T extends Loaded> void register(Class<? extends T> type, Collection<? extends T> values) {
        values.forEach(value -> register(type, value));
    }

    /**
     * Loads the target environment for the given mod identifier.
     *
     * @param type The {@link Loaded} interface.
     * @param modId The requesting mod's identifier.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    public static <T extends Loaded> void load(Class<? extends T> type, String modId) {
        try {
            REGISTRY.loadEntrypoints(type, modId);
        } catch (IllegalArgumentException exception) {
            LOGGER.error(exception.getLocalizedMessage());
        }
    }

    /**
     * Loads the target environment for the given mod identifier.
     *
     * @param type The {@link Loaded} interface.
     * @param modIds The requesting mod's identifiers.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @throws IllegalArgumentException If the given list of mod identifiers is empty.
     * @since 1.0.0
     */
    public static <T extends Loaded> void load(
        Class<? extends T> type, String... modIds
    ) throws IllegalArgumentException {
        if (modIds.length == 0) {
            throw new IllegalArgumentException("At least one mod identifier must be supplied during loading");
        }

        for (final String modId : modIds) load(type, modId);
    }

    /**
     * Loads the target environment for the given mod identifier.
     *
     * @param type The {@link Loaded} interface.
     * @param modIds The requesting mod's identifiers.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @throws IllegalArgumentException If the given list of mod identifiers is empty.
     * @since 1.0.0
     */
    public static <T extends Loaded> void load(
        Class<? extends T> type, Iterator<String> modIds
    ) throws IllegalArgumentException {
        if (!modIds.hasNext()) {
            throw new IllegalArgumentException("At least one mod identifier must be supplied during loading");
        }

        modIds.forEachRemaining(modId -> load(type, modId));
    }

    /**
     * Loads the target environment for the given mod identifier.
     *
     * @param type The {@link Loaded} interface.
     * @param modIds The requesting mod's identifiers.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @throws IllegalArgumentException If the given list of mod identifiers is empty.
     * @since 1.0.0
     */
    public static <T extends Loaded> void load(
        Class<? extends T> type, Collection<String> modIds
    ) throws IllegalArgumentException {
        if (modIds.isEmpty()) {
            throw new IllegalArgumentException("At least one mod identifier must be supplied during loading");
        }

        modIds.forEach(modId -> load(type, modId));
    }

    @Override
    public void onInitialize() {
        final Set<Class<? extends Loaded>> environments = getInterfaces();
        final String list = String.join(", ", environments.stream().map(Class::getSimpleName).toList());

        if (environments.isEmpty()) {
            throw new IllegalStateException("Lodestone initialized without any environments");
        } else {
            LOGGER.info("Lodestone fully initialized with {} interfaces: {}", environments.size(), list);
        }
    }

}
