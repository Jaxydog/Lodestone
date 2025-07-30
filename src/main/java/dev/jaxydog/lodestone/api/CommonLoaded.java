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
 * A value that should be loaded at runtime.
 * <p>
 * The provided {@link #loadCommon()} method is run on both the client <i>and</i> server. If you only want one or the
 * other, see {@link ClientLoaded} and {@link ServerLoaded}.
 *
 * @author Jaxydog
 * @see ClientLoaded
 * @see ServerLoaded
 * @since 1.0.0
 */
@BundledLoader
public interface CommonLoaded
    extends Loaded
{

    /**
     * Loads this value at runtime.
     *
     * @since 1.0.0
     */
    void loadCommon();

}
