package io.github.entityplantt.kredstone.blocks;

import java.util.Map;

import com.mojang.serialization.MapCodec;

import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.block_entities.EncasedCapacitorBlockEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.RodBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SideShapeType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.block.WireOrientation;

public class EncasedCapacitorBlock extends RodBlock implements BlockEntityProvider {
	public static final MapCodec<EncasedCapacitorBlock> CODEC = createCodec(EncasedCapacitorBlock::new);
	private static final Map<Direction, VoxelShape> SHAPEMAP = VoxelShapes
			.createFacingShapeMap(Block.createCuboidZShape(3, 10, 16));

	public EncasedCapacitorBlock(AbstractBlock.Settings s) {
		super(s);
		setDefaultState(stateManager.getDefaultState().with(FACING, Direction.UP));
	}

	protected MapCodec<? extends RodBlock> getCodec() {
		return CODEC;
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> b) {
		b.add(FACING);
	}

	@Override
	public final boolean hasDynamicBounds() {
		return true;
	}

	protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPEMAP.get(state.get(FACING));
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return this.getDefaultState().with(FACING, ctx.getSide());
	}
	
	@Override
	protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		final BlockPos newpos = pos.offset(state.get(FACING).getOpposite());
		return world.getBlockState(newpos).isSideSolid(world, newpos, state.get(FACING), SideShapeType.FULL);
	}

	@Override
	protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock,
			WireOrientation wireOrientation, boolean notify) {
		if (!canPlaceAt(state, world, pos)) world.breakBlock(pos, true);
	}

	@Override
	protected boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new EncasedCapacitorBlockEntity(pos, state);
	}

	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
			BlockEntityType<T> type) {
		return type == ModBlockEntities.ENCASED_CAPACITOR ? EncasedCapacitorBlockEntity::tick : null;
	}
}