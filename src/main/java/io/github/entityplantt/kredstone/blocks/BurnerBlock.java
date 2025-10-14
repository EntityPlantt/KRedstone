package io.github.entityplantt.kredstone.blocks;

import com.mojang.serialization.MapCodec;

import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.ModItems;
import io.github.entityplantt.kredstone.block_entities.BurnerBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class BurnerBlock extends BlockWithEntity {
	private static MapCodec<BurnerBlock> CODEC = createCodec(BurnerBlock::new);

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
	protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos,
			PlayerEntity player, Hand hand, BlockHitResult hit) {
		BurnerBlockEntity e = (BurnerBlockEntity) world.getBlockEntity(pos);
		if (e.getType() == ModBlockEntities.BURNER) {
			if (stack.getItem() == ModItems.FUEL_SUPPLIER) {
				e.putRod(stack);
			}
		}
		return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}
}