package com.gmail.rimevel.cooking_pot;

import net.minecraft.util.StringIdentifiable;

public enum CookingPotState implements StringIdentifiable
{
	EMPTY("empty"),
	COOKING("cooking"),
	DONE("done");

	private final String name;

	private CookingPotState(String name)
	{
		this.name = name;
	}

	public String toString()
	{
		return this.asString();
	}
  
	@Override
	public String asString()
	{
		return this.name;
	}
}