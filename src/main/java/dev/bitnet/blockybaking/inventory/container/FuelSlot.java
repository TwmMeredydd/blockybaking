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

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.common.ForgeHooks;

public class FuelSlot extends Slot {
    public FuelSlot(IInventory inv, int index, int x, int y) {
        super(inv, index, x, y);
    }

    public boolean mayPlace(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack) > 0 || isBucket(stack);
    }

    public int getMaxStackSize(ItemStack stack) {
        return isBucket(stack) ? 1 : super.getMaxStackSize(stack);
    }

    public static boolean isBucket(ItemStack p_178173_0_) {
        return p_178173_0_.getItem() == Items.BUCKET;
    }
}
