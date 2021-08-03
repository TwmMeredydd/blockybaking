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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.bitnet.blockybaking.BlockyBaking;
import dev.bitnet.blockybaking.init.ModRecipes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MixingRecipe implements IRecipe<IInventory> {
    private final ResourceLocation id;
    private final String group;
    private final NonNullList<Ingredient> ingredients;
    private final ItemStack result;
    private final int mixingTime;

    public MixingRecipe(ResourceLocation id, String group, NonNullList<Ingredient> ingredients, ItemStack result, int mixingTime) {
        this.id = id;
        this.group = group;
        this.ingredients = ingredients;
        this.result = result;
        this.mixingTime = mixingTime;
    }


    @Override
    public boolean matches(IInventory inventory, World world) {
        RecipeItemHelper recipeItemHelper = new RecipeItemHelper();
        List<ItemStack> ingredientList = new ArrayList<>();
        int i = 0;

        for (int j=0; j < inventory.getContainerSize(); ++j) {
            ItemStack itemStack = inventory.getItem(j);
            if (!itemStack.isEmpty()) {
                ++i;
                ingredientList.add(itemStack);
            }
        }

        return i == this.ingredients.size() && RecipeMatcher.findMatches(ingredientList, this.ingredients) != null;
    }

    @Override
    public ItemStack assemble(IInventory inventory) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.result;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    public int getMixingTime() {
        return this.mixingTime;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipes.MIXING_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.MIXING;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<MixingRecipe> {
        private static final ResourceLocation NAME = new ResourceLocation(BlockyBaking.MODID, "mixing");

        @Override
        public MixingRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            String group = JSONUtils.getAsString(jsonObject, "group", "");
            NonNullList<Ingredient> ingredients = itemsFromJson(JSONUtils.getAsJsonArray(jsonObject, "ingredients"));
            int mixingTime = JSONUtils.getAsInt(jsonObject, "mixingtime");

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for mixing recipe");
            } else if (ingredients.size() > 7) {
                throw new JsonParseException("Too many items for mixing recipe - the max is 7");
            } else {
                ItemStack result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(jsonObject, "result"));
                return new MixingRecipe(id, group, ingredients, result, mixingTime);
            }
        }

        private static NonNullList<Ingredient> itemsFromJson(JsonArray jsonArray) {
            NonNullList<Ingredient> ingredients = NonNullList.create();

            for(int i = 0; i < jsonArray.size(); ++i) {
                Ingredient ingredient = Ingredient.fromJson(jsonArray.get(i));
                if (!ingredient.isEmpty()) {
                    ingredients.add(ingredient);
                }
            }

            return ingredients;
        }

        @Nullable
        @Override
        public MixingRecipe fromNetwork(ResourceLocation id, PacketBuffer packetBuffer) {
            String group = packetBuffer.readUtf(32767);
            int i = packetBuffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(i, Ingredient.EMPTY);

            for(int j = 0; j < ingredients.size(); ++j) {
                ingredients.set(j, Ingredient.fromNetwork(packetBuffer));
            }

            ItemStack result = packetBuffer.readItem();
            int mixingTime = packetBuffer.readInt();
            return new MixingRecipe(id, group, ingredients, result,  mixingTime);
        }

        @Override
        public void toNetwork(PacketBuffer packetBuffer, MixingRecipe recipe) {
            packetBuffer.writeUtf(recipe.group);
            packetBuffer.writeVarInt(recipe.ingredients.size());

            for(Ingredient ingredient : recipe.ingredients) {
                ingredient.toNetwork(packetBuffer);
            }

            packetBuffer.writeItem(recipe.result);
            packetBuffer.writeInt(recipe.mixingTime);
        }
    }
}
