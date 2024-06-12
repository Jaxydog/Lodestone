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

import java.util.Objects;
import java.util.function.Consumer;

/**
 * A possible loader environment.
 *
 * @param <T> The environment's interface.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
public final class LoaderEnvironment<T extends Loaded> {

    /**
     * The associated {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    private final Class<? extends T> type;
    /**
     * A consumer method that loads the value.
     *
     * @since 1.0.0
     */
    private final Consumer<? super T> load;

    /**
     * Creates a new {@link LoaderEnvironment}.
     *
     * @param type The associated {@link Loaded} interface.
     * @param load A consumer method that loads the value.
     *
     * @throws NullPointerException If the given type of loading method are null.
     * @since 1.0.0
     */
    public LoaderEnvironment(Class<? extends T> type, Consumer<? super T> load) throws NullPointerException {
        this.type = Objects.requireNonNull(type);
        this.load = Objects.requireNonNull(load);
    }

    /**
     * Returns the associated {@link Loaded} interface.
     *
     * @return The associated {@link Loaded} interface.
     *
     * @since 1.0.0
     */
    public Class<? extends T> getInterface() {
        return this.type;
    }

    /**
     * Returns whether the associated {@link Loaded} interface is bundled with Lodestone.
     *
     * @return Whether the associated {@link Loaded} interface is bundled with Lodestone.
     *
     * @since 1.3.0
     */
    public boolean isBundled() {
        return this.type.isAnnotationPresent(BundledLoader.class);
    }

    /**
     * Loads the given value.
     *
     * @param value The value to load.
     *
     * @since 1.0.0
     */
    public void loadValue(T value) {
        this.load.accept(value);
    }

}
