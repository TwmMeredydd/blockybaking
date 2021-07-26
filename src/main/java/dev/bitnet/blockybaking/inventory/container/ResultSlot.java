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

package dev.bitnet.blockybaking.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ResultSlot extends Slot {
    private int removeCount;

    public ResultSlot(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }

        return super.remove(amount);
    }

    public ItemStack onTake(PlayerEntity player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
        return stack;
    }

    protected void onQuickCraft(ItemStack p_75210_1_, int p_75210_2_) {
        this.removeCount += p_75210_2_;
    }
}