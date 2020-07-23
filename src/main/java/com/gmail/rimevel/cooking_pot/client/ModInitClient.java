package com.gmail.rimevel.cooking_pot.client;

import com.gmail.rimevel.cooking_pot.CookingPotGui;
import com.gmail.rimevel.cooking_pot.CookingPotScreen;
import com.gmail.rimevel.cooking_pot.ModInit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry.Factory;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ModInitClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		BlockRenderLayerMap.INSTANCE.putBlock(ModInit.COOKING_POT, RenderLayer.getCutout());

		ScreenRegistry.<CookingPotGui, CookingPotScreen>register(ModInit.COOKING_POT_SCREEN_HANDLER, (Factory<CookingPotGui, CookingPotScreen>) (gui, inventory, title) -> new CookingPotScreen(gui, inventory.player, title));
		
		//(gui, inventory, title) -> new CookingPotScreen(gui, inventory.player, title)
	}
}