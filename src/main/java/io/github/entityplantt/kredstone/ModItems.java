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

	public static Item register(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
		RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, KRedstone.id(name));
		Item item = factory.apply(settings.registryKey(key));
		Registry.register(Registries.ITEM, key, item);
		ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP_KEY).register(i -> i.add(item));
		return item;
	}

	public static final Item PREPARED_STEEL = register("prepared_steel", Item::new, new Item.Settings());
	public static final Item STEEL_INGOT = register("steel_ingot", Item::new, new Item.Settings());
	public static final Item SILICON = register("silicon", Item::new, new Item.Settings());
	public static final Item CAPACITOR = register("capacitor", Item::new, new Item.Settings());
	public static final Item FUEL_SUPPLIER = register("fuel_supplier", FuelSupplierRod::new, new Item.Settings());

	public static final ItemGroup ITEM_GROUP = Registry.register(Registries.ITEM_GROUP, ITEM_GROUP_KEY,
			FabricItemGroup.builder()
					.icon(() -> new ItemStack(STEEL_INGOT)).displayName(Text.translatable("itemGroup.kredstone"))
					.build());

	public static void init() {
		KRedstone.LOGGER.info("Registering mod items");
	}
}