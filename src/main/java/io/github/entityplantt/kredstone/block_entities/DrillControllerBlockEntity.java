package io.github.entityplantt.kredstone.block_entities;

import java.util.HashMap;
import java.util.Map;

import io.github.entityplantt.kredstone.KRedstone;
import io.github.entityplantt.kredstone.ModBlockEntities;
import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModItems;
import io.github.entityplantt.kredstone.blocks.DrillBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext.Builder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class DrillControllerBlockEntity extends BlockEntity {
	public static final HashMap<Item, Float> TIP_TO_HARDNESS = new HashMap<>(Map.of(
			ModItems.STEEL_INGOT, 4f,
			Items.DIAMOND, 5f,
			Items.NETHERITE_INGOT, 10f));
	boolean poweredThisTick = false, poweredLastTick = false;
	public byte warmup = 0;
	public final SimpleInventory drill = new SimpleInventory(1) {
		public boolean canInsert(ItemStack stack) {
			return stack.isOf(ModItems.DRILL);
		}
	};
	public final SimpleInventory tip = new SimpleInventory(1) {
		public boolean canInsert(ItemStack stack) {
			return stack.isIn(TagKey.of(RegistryKeys.ITEM, KRedstone.id("drill_tips")));
		}
	};
	public final SimpleInventory inventory = new SimpleInventory(12);

	public DrillControllerBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.DRILL_CONTROLLER, pos, state);
	}

	@Override
	protected void writeData(WriteView view) {
		Inventories.writeData(view, inventory.heldStacks);
		view.put("tip", ItemStack.CODEC, tip.getStack(0));
		view.put("drill", ItemStack.CODEC, drill.getStack(0));
		view.putByte("warmup", warmup);
		super.writeData(view);
	}

	@Override
	protected void readData(ReadView view) {
		super.readData(view);
		Inventories.readData(view, inventory.heldStacks);
		tip.setStack(0, view.read("tip", ItemStack.CODEC).orElse(ItemStack.EMPTY));
		drill.setStack(0, view.read("drill", ItemStack.CODEC).orElse(ItemStack.EMPTY));
		warmup = view.getByte("warmup", (byte) 0);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(WrapperLookup registries) {
		return createNbt(registries);
	}

	public int drillLength() {
		int size = 0;
		while (size < 64 && world.isInBuildLimit(pos.offset(Direction.DOWN, size + 1))
				&& world.getBlockState(pos.offset(Direction.DOWN, size + 1)).isOf(ModBlocks.DRILL))
			++size;
		return size;
	}

	public boolean canDrillThrough(BlockPos pos) {
		KRedstone.LOGGER.info("canDrillThrough " + pos.toString());
		return world.isInBuildLimit(pos) && TIP_TO_HARDNESS.containsKey(tip.getStack(0).getItem())
				&& world.getBlockState(pos).getHardness(world, pos) <= TIP_TO_HARDNESS.get(tip.getStack(0).getItem());
	}

	public boolean canPushDrill() {
		int len = drillLength();
		return len < 64 && canDrillThrough(pos.offset(Direction.DOWN, len + 1));
	}

	public void pushDrill() {
		if (world.isClient())
			return;
		int len = drillLength();
		var blockPos = pos.offset(Direction.DOWN, len + 1);
		var stacks = world.getBlockState(blockPos).getDroppedStacks(
				new Builder((ServerWorld) world).add(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos))
						.add(LootContextParameters.TOOL, Items.NETHERITE_PICKAXE.getDefaultStack()));
		for (var stack : stacks) {
			var leftover = inventory.addStack(stack);
			if (!leftover.isEmpty())
				Block.dropStack(world, blockPos, leftover);
		}
		world.setBlockState(blockPos,
				ModBlocks.DRILL.getDefaultState().with(DrillBlock.DRILLING, true).with(DrillBlock.TIP, true));
		if (len > 0)
			world.setBlockState(blockPos.offset(Direction.UP),
					ModBlocks.DRILL.getDefaultState().with(DrillBlock.DRILLING, true));
	}

	public void getPowered() {
		poweredThisTick = true;
		if (++warmup >= 20) {
			pushDrill();
			warmup = 0;
		}
	}

	public void setDrillState(boolean state) {
		int len = drillLength();
		if (len > 0) {
			for (int i = 1; i < len; i++) {
				world.setBlockState(pos.offset(Direction.DOWN, i),
						ModBlocks.DRILL.getDefaultState().with(DrillBlock.DRILLING, state));
			}
			world.setBlockState(pos.offset(Direction.DOWN, len),
					ModBlocks.DRILL.getDefaultState().with(DrillBlock.DRILLING, state).with(DrillBlock.TIP, true));
		}
	}

	public static void tick(World world, BlockPos pos, BlockState state, DrillControllerBlockEntity entity) {
		if (entity.poweredThisTick) {
			if (!entity.poweredLastTick)
				entity.setDrillState(true);
			entity.poweredLastTick = true;
			entity.poweredThisTick = false;
		} else if (entity.poweredLastTick) {
			entity.poweredLastTick = false;
			entity.setDrillState(false);
		}
	}
}