package io.github.entityplantt.kredstone.items;

import java.time.Instant;
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
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FuelSupplierItem extends Item {
	public final int click_max_change, max_fuel;

	public FuelSupplierItem(Settings s) {
		super(s.maxCount(1));
		max_fuel = s.max_fuel;
		click_max_change = s.click_max_change;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent,
			Consumer<Text> textConsumer, TooltipType type) {
		textConsumer.accept(Text.translatable("item.kredstone.fuel_supplier.tooltip",
				stack.getOrDefault(ModComponents.FUEL, 0).toString(), Integer.toString(max_fuel))
				.formatted(Formatting.GRAY));
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
			fEntity.changeFuel(Math.min(click_max_change, itemFuel));
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

	public boolean isFull(ItemStack s) {
		return s.getOrDefault(ModComponents.FUEL, 0) >= max_fuel;
	}

	public static class Settings extends Item.Settings {
		public int max_fuel = 0, click_max_change = 0;

		public Settings fuelSupplier(int max_fuel, int click_max_change) {
			this.max_fuel = max_fuel;
			this.click_max_change = click_max_change;
			return this;
		}
	}

	@Override
	public final boolean isItemBarVisible(ItemStack stack) {
		return true;
	}

	@Override
	public final int getItemBarColor(ItemStack stack) {
		return 0xff8000;
	}

	@Override
	public int getItemBarStep(ItemStack stack) {
		return Math.clamp(stack.getOrDefault(ModComponents.FUEL, 0) * 13 / max_fuel, 0, 13);
	}
}