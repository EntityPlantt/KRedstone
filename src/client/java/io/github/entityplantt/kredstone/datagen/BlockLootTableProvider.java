package io.github.entityplantt.kredstone.datagen;

import java.util.concurrent.CompletableFuture;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

public class BlockLootTableProvider extends FabricBlockLootTableProvider {
	protected BlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, registryLookup);
	}

	@Override
	public void generate() {
		addDrop(ModBlocks.STEEL_BLOCK);
		addDrop(ModBlocks.MACHINE_CORE);
		addDrop(ModBlocks.ENCASED_CAPACITOR, ModItems.CAPACITOR);
		addDrop(ModBlocks.EXCITER);
		addDrop(ModBlocks.BURNER);
		addDrop(ModBlocks.DRILL_CONTROLLER);
		addDrop(ModBlocks.DRILL, ModItems.DRILL);
		addDrop(ModBlocks.EXCITED_OBSIDIAN, Items.OBSIDIAN);
		addDrop(ModBlocks.EXCITED_CRYING_OBSIDIAN, Items.CRYING_OBSIDIAN);
	}

	@Override
	public String getName() {
		return "BlockLootTableProvider";
	}
}
