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

package dev.jaxydog.lodestone.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An annotation that determines the loading priority of the annotated value.
 * <p>
 * Higher values are loaded sooner. If the annotated value is a class, all instances will inherit its priority unless
 * specified by another {@link LoadingPriority} annotation.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface LoadingPriority {

    /**
     * The default loading priority.
     *
     * @since 1.0.0
     */
    int DEFAULT = 0;

    /**
     * The value's loading priority.
     *
     * @return The value's loading priority.
     *
     * @since 1.0.0
     */
    int value();

}
