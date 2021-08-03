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

package dev.bitnet.blockybaking.crafting;

import com.google.gson.JsonObject;
import dev.bitnet.blockybaking.init.ModRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class DisabledRecipe implements IRecipe<IInventory> {
    private final ResourceLocation id;

    public DisabledRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(IInventory inventory, World world) {
        return false;
    }

    @Override
    public ItemStack assemble(IInventory inventory) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.DISABLED_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.DISABLED;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<DisabledRecipe> {

        @Override
        public DisabledRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            return new DisabledRecipe(id);
        }

        @Nullable
        @Override
        public DisabledRecipe fromNetwork(ResourceLocation id, PacketBuffer packetBuffer) {
            return new DisabledRecipe(id);
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, DisabledRecipe recipe) {

        }
    }
}
