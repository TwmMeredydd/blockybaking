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
import dev.bitnet.blockybaking.block.IronBowlBlock;
import dev.bitnet.blockybaking.block.StandMixerBlock;
import dev.bitnet.blockybaking.util.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    private static final Item.Properties BASE_BLOCK_ITEM_PROPERTIES = new Item.Properties().tab(ModSetup.itemGroup);

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BlockyBaking.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BlockyBaking.MODID);

    public static final RegistryObject<IronBowlBlock> IRON_BOWL = BLOCKS.register("iron_bowl", IronBowlBlock::new);
    public static final RegistryObject<StandMixerBlock> RED_STAND_MIXER = BLOCKS.register("red_stand_mixer", () -> new StandMixerBlock(DyeColor.RED));

    public static final RegistryObject<BlockItem> IRON_BOWL_ITEM = BLOCK_ITEMS.register("iron_bowl", () -> new BlockItem(IRON_BOWL.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> RED_STAND_MIXER_ITEM = BLOCK_ITEMS.register("red_stand_mixer", () -> new BlockItem(RED_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
}
