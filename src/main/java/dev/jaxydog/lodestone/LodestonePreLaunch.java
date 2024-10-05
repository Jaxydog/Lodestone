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

import dev.jaxydog.lodestone.api.ClientLoaded;
import dev.jaxydog.lodestone.api.CommonLoaded;
import dev.jaxydog.lodestone.api.DataGenerating;
import dev.jaxydog.lodestone.api.ServerLoaded;
import dev.jaxydog.lodestone.impl.LoaderEnvironmentRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.jetbrains.annotations.ApiStatus.Internal;

/**
 * Lodestone's pre-launch entrypoint.
 * <p>
 * This is used to register the basic environments before other mods typically load. This ensures that all registered
 * values are registered properly, as other mods should not be registering content in the pre-launch stage.
 *
 * @author Jaxydog
 * @since 1.0.0
 */
@Internal
public final class LodestonePreLaunch implements PreLaunchEntrypoint {

    /**
     * Creates a new instance of this entrypoint.
     *
     * @since 1.5.3
     */
    public LodestonePreLaunch() { }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void onPreLaunch() {
        Lodestone.LOGGER.info("Initializing basic Lodestone environments");

        Lodestone.createEnvironment(CommonLoaded.class, CommonLoaded::loadCommon);

        if (FabricLoader.getInstance().getEnvironmentType().equals(EnvType.CLIENT)) {
            Lodestone.createEnvironment(ClientLoaded.class, ClientLoaded::loadClient);
        } else {
            Lodestone.createEnvironment(ServerLoaded.class, ServerLoaded::loadServer);

            if (FabricDataGenHelper.ENABLED) {
                Lodestone.createEnvironment(DataGenerating.class, DataGenerating::generate);
            }
        }

        LoaderEnvironmentRegistry.FORBID_BUNDLED.set(true);
    }

}
