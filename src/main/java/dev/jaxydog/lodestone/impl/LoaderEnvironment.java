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
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.function.Consumer;

/**
 * A possible loader environment.
 *
 * @param <T> The environment's interface.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
@Internal
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
     * @since 1.0.0
     */
    public LoaderEnvironment(Class<? extends T> type, Consumer<? super T> load) {
        this.type = type;
        this.load = load;
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
