package io.github.entityplantt.kredstone.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public interface IPowerableBlock {
	abstract int powerNeeds(World world, BlockPos pos, BlockState state, Direction fromDirection);

	abstract void getPowered(World world, BlockPos pos, BlockState state, Direction fromDirection);
}