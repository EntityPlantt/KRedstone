package io.github.entityplantt.kredstone.blocks;

import com.mojang.serialization.MapCodec;

import io.github.entityplantt.kredstone.block_entities.DrillControllerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.util.math.BlockPos;

public class DrillControllerBlock extends BlockWithEntity {
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
}