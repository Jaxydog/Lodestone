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

import dev.jaxydog.lodestone.api.*;
import dev.jaxydog.lodestone.impl.LoaderEnvironment;
import dev.jaxydog.lodestone.impl.LoaderEnvironmentRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Lodestone's common entrypoint.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
public class Lodestone implements ModInitializer {

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
    static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    /**
     * The mod's loader environment registry instance.
     *
     * @since 1.0.0
     */
    private static final LoaderEnvironmentRegistry REGISTRY = LoaderEnvironmentRegistry.create();
    /**
     * A map containing queues of to-be-registered loader entrypoints.
     * <p>
     * This is only used during mod initialization, in cases where an entrypoint is registered before its associated
     * environment is.
     *
     * @since 1.0.0
     */
    private static final Map<Class<? extends Loaded>, Queue<? extends Loaded>> QUEUE = new Object2ObjectOpenHashMap<>();
    /**
     * Tracks whether the mod has finished loading all environment instances.
     *
     * @since 1.0.0
     */
    private static final AtomicBoolean LOADED_ENVIRONMENTS = new AtomicBoolean(false);

    /**
     * Registers a value for automatic registration.
     *
     * @param type The {@link Loaded} interface.
     * @param value The value to be registered.
     * @param queueIfMissing Whether values should be queued if the environment is missing.
     * @param <T> The type of the {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    private static <T extends Loaded> void register(Class<? extends T> type, T value, boolean queueIfMissing) {
        if (!queueIfMissing || REGISTRY.has(type)) {
            REGISTRY.addEntrypoint(type, value);
        } else {
            ((Queue<T>) QUEUE.computeIfAbsent(type, t -> new ArrayDeque<T>(1))).add(value);
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
        final boolean queueIfMissing = !LOADED_ENVIRONMENTS.get();

        for (final T value : values) register(type, value, queueIfMissing);
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
        final boolean queueIfMissing = !LOADED_ENVIRONMENTS.get();

        values.forEachRemaining(value -> register(type, value, queueIfMissing));
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
        final boolean queueIfMissing = !LOADED_ENVIRONMENTS.get();

        values.forEach(value -> register(type, value, queueIfMissing));
    }

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
        REGISTRY.register(new LoaderEnvironment<>(type, load));
    }

    @Override
    public void onInitialize() {
        createEnvironment(CommonLoaded.class, CommonLoaded::loadCommon);

        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            createEnvironment(ClientLoaded.class, ClientLoaded::loadClient);
        } else {
            createEnvironment(ServerLoaded.class, ServerLoaded::loadServer);

            if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
                createEnvironment(DataGenerating.class, DataGenerating::generate);
            }
        }

        LOADED_ENVIRONMENTS.set(true);

        QUEUE.forEach(Lodestone::register);
    }

}
