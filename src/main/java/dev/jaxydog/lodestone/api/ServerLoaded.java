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

/**
 * A value that should be loaded at runtime.
 * <p>
 * The provided {@link #loadServer()} method is only run on the server. If you only want the client or both, see
 * {@link ClientLoaded} and {@link CommonLoaded}.
 *
 * @author Jaxydog
 * @see CommonLoaded
 * @see ClientLoaded
 * @since 1.0.0
 */
public interface ServerLoaded extends Loaded {

    /**
     * Loads this value at runtime on the server.
     *
     * @since 1.0.0
     */
    void loadServer();

}
