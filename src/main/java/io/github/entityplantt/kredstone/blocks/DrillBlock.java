package io.github.entityplantt.kredstone.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;

public class DrillBlock extends Block {
	public static final BooleanProperty DRILLING = BooleanProperty.of("drilling"), TIP = BooleanProperty.of("tip");

	public DrillBlock(Settings settings) {
		super(settings);
		setDefaultState(stateManager.getDefaultState().with(DRILLING, false).with(TIP, false));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(DRILLING).add(TIP);
	}
}