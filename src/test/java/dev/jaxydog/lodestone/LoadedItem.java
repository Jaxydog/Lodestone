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

package dev.jaxydog.lodestone;

import dev.jaxydog.lodestone.LodestoneTest.TestLoaded;
import dev.jaxydog.lodestone.api.CommonLoaded;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class LoadedItem extends Item implements CommonLoaded, TestLoaded {

    private final String path;

    public LoadedItem(String path, Settings settings) {
        super(settings);

        this.path = path;
    }

    @Override
    public Identifier getLoaderId() {
        return Identifier.of(LodestoneTest.MOD_ID, this.path);
    }

    @Override
    public void loadCommon() {
        Registry.register(Registries.ITEM, this.getLoaderId(), this);

        Lodestone.LOGGER.info("Registered '{}'", this.getLoaderId());
    }

    @Override
    public void loadTest() {
        Lodestone.LOGGER.info("Test print from '{}'", this.getLoaderId());
    }

}
