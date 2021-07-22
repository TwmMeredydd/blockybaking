/*
 * Copyright (c) BitNet 2021
 *
 * This file is part of Blocky Baking.
 *
 *     Blocky Baking is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Foobar is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Blocky Baking.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.bitnet.blockybaking.init;

import dev.bitnet.blockybaking.BlockyBaking;
import dev.bitnet.blockybaking.tile.StandMixerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntityTypes {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, BlockyBaking.MODID);

    public static final RegistryObject<TileEntityType<StandMixerTileEntity>> STAND_MIXER = TILE_ENTITY_TYPES.register("stand_mixer", () -> TileEntityType.Builder.of(StandMixerTileEntity::new, ModBlocks.WHITE_STAND_MIXER.get(), ModBlocks.ORANGE_STAND_MIXER.get(), ModBlocks.MAGENTA_STAND_MIXER.get(), ModBlocks.LIGHT_BLUE_STAND_MIXER.get(), ModBlocks.YELLOW_STAND_MIXER.get(), ModBlocks.LIME_STAND_MIXER.get(), ModBlocks.PINK_STAND_MIXER.get(), ModBlocks.GRAY_STAND_MIXER.get(), ModBlocks.LIGHT_GRAY_STAND_MIXER.get(), ModBlocks.CYAN_STAND_MIXER.get(), ModBlocks.PURPLE_STAND_MIXER.get(), ModBlocks.BLUE_STAND_MIXER.get(), ModBlocks.BROWN_STAND_MIXER.get(), ModBlocks.GREEN_STAND_MIXER.get(), ModBlocks.RED_STAND_MIXER.get(), ModBlocks.GRAY_STAND_MIXER.get()).build(null));
}
