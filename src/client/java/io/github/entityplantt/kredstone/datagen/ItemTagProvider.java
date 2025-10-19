package io.github.entityplantt.kredstone.datagen;

import java.util.concurrent.CompletableFuture;

import io.github.entityplantt.kredstone.KRedstone;
import io.github.entityplantt.kredstone.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public ItemTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	public static final TagKey<Item> DRILL_TIPS = TagKey.of(RegistryKeys.ITEM, KRedstone.id("drill_tips"));

	@Override
	protected void configure(WrapperLookup wrapperLookup) {
		valueLookupBuilder(DRILL_TIPS).add(ModItems.STEEL_INGOT, Items.NETHERITE_INGOT, Items.DIAMOND);
	}
}