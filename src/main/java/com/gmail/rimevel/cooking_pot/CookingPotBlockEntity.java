package com.gmail.rimevel.cooking_pot;

import java.util.ArrayList;

import com.gmail.rimevel.cooking_pot.CookingPotData.FoodGroup;
import com.gmail.rimevel.cooking_pot.CookingPotData.Recipe;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;

public class CookingPotBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory, Tickable
{
	private static final int COOKING_TIME_TOTAL = 400;

	DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);

	int cookTime = 0;

	public CookingPotBlockEntity()
	{
		super(ModInit.COOKING_POT_ENTITY);
		//Initailize the ingredient and recipe data for the cooking pot.
		if(!CookingPotData.isLoaded())
		{
			new CookingPotData();
		}
	}

	@Override
	public void tick()
	{
		if(isCooking())
		{
			if(cookTime > 0)
			{
				--this.cookTime;
			}
		}
		else
		{
			if(isFilled())
			{
				cookTime = COOKING_TIME_TOTAL;
				setCookingState(true);
			}
		}
	}

	public boolean isCooking()
	{
		return this.cookTime > 0 && getCachedState().get(CookingPotBlock.COOKING).booleanValue();
	}

	public ItemStack checkRecipe()
	{
		ArrayList<FoodGroup> allGroups = new ArrayList<FoodGroup>();
		for (ItemStack itemStack : items)
		{
			FoodGroup[] itemFoodGroups = CookingPotData.getIngredientGroups(itemStack.getItem());
			for (FoodGroup group : itemFoodGroups)
			{
				allGroups.add(group);
			}
		}

		System.out.println("Food group count:" + allGroups.size());

		for (Recipe recipe : CookingPotData.getRecipies()) {
			if(recipe.compare(allGroups))
			{
				setCookingState(false);
				return recipe.getResult();
			}
		}

		return ItemStack.EMPTY;
	}

	private void setCookingState(boolean value)
	{
		world.setBlockState(this.pos, getCachedState().with(CookingPotBlock.COOKING, value));
	}

	//Inventory

	@Override
	public DefaultedList<ItemStack> getItems()
	{
		return items;
	}

	@Override
	public int getMaxCountPerStack()
	{
		return 1;
	}

	@Override
	public boolean isValid(int slot, ItemStack stack)
	{
		return CookingPotData.getIngredientGroups(stack.getItem()).length > 0;
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
		//this.cookTime = tag.getShort("CookTime");
		Inventories.fromTag(tag, items);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		Inventories.toTag(tag, items);
		//tag.putShort("CookTime", (short)this.cookTime);
		return super.toTag(tag);
	}

	//NamedScreenHandlerFactory

	@Override
	public Text getDisplayName()
	{
		// Using the block name as the screen title
		return new TranslatableText(getCachedState().getBlock().getTranslationKey());
	}

	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inventory, PlayerEntity player)
	{
		return new CookingPotGui(ModInit.COOKING_POT_SCREEN_HANDLER, syncId, inventory, ScreenHandlerContext.create(world, pos));
	}
}