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

import dev.bitnet.blockybaking.init.ModContainerTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class StandMixerContainer extends Container {
    private final IInventory container;
    private final IIntArray data;

    public StandMixerContainer(int id, PlayerInventory playerInv) {
        this(id, playerInv, new Inventory(9), new IntArray(4));
    }

    public StandMixerContainer(int id, PlayerInventory playerInv, IInventory inv, IIntArray data) {
        super(ModContainerTypes.STAND_MIXER.get(), id);
        this.container = inv;
        this.data = data;

        this.addSlot(new Slot(this.container, 0, 52, 77));
        this.addSlot(new Slot(this.container, 1, 70, 77));
        this.addSlot(new Slot(this.container, 2, 88, 77));
        this.addSlot(new Slot(this.container, 3, 106, 77));
        this.addSlot(new Slot(this.container, 4, 61, 95));
        this.addSlot(new Slot(this.container, 5, 79, 95));
        this.addSlot(new Slot(this.container, 6, 97, 95));
        this.addSlot(new FuelSlot(this.container, 7, 15, 101));
        this.addSlot(new ResultSlot(this.container, 8, 152, 101));

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInv, j1 + l * 9 + 9, 8 + j1 * 18, 144 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInv, i1, 8 + i1 * 18, 202));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.container.stillValid(player);
    }
}
