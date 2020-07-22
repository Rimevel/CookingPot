package com.gmail.rimevel.cooking_pot;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class CookingPotBlock extends Block implements BlockEntityProvider
{
	public CookingPotBlock(Settings settings)
	{
		super(settings);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return null;
	}
	
}