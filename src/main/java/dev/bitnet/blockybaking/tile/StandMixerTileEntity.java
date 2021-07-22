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

import dev.bitnet.blockybaking.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class StandMixerTileEntity extends LockableTileEntity implements ITickableTileEntity {
    protected NonNullList<ItemStack> items = NonNullList.withSize(12, ItemStack.EMPTY);
    private int mixingProgress;
    private int mixingTotalTime;
    private int powerStore;

    public final IIntArray dataAccess = new IIntArray() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0: return StandMixerTileEntity.this.mixingProgress;
                case 1: return StandMixerTileEntity.this.mixingTotalTime;
                case 2: return StandMixerTileEntity.this.powerStore;
                default: return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: StandMixerTileEntity.this.mixingProgress = value;
                case 1: StandMixerTileEntity.this.mixingTotalTime = value;
                case 2: StandMixerTileEntity.this.powerStore = value;
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

    }

    @Override
    public void load(BlockState state, CompoundNBT compoundNBT) {
        super.load(state, compoundNBT);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compoundNBT, this.items);
        this.mixingProgress = compoundNBT.getInt("MixingProgress");
        this.mixingTotalTime = compoundNBT.getInt("MixingTotalTime");
        this.powerStore = compoundNBT.getInt("PowerStore");
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        super.save(compoundNBT);
        compoundNBT.putInt("MixingProgress", this.mixingProgress);
        compoundNBT.putInt("MixingTotalTime", this.mixingTotalTime);
        compoundNBT.putInt("PowerStore", this.mixingTotalTime);
        ItemStackHelper.saveAllItems(compoundNBT, this.items);

        return compoundNBT;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.blockybaking.stand_mixer");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return null;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
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
}
