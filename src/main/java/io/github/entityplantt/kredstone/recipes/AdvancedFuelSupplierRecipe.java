package io.github.entityplantt.kredstone.recipes;

import java.util.List;
import java.util.Optional;

import io.github.entityplantt.kredstone.ModComponents;
import io.github.entityplantt.kredstone.ModItems;
import io.github.entityplantt.kredstone.ModRecipeSerializers;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.IngredientPlacement;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.input.CraftingRecipeInput;
import net.minecraft.registry.RegistryWrapper.WrapperLookup;
import net.minecraft.world.World;

public class AdvancedFuelSupplierRecipe extends SpecialCraftingRecipe {
	private static final IngredientPlacement INGREDIENT_PLACEMENT = IngredientPlacement.forMultipleSlots(List.of(
			Optional.empty(), Optional.empty(), Optional.of(Ingredient.ofItem(ModItems.FUEL_SUPPLIER)),
			Optional.empty(), Optional.of(Ingredient.ofItem(ModItems.CAPACITOR_ADVANCED)), Optional.empty(),
			Optional.of(Ingredient.ofItem(Items.REDSTONE)), Optional.empty(), Optional.empty()));

	public AdvancedFuelSupplierRecipe(CraftingRecipeCategory category) {
		super(category);
	}

	@Override
	public boolean isIgnoredInRecipeBook() {
		return false;
	}

	@Override
	public ItemStack craft(CraftingRecipeInput input, WrapperLookup registries) {
		var stack = new ItemStack(ModItems.FUEL_SUPPLIER_ADVANCED);
		stack.set(ModComponents.FUEL, input.getStackInSlot(2).getOrDefault(ModComponents.FUEL, 0));
		return stack;
	}

	@Override
	public boolean matches(CraftingRecipeInput input, World world) {
		return input.getStackCount() == 3 && input.size() == 9
				&& input.getStackInSlot(2).isOf(ModItems.FUEL_SUPPLIER)
				&& input.getStackInSlot(4).isOf(ModItems.CAPACITOR_ADVANCED)
				&& input.getStackInSlot(6).isOf(Items.REDSTONE);
	}

	@Override
	public RecipeSerializer<AdvancedFuelSupplierRecipe> getSerializer() {
		return ModRecipeSerializers.ADVANCED_FUEL_SUPPLIER;
	}

	@Override
	public IngredientPlacement getIngredientPlacement() {
		return INGREDIENT_PLACEMENT;
	}
}