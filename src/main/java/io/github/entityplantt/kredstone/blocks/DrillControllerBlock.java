package io.github.entityplantt.kredstone.blocks;

import com.mojang.serialization.MapCodec;

import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.block_entities.DrillControllerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DrillControllerBlock extends BlockWithEntity implements IPowerableBlock {
	private static final MapCodec<DrillControllerBlock> CODEC = createCodec(DrillControllerBlock::new);

	public DrillControllerBlock(Settings settings) {
		super(settings);
	}

	@Override
	public DrillControllerBlockEntity createBlockEntity(BlockPos arg0, BlockState arg1) {
		return new DrillControllerBlockEntity(arg0, arg1);
	}

	@Override
	protected MapCodec<DrillControllerBlock> getCodec() {
		return CODEC;
	}

	@Override
	public int powerNeeds(World world, BlockPos pos, BlockState state, Direction fromDirection) {
		return world.getBlockEntity(pos) instanceof DrillControllerBlockEntity e && e.canPushDrill() ? 2 : 0;
	}

	@Override
	public void getPowered(World world, BlockPos pos, BlockState state, Direction fromDirection) {
		if (world.getBlockEntity(pos) instanceof DrillControllerBlockEntity e)
			e.getPowered();
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type) {
		return validateTicker(type, ModBlockEntities.DRILL_CONTROLLER, DrillControllerBlockEntity::tick);
	}
}