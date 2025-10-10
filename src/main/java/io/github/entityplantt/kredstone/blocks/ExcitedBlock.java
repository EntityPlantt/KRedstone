package io.github.entityplantt.kredstone.blocks;

import java.util.HashMap;

import io.github.entityplantt.kredstone.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ExcitedBlock extends Block {
	private Block normalBlock;

	private static HashMap<Block, ExcitedBlock> excitedVersion = new HashMap<>();

	public ExcitedBlock(Block b, RegistryKey<Block> k) {
		super(b.getSettings().registryKey(k).pistonBehavior(PistonBehavior.PUSH_ONLY).strength(5)
				.luminance((BlockState state) -> 2).requiresTool());
		normalBlock = b;
	}

	@Override
	protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
		world.scheduleBlockTick(pos, state.getBlock(), 2);
	}

	@Override
	protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(5) == 0) {
			world.setBlockState(pos, normalBlock.getDefaultState());
			world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS);
		} else
			world.scheduleBlockTick(pos, state.getBlock(), 2);
	}

	public static ExcitedBlock register(Block normalBlock) {
		String name = "excited_" + Registries.BLOCK.getId(normalBlock).getPath();
		RegistryKey<Block> k = ModBlocks.keyOfBlock(name);
		ExcitedBlock b = new ExcitedBlock(normalBlock, k);
		excitedVersion.put(normalBlock, b);
		return Registry.register(Registries.BLOCK, k, b);
	}

	public static ExcitedBlock getExcited(Block b) {
		return excitedVersion.get(b);
	}
}