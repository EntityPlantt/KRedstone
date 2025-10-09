package io.github.entityplantt.kredstone.datagen.lang;

import java.util.concurrent.CompletableFuture;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;

public class EnglishLanguageProvider extends net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider {
	public EnglishLanguageProvider(FabricDataOutput dataOutput,
			CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
		super(dataOutput, "en_us", registryLookup);
	}

	@Override
	public void generateTranslations(WrapperLookup registryLookup, TranslationBuilder b) {
		b.add(ModItems.PREPARED_STEEL, "Prepared Steel Mixture");
		b.add(ModItems.STEEL_INGOT, "Steel Ingot");
		b.add(ModItems.SILICON, "Silicon");
		b.add(ModItems.CAPACITOR, "Capacitor");
		b.add(ModBlocks.STEEL_BLOCK, "Steel Block");
		b.add(ModBlocks.MACHINE_CORE, "Machine Core");
		b.add(ModBlocks.ENCASED_CAPACITOR, "Encased Capacitor");
		b.add("itemGroup.kredstone", "1000 Redstone");
	}
}