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

package dev.jaxydog.lodestone.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that informs automatic loaders to skip the target value.
 * <p>
 * If the array returned by {@link #value()} is empty, it is assumed that the annotated value should be skipped within
 * all environments.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface IgnoreLoading {

    /**
     * The {@link Loaded} interfaces to ignore.
     *
     * @return The {@link Loaded} interfaces to ignore.
     *
     * @since 1.0.0
     */
    Class<? extends Loaded>[] value();

}
