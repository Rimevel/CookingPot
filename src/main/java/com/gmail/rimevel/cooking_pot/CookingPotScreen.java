package com.gmail.rimevel.cooking_pot;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class CookingPotScreen extends CottonInventoryScreen<CookingPotGui>
{
	public CookingPotScreen(CookingPotGui gui, PlayerEntity player, Text title)
	{
		super(gui, player, title);
	}
}