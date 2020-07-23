package com.gmail.rimevel.cooking_pot;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;

public class CookingPotIngredients
{
	private ArrayList<Item> meat = new ArrayList<Item>();
	private ArrayList<Item> fish = new ArrayList<Item>();
	private ArrayList<Item> poison = new ArrayList<Item>();
	private ArrayList<Item> veggie = new ArrayList<Item>();

	public CookingPotIngredients()
	{
		meat.add(Items.PORKCHOP);
		meat.add(Items.BEEF);
		meat.add(Items.MUTTON);
		meat.add(Items.CHICKEN);
		meat.add(Items.RABBIT);
		meat.add(Items.COOKED_PORKCHOP);
		meat.add(Items.COOKED_BEEF);
		meat.add(Items.COOKED_MUTTON);
		meat.add(Items.COOKED_CHICKEN);
		meat.add(Items.COOKED_RABBIT);

		fish.add(Items.COD);
		fish.add(Items.SALMON);
		fish.add(Items.TROPICAL_FISH);
		fish.add(Items.COOKED_COD);
		fish.add(Items.COOKED_SALMON);

		poison.add(Items.PUFFERFISH);
		poison.add(Items.POISONOUS_POTATO);

		veggie.add(Items.CARROT);
		veggie.add(Items.POTATO);
	}
}