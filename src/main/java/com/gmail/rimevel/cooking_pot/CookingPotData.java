package com.gmail.rimevel.cooking_pot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;

public class CookingPotData
{
	public enum FoodGroup
	{
		MEAT,
		SEAFOOD,
		VEGGIES,
		FRUIT,
		SWEETENERS,
		POISON,
		DAIRY,
		GOLDEN,
		EGGS,
		MUSHROOM,
		GRAIN
	}

	private static HashMap<Item, FoodGroup[]> ingredients = new HashMap<Item, FoodGroup[]>();

	private static ArrayList<Recipe> recipies = new ArrayList<Recipe>();

	private static boolean loaded;

	public CookingPotData()
	{
		//Meat
		addIngredient(Items.PORKCHOP, FoodGroup.MEAT);
		addIngredient(Items.BEEF, FoodGroup.MEAT);
		addIngredient(Items.MUTTON, FoodGroup.MEAT);
		addIngredient(Items.CHICKEN, FoodGroup.MEAT);
		addIngredient(Items.RABBIT, FoodGroup.MEAT);
		//SEAFOOD
		addIngredient(Items.COD, FoodGroup.SEAFOOD);
		addIngredient(Items.SALMON, FoodGroup.SEAFOOD);
		addIngredient(Items.TROPICAL_FISH, FoodGroup.SEAFOOD);
		addIngredient(Items.PUFFERFISH, FoodGroup.SEAFOOD, FoodGroup.POISON);
		//VEGGIES
		addIngredient(Items.CARROT, FoodGroup.VEGGIES);
		addIngredient(Items.GOLDEN_CARROT, FoodGroup.GOLDEN);
		addIngredient(Items.POTATO, FoodGroup.VEGGIES);
		addIngredient(Items.BEETROOT, FoodGroup.VEGGIES);
		//MUSHROOMS
		addIngredient(Items.BROWN_MUSHROOM, FoodGroup.VEGGIES, FoodGroup.MUSHROOM);
		addIngredient(Items.RED_MUSHROOM, FoodGroup.VEGGIES, FoodGroup.MUSHROOM);
		//POISON
		addIngredient(Items.POISONOUS_POTATO, FoodGroup.VEGGIES, FoodGroup.POISON);
		//FRUIT
		addIngredient(Items.APPLE, FoodGroup.FRUIT);
		addIngredient(Items.GOLDEN_APPLE, FoodGroup.FRUIT, FoodGroup.GOLDEN);
		addIngredient(Items.MELON_SLICE, FoodGroup.FRUIT);
		//SWEETENERS
		addIngredient(Items.HONEYCOMB, FoodGroup.SWEETENERS);
		addIngredient(Items.HONEY_BOTTLE, FoodGroup.SWEETENERS);
		addIngredient(Items.SUGAR, FoodGroup.SWEETENERS);
		//EGGS
		addIngredient(Items.EGG, FoodGroup.EGGS);
		//GRAIN
		addIngredient(Items.WHEAT, FoodGroup.GRAIN);

		//Recipes

		//Mushroom stew
		addRecipe(new ItemStack(Items.MUSHROOM_STEW, 1), FoodGroup.MUSHROOM, FoodGroup.MUSHROOM, FoodGroup.VEGGIES);

		loaded = true;
	}

	public static boolean isLoaded()
	{
		return loaded;
	}

	public void addIngredient(Item item, FoodGroup...foodGroups)
	{
		ingredients.put(item, foodGroups);
	}

	public void addRecipe(ItemStack result, FoodGroup...groups)
	{
		recipies.add(new Recipe(result, groups));
	}

	public static FoodGroup[] getIngredientGroups(Item item)
	{
		if(ingredients.containsKey(item))
		{
			return ingredients.get(item);
		}
		return new FoodGroup[0];
	}

	public static ArrayList<Recipe> getRecipies()
	{
		return recipies;
	}

	//Inner classes

	public class Recipe
	{
		private FoodGroup[] groups;
		private ItemStack result;

		public Recipe(ItemStack result, FoodGroup...groups)
		{
			this.groups = groups;
			this.result = result;
		}

		public boolean compare(ArrayList<FoodGroup> input)
		{
			ArrayList<FoodGroup> remaining = new ArrayList<FoodGroup>(Arrays.asList(this.groups));

			for (FoodGroup foodGroup : input)
			{
				if(remaining.contains(foodGroup))
				{
					remaining.remove(foodGroup);
				}
			}

			if(remaining.size() == 0)
			{
				return true;
			}
			return false;
		}

		public ItemStack getResult()
		{
			return result;
		}
	}
}