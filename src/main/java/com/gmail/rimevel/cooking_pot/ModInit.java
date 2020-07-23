package com.gmail.rimevel.cooking_pot;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;

import net.minecraft.util.registry.Registry;

public class ModInit implements ModInitializer
{
	//INSTANCE: Cooking pot - block
	public static final Block COOKING_POT = new CookingPotBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f).nonOpaque());

	//INSTANCE: Cooking pot - tile entity
	public static BlockEntityType<CookingPotBlockEntity> COOKING_POT_ENTITY;

	public static ScreenHandlerType<CookingPotGui> COOKING_POT_SCREEN_HANDLER;

	@Override
	public void onInitialize()
	{
		//Cooking pot - block
		Registry.register(Registry.BLOCK, CookingPotBlock.ID, COOKING_POT);

		//Cooking pot - item
		Registry.register(Registry.ITEM, CookingPotBlock.ID, new BlockItem(COOKING_POT, new Item.Settings().group(ItemGroup.DECORATIONS)));

		//Cooking pot - tile entity
		COOKING_POT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, CookingPotBlock.ID.toString(), BlockEntityType.Builder.create(CookingPotBlockEntity::new, COOKING_POT).build(null));
		
		//Cooking pot - screen handler
		COOKING_POT_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(CookingPotBlock.ID, (syncId, inventory) -> new CookingPotGui(COOKING_POT_SCREEN_HANDLER, syncId, inventory));
	}
}
