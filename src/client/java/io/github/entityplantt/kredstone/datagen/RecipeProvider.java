package io.github.entityplantt.kredstone.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import io.github.entityplantt.kredstone.ModBlocks;
import io.github.entityplantt.kredstone.ModItems;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

public class RecipeProvider extends FabricRecipeProvider {
	public RecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}

	@Override
	protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup registryLookup,
			RecipeExporter exporter) {
		return new RecipeGenerator(registryLookup, exporter) {
			@Override
			public void generate() {
				// RegistryWrapper.Impl<Item> itemLookup =
				// registries.getOrThrow(RegistryKeys.ITEM);
				final var rc = RecipeCategory.MISC;
				final var mcc1 = hasItem(ModBlocks.MACHINE_CORE.asItem());
				final var mcc2 = conditionsFromItem(ModBlocks.MACHINE_CORE.asItem());

				createShaped(rc, ModItems.PREPARED_STEEL, 8)
						.pattern("iii").pattern("ici").pattern("iii")
						.input('i', Items.IRON_INGOT)
						.input('c', ItemTags.COALS)
						.criterion(hasItem(Items.IRON_INGOT), conditionsFromItem(Items.IRON_INGOT))
						.offerTo(exporter);
				createShaped(rc, ModBlocks.MACHINE_CORE.asItem())
						.pattern("sss").pattern("srs").pattern("sss")
						.input('s', ModItems.STEEL_INGOT)
						.input('r', Items.REDSTONE)
						.criterion(hasItem(ModItems.STEEL_INGOT), conditionsFromItem(ModItems.STEEL_INGOT))
						.offerTo(exporter);
				offerReversibleCompactingRecipes(rc, ModItems.STEEL_INGOT, rc, ModBlocks.STEEL_BLOCK.asItem());
				createShaped(rc, ModItems.CAPACITOR, 4)
						.pattern("scs").pattern("cic").pattern("srs")
						.input('i', Items.IRON_INGOT)
						.input('c', Items.COPPER_INGOT)
						.input('s', ModItems.SILICON)
						.input('r', Items.REDSTONE)
						.criterion(hasItem(ModItems.SILICON), conditionsFromItem(ModItems.SILICON))
						.offerTo(exporter);
				offerBlasting(List.of(ModItems.PREPARED_STEEL), rc, ModItems.STEEL_INGOT, 1.4f, 200,
						"steel_ingot");
				offerSmelting(List.of(Items.FLINT), rc, ModItems.SILICON, .3f, 200, "silicon");
				createShapeless(rc, ModBlocks.ENCASED_CAPACITOR.asItem())
						.input(ModItems.CAPACITOR)
						.input(Items.POLISHED_DEEPSLATE_SLAB)
						.criterion(hasItem(ModItems.CAPACITOR), conditionsFromItem(ModItems.CAPACITOR))
						.offerTo(exporter);
				createShaped(rc, ModBlocks.EXCITER.asItem())
						.pattern("gag").pattern("ama").pattern("grg")
						.input('g', Ingredient.ofItems(Items.GLOWSTONE_DUST, Items.QUARTZ))
						.input('a', Items.AMETHYST_SHARD)
						.input('r', Items.REDSTONE)
						.input('m', ModBlocks.MACHINE_CORE.asItem())
						.criterion(mcc1, mcc2).offerTo(exporter);
				createShaped(rc, ModBlocks.BURNER.asItem())
						.pattern("rcr").pattern("cmc").pattern("rcr")
						.input('r', Items.REDSTONE)
						.input('c', ItemTags.COALS)
						.input('m', ModBlocks.MACHINE_CORE.asItem())
						.criterion(mcc1, mcc2).offerTo(exporter);
				createShaped(rc, ModItems.FUEL_SUPPLIER)
						.pattern("  b").pattern(" c ").pattern("r  ")
						.input('b', Items.REDSTONE_BLOCK)
						.input('r', Items.REDSTONE)
						.input('c', ModItems.CAPACITOR)
						.criterion(hasItem(ModItems.CAPACITOR), conditionsFromItem(ModItems.CAPACITOR))
						.offerTo(exporter);
				createShaped(rc, ModItems.CAPACITOR_ADVANCED)
						.pattern("ccc").pattern("cgc").pattern("ccc")
						.input('c', ModItems.CAPACITOR)
						.input('g', Items.GOLD_INGOT)
						.criterion(hasItem(ModItems.CAPACITOR), conditionsFromItem(ModItems.CAPACITOR))
						.offerTo(exporter);
			}
		};
	}

	@Override
	public String getName() {
		return "RecipeProvider";
	}
}