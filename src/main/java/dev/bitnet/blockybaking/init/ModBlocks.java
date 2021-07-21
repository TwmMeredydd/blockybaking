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

    public static final RegistryObject<StandMixerBlock> WHITE_STAND_MIXER = BLOCKS.register("white_stand_mixer", () -> new StandMixerBlock(DyeColor.WHITE));
    public static final RegistryObject<StandMixerBlock> ORANGE_STAND_MIXER = BLOCKS.register("orange_stand_mixer", () -> new StandMixerBlock(DyeColor.ORANGE));
    public static final RegistryObject<StandMixerBlock> MAGENTA_STAND_MIXER = BLOCKS.register("magenta_stand_mixer", () -> new StandMixerBlock(DyeColor.MAGENTA));
    public static final RegistryObject<StandMixerBlock> LIGHT_BLUE_STAND_MIXER = BLOCKS.register("light_blue_stand_mixer", () -> new StandMixerBlock(DyeColor.LIGHT_BLUE));
    public static final RegistryObject<StandMixerBlock> YELLOW_STAND_MIXER = BLOCKS.register("yellow_stand_mixer", () -> new StandMixerBlock(DyeColor.YELLOW));
    public static final RegistryObject<StandMixerBlock> LIME_STAND_MIXER = BLOCKS.register("lime_stand_mixer", () -> new StandMixerBlock(DyeColor.LIME));
    public static final RegistryObject<StandMixerBlock> PINK_STAND_MIXER = BLOCKS.register("pink_stand_mixer", () -> new StandMixerBlock(DyeColor.PINK));
    public static final RegistryObject<StandMixerBlock> GRAY_STAND_MIXER = BLOCKS.register("gray_stand_mixer", () -> new StandMixerBlock(DyeColor.GRAY));
    public static final RegistryObject<StandMixerBlock> LIGHT_GRAY_STAND_MIXER = BLOCKS.register("light_gray_stand_mixer", () -> new StandMixerBlock(DyeColor.LIGHT_GRAY));
    public static final RegistryObject<StandMixerBlock> CYAN_STAND_MIXER = BLOCKS.register("cyan_stand_mixer", () -> new StandMixerBlock(DyeColor.CYAN));
    public static final RegistryObject<StandMixerBlock> PURPLE_STAND_MIXER = BLOCKS.register("purple_stand_mixer", () -> new StandMixerBlock(DyeColor.PURPLE));
    public static final RegistryObject<StandMixerBlock> BLUE_STAND_MIXER = BLOCKS.register("blue_stand_mixer", () -> new StandMixerBlock(DyeColor.BLUE));
    public static final RegistryObject<StandMixerBlock> BROWN_STAND_MIXER = BLOCKS.register("brown_stand_mixer", () -> new StandMixerBlock(DyeColor.BROWN));
    public static final RegistryObject<StandMixerBlock> GREEN_STAND_MIXER = BLOCKS.register("green_stand_mixer", () -> new StandMixerBlock(DyeColor.GREEN));
    public static final RegistryObject<StandMixerBlock> RED_STAND_MIXER = BLOCKS.register("red_stand_mixer", () -> new StandMixerBlock(DyeColor.RED));
    public static final RegistryObject<StandMixerBlock> BLACK_STAND_MIXER = BLOCKS.register("black_stand_mixer", () -> new StandMixerBlock(DyeColor.BLACK));

    public static final RegistryObject<BlockItem> WHITE_STAND_MIXER_ITEM = BLOCK_ITEMS.register("white_stand_mixer", () -> new BlockItem(WHITE_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> ORANGE_STAND_MIXER_ITEM = BLOCK_ITEMS.register("orange_stand_mixer", () -> new BlockItem(ORANGE_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> MAGENTA_STAND_MIXER_ITEM = BLOCK_ITEMS.register("magenta_stand_mixer", () -> new BlockItem(MAGENTA_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> LIGHT_BLUE_STAND_MIXER_ITEM = BLOCK_ITEMS.register("light_blue_stand_mixer", () -> new BlockItem(LIGHT_BLUE_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> YELLOW_STAND_MIXER_ITEM = BLOCK_ITEMS.register("yellow_stand_mixer", () -> new BlockItem(YELLOW_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> LIME_STAND_MIXER_ITEM = BLOCK_ITEMS.register("lime_stand_mixer", () -> new BlockItem(LIME_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> PINK_STAND_MIXER_ITEM = BLOCK_ITEMS.register("pink_stand_mixer", () -> new BlockItem(PINK_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> GRAY_STAND_MIXER_ITEM = BLOCK_ITEMS.register("gray_stand_mixer", () -> new BlockItem(GRAY_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> LIGHT_GRAY_STAND_MIXER_ITEM = BLOCK_ITEMS.register("light_gray_stand_mixer", () -> new BlockItem(LIGHT_GRAY_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> CYAN_STAND_MIXER_ITEM = BLOCK_ITEMS.register("cyan_stand_mixer", () -> new BlockItem(CYAN_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> PURPLE_STAND_MIXER_ITEM = BLOCK_ITEMS.register("purple_stand_mixer", () -> new BlockItem(PURPLE_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> BLUE_STAND_MIXER_ITEM = BLOCK_ITEMS.register("blue_stand_mixer", () -> new BlockItem(BLUE_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> BROWN_STAND_MIXER_ITEM = BLOCK_ITEMS.register("brown_stand_mixer", () -> new BlockItem(BROWN_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> GREEN_STAND_MIXER_ITEM = BLOCK_ITEMS.register("green_stand_mixer", () -> new BlockItem(GREEN_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> RED_STAND_MIXER_ITEM = BLOCK_ITEMS.register("red_stand_mixer", () -> new BlockItem(RED_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
    public static final RegistryObject<BlockItem> BLACK_STAND_MIXER_ITEM = BLOCK_ITEMS.register("black_stand_mixer", () -> new BlockItem(BLACK_STAND_MIXER.get(), BASE_BLOCK_ITEM_PROPERTIES));
}
