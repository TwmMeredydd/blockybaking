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

package dev.bitnet.blockybaking.block;

import dev.bitnet.blockybaking.tile.StandMixerTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class StandMixerBlock extends ContainerBlock {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public static final BooleanProperty LIT = AbstractFurnaceBlock.LIT;

    private static final VoxelShape BASE = VoxelShapes.or(
            Block.box(0,0,0,16,1,9),
            Block.box(2, 0, 9, 14, 1, 16)
    );

    private static final VoxelShape BACK = Block.box(5, 1, 0, 11, 10, 4);
    private static final VoxelShape ARM = Block.box(5, 10, 0, 11, 14, 14);
    private static final VoxelShape DIAL = Block.box(11, 6, 1, 11.75, 8, 3);
    private static final VoxelShape BOWL = VoxelShapes.or(
            Block.box(4, 1, 6, 12, 3, 14),
            Block.box(2, 3, 4, 14, 6.5, 16)
    );

    public StandMixerBlock(DyeColor color) {
        super(Properties.of(Material.METAL, color));

        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }


    private static final VoxelShape NORTH = VoxelShapes.or(BASE, BACK, ARM, DIAL);

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        super.createBlockStateDefinition(stateBuilder);
        stateBuilder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext useContext) {
        return super.getStateForPlacement(useContext).setValue(FACING, useContext.getHorizontalDirection()).setValue(LIT, false);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return rotateShape(VoxelShapes.or(NORTH, BOWL), Direction.NORTH, state.getValue(FACING));
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader state) {
        return new StandMixerTileEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (world.isClientSide) {
            return ActionResultType.SUCCESS;
        } else {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof StandMixerTileEntity) {
                player.openMenu((INamedContainerProvider)tileentity);
            }
            return ActionResultType.CONSUME;
        }
    }

    public static VoxelShape rotateShape(final VoxelShape shape, final Direction from, final Direction to) {
        if (from.getAxis() == Direction.Axis.Y || to.getAxis() == Direction.Axis.Y) {
            throw new IllegalArgumentException("Invalid Direction!");
        }
        if (from == to) {
            return shape;
        }

        final VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};

        final int times = (to.get2DDataValue() - from.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1], VoxelShapes.create(new AxisAlignedBB(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        return buffer[0];
    }
}
