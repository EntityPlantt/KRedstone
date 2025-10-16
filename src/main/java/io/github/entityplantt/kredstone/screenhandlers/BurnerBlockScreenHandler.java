package io.github.entityplantt.kredstone.screenhandlers;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModItems;
import io.github.entityplantt.kredstone.ModScreenHandlers;
import io.github.entityplantt.kredstone.block_entities.BurnerBlockEntity;
import io.github.entityplantt.kredstone.network.BlockPosPayload;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;

public class BurnerBlockScreenHandler extends ScreenHandler {
	private final BurnerBlockEntity entity;
	private final ScreenHandlerContext context;

	// Client
	public BurnerBlockScreenHandler(int syncId, PlayerInventory inventory, BlockPosPayload payload) {
		this(syncId, inventory, (BurnerBlockEntity) inventory.player.getEntityWorld().getBlockEntity(payload.pos()));
	}

	// Main
	public BurnerBlockScreenHandler(int syncId, PlayerInventory inventory, BurnerBlockEntity entity) {
		super(ModScreenHandlers.BURNER, syncId);
		this.entity = entity;
		context = ScreenHandlerContext.create(entity.getWorld(), entity.getPos());
		addPlayerSlots(inventory, 8, 50);
		addSlot(new Slot(entity.inventory, 0, 105, 22) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return stack.getItem().equals(ModItems.FUEL_SUPPLIER);
			}
		});
		addSlot(new Slot(entity.inventory, 1, 55, 22) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return entity.getWorld().getFuelRegistry().isFuel(stack);
			}
		});
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(context, player, ModBlocks.BURNER);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slot) {
		return ItemStack.EMPTY;
	}

	public BurnerBlockEntity getEntity() {
		return entity;
	}
}