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

import net.minecraft.util.Identifier;

/**
 * A value that should be loaded at runtime.
 * <p>
 * This interface is typically not used directly. You most likely want one of its sub-interfaces.
 *
 * @author Jaxydog
 * @see CommonLoaded
 * @see ClientLoaded
 * @see ServerLoaded
 * @see DataGenerating
 * @since 1.0.0
 */
public interface Loaded {

    /**
     * Returns this value's associated identifier, typically used during the automatic loading process.
     *
     * @return This value's associated identifier.
     *
     * @since 1.0.0
     */
    Identifier getLoaderId();

}
