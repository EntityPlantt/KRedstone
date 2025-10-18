package io.github.entityplantt.kredstone.datagen;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.block.Block;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.ItemModels;
import net.minecraft.client.data.ModelIds;
import net.minecraft.client.data.Models;
import net.minecraft.client.data.TextureMap;
import net.minecraft.client.data.TexturedModel;
import net.minecraft.client.data.VariantsBlockModelDefinitionCreator;
import net.minecraft.client.render.model.json.WeightedVariant;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.BooleanProperty;

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
		registerBooleanProperty(gen, ModBlocks.BURNER, Properties.LIT, "_on");
	}

	private void registerBooleanProperty(BlockStateModelGenerator gen, Block b, BooleanProperty p,
			String trueSubModel) {
		WeightedVariant weightedVariant = BlockStateModelGenerator
				.createWeightedVariant(TexturedModel.CUBE_ALL.upload(b, gen.modelCollector));
		WeightedVariant weightedVariant2 = BlockStateModelGenerator.createWeightedVariant(
				gen.createSubModel(b, trueSubModel, Models.CUBE_ALL, TextureMap::all));
		gen.blockStateCollector.accept(VariantsBlockModelDefinitionCreator.of(b)
				.with(BlockStateModelGenerator.createBooleanModelMap(Properties.LIT, weightedVariant2,
						weightedVariant)));
	}

	@Override
	public void generateItemModels(ItemModelGenerator gen) {
		gen.register(ModItems.STEEL_INGOT, Models.GENERATED);
		gen.register(ModItems.PREPARED_STEEL, Models.GENERATED);
		gen.register(ModItems.SILICON, Models.GENERATED);
		gen.register(ModItems.CAPACITOR, Models.GENERATED);
		gen.register(ModItems.CAPACITOR_ADVANCED, Models.GENERATED);
		gen.output.accept(ModBlocks.EXCITER.asItem(), ItemModels.basic(ModelIds.getBlockModelId(ModBlocks.EXCITER)));
		gen.register(ModItems.FUEL_SUPPLIER, Models.HANDHELD);
		gen.register(ModItems.FUEL_SUPPLIER_ADVANCED, Models.HANDHELD);
	}

	@Override
	public String getName() {
		return "ModelProvider";
	}
}