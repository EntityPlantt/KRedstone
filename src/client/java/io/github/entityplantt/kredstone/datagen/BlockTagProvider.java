package io.github.entityplantt.kredstone.datagen;

import java.util.concurrent.CompletableFuture;

import io.github.entityplantt.kredstone.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
	public BlockTagProvider(FabricDataOutput output, CompletableFuture<WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	public static final TagKey<Block> PICKAXE = TagKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla("mineable/pickaxe"));

	@Override
	protected void configure(WrapperLookup wrapperLookup) {
		valueLookupBuilder(PICKAXE).setReplace(false)
				.add(ModBlocks.STEEL_BLOCK, ModBlocks.MACHINE_CORE, ModBlocks.BURNER, ModBlocks.EXCITED_OBSIDIAN,
						ModBlocks.EXCITED_CRYING_OBSIDIAN, ModBlocks.EXCITER);
	}
}