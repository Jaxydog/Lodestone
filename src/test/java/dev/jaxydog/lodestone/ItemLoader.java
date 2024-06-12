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

package dev.jaxydog.lodestone;

import dev.jaxydog.lodestone.LodestoneTest.TestLoaded;
import dev.jaxydog.lodestone.api.AutoLoader;
import dev.jaxydog.lodestone.api.IgnoreLoading;
import dev.jaxydog.lodestone.api.LoadingPriority;
import net.minecraft.item.Item.Settings;
import net.minecraft.util.Identifier;

public final class ItemLoader extends AutoLoader {

    @LoadingPriority(-1)
    public static final LoadedItem ITEM_1 = new LoadedItem("item_1", new Settings());
    public static final LoadedItem ITEM_2 = new LoadedItem("item_2", new Settings());
    @LoadingPriority(1)
    @IgnoreLoading({ TestLoaded.class })
    public static final LoadedItem ITEM_3 = new LoadedItem("item_3", new Settings());

    @Override
    public Identifier getLoaderId() {
        return Identifier.of(LodestoneTest.MOD_ID, "item");
    }

}
