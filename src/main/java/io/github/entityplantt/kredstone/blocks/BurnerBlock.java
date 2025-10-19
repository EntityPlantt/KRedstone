package io.github.entityplantt.kredstone.blocks;

import java.util.function.BiConsumer;

import com.mojang.serialization.MapCodec;

import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.block_entities.BurnerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class BurnerBlock extends BlockWithEntity {
	private static final MapCodec<BurnerBlock> CODEC = createCodec(BurnerBlock::new);

	public BurnerBlock(Settings s) {
		super(s);
		setDefaultState(stateManager.getDefaultState().with(Properties.LIT, false));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> b) {
		b.add(Properties.LIT);
	}

	@Override
	protected boolean hasComparatorOutput(BlockState state) {
		return true;
	}

	@Override
	protected int getComparatorOutput(BlockState state, World world, BlockPos pos, Direction direction) {
		BlockEntity e = world.getBlockEntity(pos);
		return e.getType() == ModBlockEntities.BURNER ? ((BurnerBlockEntity) e).rodCharge() : 0;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BurnerBlockEntity(pos, state);
	}

	@Override
	protected MapCodec<BurnerBlock> getCodec() {
		return CODEC;
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type) {
		return validateTicker(type, ModBlockEntities.BURNER, BurnerBlockEntity::tick);
	}

	@Override
	protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
		if (!world.isClient() && world.getBlockEntity(pos) instanceof BurnerBlockEntity entity) {
			player.openHandledScreen(entity);
		}
		return ActionResult.SUCCESS;
	}

	private static void dropStuff(World world, BlockPos pos) {
		if (!world.isClient() && world.getBlockEntity(pos) instanceof BurnerBlockEntity entity) {
			ItemScatterer.spawn(world, pos, entity.rodInventory.heldStacks);
			ItemScatterer.spawn(world, pos, entity.fuelInventory.heldStacks);
		}
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		dropStuff(world, pos);
		return super.onBreak(world, pos, state, player);
	}

	@Override
	protected void onExploded(BlockState state, ServerWorld world, BlockPos pos, Explosion explosion,
			BiConsumer<ItemStack, BlockPos> stackMerger) {
		dropStuff(world, pos);
		super.onExploded(state, world, pos, explosion, stackMerger);
	}
}