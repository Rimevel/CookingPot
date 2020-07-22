package com.gmail.rimevel.cooking_pot;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.collection.DefaultedList;

public class CookingPotBlockEntity extends BlockEntity implements ImplementedInventory
{
	DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);

	public CookingPotBlockEntity()
	{
		super(ModInit.COOKING_POT_ENTITY);
	}

	@Override
	public DefaultedList<ItemStack> getItems()
	{
		return null;
	}

	@Override
	public boolean canPlayerUse(PlayerEntity player)
	{
		return pos.isWithinDistance(player.getBlockPos(), 4.5);
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag)
	{
		super.fromTag(state, tag);
		Inventories.fromTag(tag, items);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		Inventories.toTag(tag, items);
		return super.toTag(tag);
	}
}