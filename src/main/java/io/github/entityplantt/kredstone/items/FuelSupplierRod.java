package io.github.entityplantt.kredstone.items;

import java.util.function.Consumer;

import io.github.entityplantt.kredstone.ModComponents;
import io.github.entityplantt.kredstone.block_entities.AbstractFuelContainerBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FuelSupplierRod extends Item {
	private static int CLICK_MAX_CHANGE = 100;

	public FuelSupplierRod(Settings s) {
		super(s);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
			Consumer<Text> textConsumer, TooltipType type) {
		textConsumer.accept(Text.of("Fuel: " + stack.getOrDefault(ModComponents.FUEL, 0).toString()));
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return stack.getOrDefault(ModComponents.FUEL, 0) > 0;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext ctx) {
		ItemStack stack = ctx.getStack();
		World world = ctx.getWorld();
		BlockPos pos = ctx.getBlockPos();
		BlockEntity entity = world.getBlockEntity(pos);
		if (entity != null && entity instanceof AbstractFuelContainerBlockEntity) {
			AbstractFuelContainerBlockEntity fEntity = (AbstractFuelContainerBlockEntity) entity;
			int itemFuel = stack.getOrDefault(ModComponents.FUEL, 0);
			int fuel = fEntity.getFuel();
			fEntity.changeFuel(Math.min(CLICK_MAX_CHANGE, itemFuel));
			fuel = Math.max(fEntity.getFuel() - fuel, 0);
			if (fuel > 0) {
				ctx.getPlayer().swingHand(ctx.getHand());
				stack.set(ModComponents.FUEL, itemFuel - fuel);
				return ActionResult.SUCCESS;
			}
			return ActionResult.FAIL;
		}
		return super.useOnBlock(ctx);
	}
}