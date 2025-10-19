package io.github.entityplantt.kredstone;

import io.github.entityplantt.kredstone.recipes.*;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class ModRecipeSerializers {
	public static final RecipeSerializer<AdvancedFuelSupplierRecipe> ADVANCED_FUEL_SUPPLIER = register(
			"crafting_advanced_fuel_supplier",
			new SpecialCraftingRecipe.SpecialRecipeSerializer<>(AdvancedFuelSupplierRecipe::new));

	private static <T extends Recipe<?>> RecipeSerializer<T> register(String name, RecipeSerializer<T> serializer) {
		return Registry.register(Registries.RECIPE_SERIALIZER,
				RegistryKey.of(RegistryKeys.RECIPE_SERIALIZER, KRedstone.id(name)), serializer);
	}

	public static void init() {
	}
}