package io.github.entityplantt.kredstone.screenhandlers;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModScreenHandlers;
import io.github.entityplantt.kredstone.block_entities.BurnerBlockEntity;
import io.github.entityplantt.kredstone.items.FuelSupplierItem;
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
		addSlot(new Slot(entity.rodInventory, 0, 105, 22) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return stack.getItem() instanceof FuelSupplierItem fs && !fs.isFull(stack);
			}
		});
		addSlot(new Slot(entity.fuelInventory, 0, 55, 22) {
			@Override
			public boolean canInsert(ItemStack stack) {
				return entity.getWorld().getFuelRegistry().isFuel(stack);
			}
		});
		addPlayerSlots(inventory, 8, 50);
	}

	@Override
	public boolean canUse(PlayerEntity player) {
		return canUse(context, player, ModBlocks.BURNER);
	}

	@Override
	public ItemStack quickMove(PlayerEntity player, int slotidx) {
		var newStack = ItemStack.EMPTY;
		var slot = getSlot(slotidx);
		if (slot != null && slot.hasStack()) {
			var slotStack = slot.getStack();
			newStack = slotStack.copy();
			if (slotidx < 2) {
				// slot in block
				if (!insertItem(slotStack, 2, slots.size(), true))
					return ItemStack.EMPTY;
			}
			// slot in player
			else if (!insertItem(slotStack, 0, 2, false))
				return ItemStack.EMPTY;
			if (slotStack.isEmpty())
				slot.setStack(ItemStack.EMPTY);
			else
				slot.markDirty();
		}
		return newStack;
	}

	public BurnerBlockEntity getEntity() {
		return entity;
	}

	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.entity.rodInventory.onClose(player);
		this.entity.fuelInventory.onClose(player);
	}
}