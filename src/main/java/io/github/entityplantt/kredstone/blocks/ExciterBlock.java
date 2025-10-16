package io.github.entityplantt.kredstone.blocks;

import com.mojang.serialization.MapCodec;

import io.github.entityplantt.kredstone.KRedstone;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ExciterBlock extends Block implements IPowerableBlock {
	private static final MapCodec<ExciterBlock> CODEC = createCodec(ExciterBlock::new);
	public static final IntProperty POWER = IntProperty.of("power", 0, 2);

	public ExciterBlock(Settings s) {
		super(s);
		setDefaultState(stateManager.getDefaultState().with(POWER, 0));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(POWER);
	}

	@Override
	public int powerNeeds(World world, BlockPos pos, BlockState state, Direction fromDirection) {
		return 1;
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (state.get(POWER) == 0) return;
		world.setBlockState(pos, state.with(POWER, state.get(POWER) - 1));
		if (state.get(POWER) > 1) world.scheduleBlockTick(pos, this, 1);
		for (Direction d : KRedstone.DALL) {
			BlockPos p = pos.offset(d);
			BlockState s = world.getBlockState(p);
			ExcitedBlock e = ExcitedBlock.getExcited(s.getBlock());
			if (e == null) continue;
			world.setBlockState(p, e.getDefaultState());
		}
	}

	@Override
	public void getPowered(World world, BlockPos pos, BlockState state, Direction fromDirection) {
		world.setBlockState(pos, state.with(POWER, 2));
		if (!world.getBlockTickScheduler().isQueued(pos, this)) world.scheduleBlockTick(pos, this, 1);
	}
	
	@Override
	protected MapCodec<ExciterBlock> getCodec() {
		return CODEC;
	}
}