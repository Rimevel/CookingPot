package com.gmail.rimevel.cooking_pot;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModInit implements ModInitializer
{
	public static final Block COOKING_POT = new CookingPotBlock(FabricBlockSettings.of(Material.METAL).hardness(4.0f).nonOpaque());

	public static BlockEntityType<CookingPotBlockEntity> COOKING_POT_ENTITY;

	@Override
	public void onInitialize()
	{
		//Cooking pot - block
		Registry.register(Registry.BLOCK, new Identifier("cooking_pot", "cooking_pot"), COOKING_POT);

		//Cooking pot - item
		Registry.register(Registry.ITEM, new Identifier("cooking_pot", "cooking_pot"), new BlockItem(COOKING_POT, new Item.Settings().group(ItemGroup.DECORATIONS)));

		//Cooking pot - tile entity
		COOKING_POT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, "cooking_pot:cooking_pot", BlockEntityType.Builder.create(CookingPotBlockEntity::new, COOKING_POT).build(null));
	}
}
