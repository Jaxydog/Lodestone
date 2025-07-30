/*
 * SPDX-License-Identifier: LGPL-3.0-or-later
 *
 * Copyright © 2024 Jaxydog
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

import dev.jaxydog.lodestone.impl.BundledLoader;

/**
 * A value that should be loaded during data generation.
 * <p>
 * The provided {@link #generate()} method is run on the server environment during data generation.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
@BundledLoader
public interface DataGenerating
    extends Loaded
{

    /**
     * Loads this value at runtime.
     *
     * @since 1.0.0
     */
    void generate();

}
