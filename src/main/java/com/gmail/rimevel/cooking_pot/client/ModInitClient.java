package com.gmail.rimevel.cooking_pot.client;

import com.gmail.rimevel.cooking_pot.ModInit;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class ModInitClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		BlockRenderLayerMap.INSTANCE.putBlock(ModInit.COOKING_POT, RenderLayer.getCutout());
	}
}