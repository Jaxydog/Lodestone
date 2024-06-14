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

import dev.jaxydog.lodestone.api.CommonLoaded;
import dev.jaxydog.lodestone.api.Loaded;
import net.fabricmc.api.ModInitializer;

public class LodestoneTest implements ModInitializer {

    public static final String MOD_ID = "lodestone-test";
    public static final RootLoader LOADER = new RootLoader();

    @Override
    public void onInitialize() {
        Lodestone.createEnvironment(TestLoaded.class, TestLoaded::loadTest);

        LOADER.register();

        Lodestone.load(CommonLoaded.class, MOD_ID);
        Lodestone.load(TestLoaded.class, MOD_ID);
    }

    interface TestLoaded extends Loaded {

        void loadTest();

    }

}
