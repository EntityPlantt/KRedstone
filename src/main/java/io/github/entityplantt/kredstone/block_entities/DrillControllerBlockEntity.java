package io.github.entityplantt.kredstone.block_entities;

import io.github.entityplantt.kredstone.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class DrillControllerBlockEntity extends BlockEntity {
	public DrillControllerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.DRILL_CONTROLLER, pos, state);
	}
}