package io.github.entityplantt.kredstone;

import java.util.function.Function;

import io.github.entityplantt.kredstone.items.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

public class ModItems {
	public static RegistryKey<ItemGroup> ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),
			KRedstone.id("items"));

	public static <T extends Item.Settings> Item register(String name, Function<T, Item> factory, T settings) {
		var key = RegistryKey.of(RegistryKeys.ITEM, KRedstone.id(name));
		@SuppressWarnings("unchecked")
		var item = factory.apply((T) settings.registryKey(key));
		Registry.register(Registries.ITEM, key, item);
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(i -> {
			i.add(item);
			if (item instanceof FuelSupplierItem fuelSupplierItem) {
				var stack = new ItemStack(item);
				stack.set(ModComponents.FUEL, fuelSupplierItem.max_fuel);
				i.add(stack);
			}
		});
		return item;
	}

	public static final Item PREPARED_STEEL = register("prepared_steel", Item::new, new Item.Settings());
	public static final Item STEEL_INGOT = register("steel_ingot", Item::new, new Item.Settings());
	public static final Item SILICON = register("silicon", Item::new, new Item.Settings());
	public static final Item CAPACITOR = register("capacitor", Item::new, new Item.Settings());
	public static final Item CAPACITOR_ADVANCED = register("capacitor_advanced", Item::new, new Item.Settings());
	public static final Item FUEL_SUPPLIER = register("fuel_supplier", FuelSupplierItem::new,
			new FuelSupplierItem.Settings().fuelSupplier(10000, 100));
	public static final Item FUEL_SUPPLIER_ADVANCED = register("fuel_supplier_advanced", FuelSupplierItem::new,
			new FuelSupplierItem.Settings().fuelSupplier(100000, 1000));

	public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY,
			FabricItemGroup.builder()
					.icon(() -> new ItemStack(STEEL_INGOT)).displayName(Text.translatable("itemGroup.kredstone"))
					.build());

	public static void init() {
		KRedstone.LOGGER.info("Registering mod items");
	}
}