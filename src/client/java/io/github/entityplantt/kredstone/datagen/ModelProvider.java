package io.github.entityplantt.kredstone.datagen;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;

public class ModelProvider extends FabricModelProvider {
	public ModelProvider(FabricDataOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockStateModelGenerator gen) {
		gen.registerSimpleCubeAll(ModBlocks.STEEL_BLOCK);
		gen.registerSimpleCubeAll(ModBlocks.MACHINE_CORE);
		gen.registerRod(ModBlocks.ENCASED_CAPACITOR);
		gen.registerSimpleCubeAll(ModBlocks.EXCITED_BEDROCK);
		gen.registerSimpleCubeAll(ModBlocks.EXCITED_OBSIDIAN);
		gen.registerSimpleCubeAll(ModBlocks.EXCITED_CRYING_OBSIDIAN);
	}

	@Override
	public void generateItemModels(ItemModelGenerator gen) {
		gen.register(ModItems.STEEL_INGOT, Models.GENERATED);
		gen.register(ModItems.PREPARED_STEEL, Models.GENERATED);
		gen.register(ModItems.SILICON, Models.GENERATED);
		gen.register(ModItems.CAPACITOR, Models.GENERATED);
	}

	@Override
	public String getName() {
		return "ModelProvider";
	}
}
