/*
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * Copyright © 2025 Jaxydog
 *
 * This file is part of Lodestone.
 *
 * Lodestone is free software: you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * Lodestone is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with Lodestone. If not, see
 * <https://www.gnu.org/licenses/>.
 */

package dev.jaxydog.lodestone.api;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * A generic class that wraps a value of type {@code T}, providing a simple interface for applying arbitrary load
 * methods to generic values. This is typically used alongside an {@link AutoLoader} class.
 * <p>
 * The inner value can be retrieved using the {@link #getValue()} method.
 *
 * @param <T> The type of the value stored within this class.
 *
 * @author Jaxydog
 * @since 1.8.0
 */
public class AutoLoaded<T>
    implements Loaded
{

    /**
     * The loader identifier.
     *
     * @since 1.8.0
     */
    private final Identifier loaderId;
    /**
     * The inner value.
     *
     * @since 1.8.0
     */
    private final T value;

    /**
     * The inner list of loading methods.
     *
     * @since 1.8.0
     */
    protected final Map<Class<? extends Loaded>, List<LoadMethod<T>>> loadMethods = new Object2ObjectOpenHashMap<>();

    /**
     * Creates a new {@link AutoLoaded} value.
     *
     * @param loaderId The loader {@link Identifier}.
     * @param value The inner value.
     *
     * @throws NullPointerException If the given value is null.
     * @since 1.8.0
     */
    public AutoLoaded(Identifier loaderId, T value)
        throws NullPointerException
    {
        this.loaderId = loaderId;
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns this wrapper's inner value.
     *
     * @return The inner value.
     *
     * @since 1.8.0
     */
    public final T getValue() {
        return this.value;
    }

    /**
     * Adds a method that will be run within the specified {@link Loaded} interface.
     *
     * @param type The {@link Loaded} interface.
     * @param method The method to run.
     *
     * @return This value.
     *
     * @throws NullPointerException If either the {@link Loaded} interface or the given method are null.
     * @since 1.8.0
     */
    public final AutoLoaded<T> on(Class<? extends Loaded> type, LoadMethod<T> method)
        throws NullPointerException
    {
        final List<LoadMethod<T>> methods = this.loadMethods.computeIfAbsent(type, ignored -> new ObjectArrayList<>(1));

        methods.add(Objects.requireNonNull(method));

        return this;
    }

    /**
     * Returns the methods assigned to the given {@link Loaded} interface.
     *
     * @param type The {@link Loaded} interface for which to return methods.
     *
     * @return An optional list of methods.
     *
     * @since 1.8.0
     */
    final Optional<List<LoadMethod<T>>> getLoadMethods(Class<? extends Loaded> type) {
        return Optional.ofNullable(this.loadMethods.get(type));
    }

    /**
     * Returns the methods assigned to the given {@link Loaded} interface, bound to this {@link AutoLoaded} wrapper.
     *
     * @param type The {@link Loaded} interface for which to return methods.
     *
     * @return An optional list of methods.
     *
     * @since 1.8.0
     */
    final Optional<List<Runnable>> getBoundLoadMethods(Class<? extends Loaded> type) {
        return this.getLoadMethods(type).map(list -> list.stream().map(method -> method.bind(this)).toList());
    }

    @Override
    public final Identifier getLoaderId() {
        return this.loaderId;
    }

    /**
     * A method run during loading, used with a {@link AutoLoaded} wrapper.
     *
     * @param <T> The type of the wrapped value.
     *
     * @since 1.8.0
     */
    @FunctionalInterface
    public interface LoadMethod<T> {

        /**
         * Runs loading logic.
         *
         * @param self The wrapper instance.
         *
         * @since 1.8.0
         */
        void load(AutoLoaded<T> self);

        /**
         * Binds a wrapper to this method so that it may be called without passing it by value.
         *
         * @param wrapper The wrapper to bind.
         *
         * @return An aliased runnable.
         */
        default Runnable bind(AutoLoaded<T> wrapper) {
            return () -> this.load(wrapper);
        }

    }

}
