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

package dev.bitnet.blockybaking.tile;

import dev.bitnet.blockybaking.crafting.MixingRecipe;
import dev.bitnet.blockybaking.init.ModRecipes;
import dev.bitnet.blockybaking.init.ModTileEntityTypes;
import dev.bitnet.blockybaking.inventory.container.StandMixerContainer;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

public class StandMixerTileEntity extends LockableTileEntity implements ITickableTileEntity {
    protected NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private int mixingProgress;
    private int mixingTotalTime;
    private int litTime;
    private int litDuration;

    public final IIntArray dataAccess = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return StandMixerTileEntity.this.mixingProgress;
                case 1: return StandMixerTileEntity.this.mixingTotalTime;
                case 2: return StandMixerTileEntity.this.litTime;
                case 3: return StandMixerTileEntity.this.litDuration;
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: StandMixerTileEntity.this.mixingProgress = value;
                case 1: StandMixerTileEntity.this.mixingTotalTime = value;
                case 2: StandMixerTileEntity.this.litTime = value;
                case 3: StandMixerTileEntity.this.litDuration = value;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    public StandMixerTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public StandMixerTileEntity() {
        this(ModTileEntityTypes.STAND_MIXER.get());
    }

    @Override
    public void tick() {
        if (this.isLit()) {
            --this.litTime;
        }

        if (this.level.isClientSide) {
            ItemStack fuelSlotContents = this.items.get(7);
            if (this.isLit() || !fuelSlotContents.isEmpty() && !this.craftingSlotsEmpty()) {
                IRecipe<?> recipe = this.level.getRecipeManager().getRecipeFor(ModRecipes.MIXING, this, this.level).orElse(null);
                if (!this.isLit() && this.canBurn(recipe)) {
                    this.litTime = this.litDuration = ForgeHooks.getBurnTime(fuelSlotContents, ModRecipes.MIXING);
                    if (this.isLit()) {
                        if (fuelSlotContents.hasContainerItem())
                            this.items.set(7, fuelSlotContents.getContainerItem());
                        else if (!fuelSlotContents.isEmpty()) {
                            fuelSlotContents.shrink(1);
                            if (fuelSlotContents.isEmpty()) {
                                this.items.set(7, ItemStack.EMPTY);
                            }
                        }
                    }
                }

                if (this.isLit() && this.canBurn(recipe)) {
                    ++this.mixingProgress;
                    if (this.mixingProgress == this.mixingTotalTime) {
                        this.mixingProgress = 0;
                        this.mixingTotalTime = this.level.getRecipeManager().getRecipeFor(ModRecipes.MIXING, this, this.level).map(MixingRecipe::getMixingTime).orElse(200);

                        if (recipe != null && this.canBurn(recipe)) {
                            NonNullList<ItemStack> inputSlotContents = this.getInputSlotContents();
                            ItemStack recipeResult = ((MixingRecipe)recipe).assemble(this);
                            ItemStack resultSlotContents = this.items.get(8);

                            if (resultSlotContents.isEmpty()) {
                                this.items.set(8, recipeResult);
                            } else if (resultSlotContents.sameItem(recipeResult)) {
                                resultSlotContents.grow(recipeResult.getCount());
                            }

                            for (ItemStack slotContents : inputSlotContents) {
                                slotContents.shrink(1);
                            }
                        }
                    }
                } else {
                    this.mixingProgress = 0;
                }
            } else if (!this.isLit() && this.mixingProgress > 0) {
                this.mixingProgress = MathHelper.clamp(this.mixingProgress - 2, 0, this.mixingTotalTime);
            }
        }
    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        super.load(state, compoundNBT);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compoundNBT, this.items);
        this.mixingProgress = compoundNBT.getInt("MixingProgress");
        this.mixingTotalTime = compoundNBT.getInt("MixingTotalTime");
        this.litTime = compoundNBT.getInt("LitTime");
        this.litDuration = compoundNBT.getInt("LitDuration");
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.putInt("MixingProgress", this.mixingProgress);
        compoundNBT.putInt("MixingTotalTime", this.mixingTotalTime);
        compoundNBT.putInt("LitTime", this.litTime);
        compoundNBT.putInt("LitDuration", this.litDuration);
        ItemStackHelper.saveAllItems(compoundNBT, this.items);

        return compoundNBT;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.blockybaking.stand_mixer");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new StandMixerContainer(id, playerInventory, this,  this.dataAccess);
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.items) {
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int index) {
        return this.items.get(index);
    }

    @Override
    public ItemStack removeItem(int index, int amount) {
        return ItemStackHelper.removeItem(this.items, index, amount);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return ItemStackHelper.takeItem(this.items, index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        this.items.set(index, stack);
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    public NonNullList<ItemStack> getInputSlotContents() {
        NonNullList<ItemStack> list = NonNullList.withSize(7, ItemStack.EMPTY);

        for (int i = 0; i < 7; i++) {
            list.set(i, this.items.get(i));
        }

        return list;
    }

    public boolean craftingSlotsEmpty() {
        NonNullList<ItemStack> list = this.getInputSlotContents();

        for (ItemStack itemStack : list) {
            if (!itemStack.isEmpty()) return false;
        }

        return true;
    }

    public boolean isLit() {
        return this.litTime > 0;
    }

    private boolean canBurn(@Nullable IRecipe<?> recipe) {
        if (!this.craftingSlotsEmpty() && recipe != null) {
            ItemStack recipeResult = ((MixingRecipe)recipe).assemble(this);
            if (recipeResult.isEmpty()) return false;
            else {
                ItemStack resultSlotContents = this.items.get(8);
                if (resultSlotContents.isEmpty())
                    return true;
                else if (!resultSlotContents.sameItem(recipeResult))
                    return false;
                else if (resultSlotContents.getCount() + recipeResult.getCount() <= this.getMaxStackSize() && resultSlotContents.getCount() + recipeResult.getCount() <= resultSlotContents.getMaxStackSize())
                    return true;
                else
                    return recipeResult.getCount() + resultSlotContents.getCount() <= recipeResult.getMaxStackSize();
            }
        } else return false;
    }
}
