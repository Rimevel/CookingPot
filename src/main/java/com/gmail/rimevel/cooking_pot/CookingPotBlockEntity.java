package com.gmail.rimevel.cooking_pot;

import java.util.ArrayList;
import java.util.Random;

import com.gmail.rimevel.cooking_pot.CookingPotData.FoodGroup;
import com.gmail.rimevel.cooking_pot.CookingPotData.Recipe;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class CookingPotBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory, Tickable
{
	private static final int COOKING_TIME_TOTAL = 800;

	DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);

	public int cookTime = COOKING_TIME_TOTAL;

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
		if(world.isClient)
		{
			if(getState() == CookingPotState.COOKING)
			{
				spawnSmokeParticles();
			}
		}

		if(getState() == CookingPotState.COOKING)
		{
			if(cookTime > 0)
			{
				--this.cookTime;
				return;
			}

			ItemStack result = checkRecipe();
			clear();
			setStack(0, result);
			setCookingState(CookingPotState.DONE);
		}

		if(getState() == CookingPotState.EMPTY)
		{
			//Inventory is filled so lets start cooking :)
			if(isFilled())
			{
				setCookingState(CookingPotState.COOKING);
				world.playSound(null, pos, ModInit.EVENT_SOUND_POT_LID_SET, SoundCategory.BLOCKS, 1f, 1f);
			}
		}
	}

	//Complete a recipe if able and reset the pot to the initial state.
	public boolean finishAndReset(PlayerEntity player)
	{
		if(cookTime <= 0)
		{
			if(player.getMainHandStack().getItem() == Items.BOWL)
			{
				PlayerInventory playerInv = player.inventory;
				playerInv.insertStack(items.get(0));
				player.getMainHandStack().decrement(1);
				world.playSound(null, pos, ModInit.EVENT_SOUND_POT_LID_LIFT, SoundCategory.BLOCKS, 1f, 1f);
				world.setBlockState(pos, world.getBlockState(pos).with(CookingPotBlock.COOKING, CookingPotState.EMPTY));
				cookTime = COOKING_TIME_TOTAL;
				return true;
			}
		}

		return false;
	}

	public CookingPotState getState()
	{
		return getCachedState().get(CookingPotBlock.COOKING);
	}

	//Returns the resulting dish from the ingredients in the pot.
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

		for (Recipe recipe : CookingPotData.getRecipies())
		{
			if(recipe.compare(allGroups))
			{
				return recipe.getResult().copy();
			}
		}

		return ItemStack.EMPTY;
	}

	private void setCookingState(CookingPotState state)
	{
		world.setBlockState(this.pos, getCachedState().with(CookingPotBlock.COOKING, state));
	}

	//Spawn smoke, can only run on the client side!
	private void spawnSmokeParticles()
	{
		if(world != null)
		{
			Random random = world.random;
			if(random.nextFloat() <= 0.11f)
			{
				for(int i = 0; i < random.nextInt(2) + 2; ++i)
				{
					double x = pos.getX() + (random.nextInt(2) == 0 ? 0.15D : 0.85D);
					double y = pos.getY() + 0.98D;
					double z = pos.getZ() + (random.nextInt(2) == 0 ? 0.015D : 0.85D);

					world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0D, 0.01f, 0.0D);
				}
			}
		}
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
		this.cookTime = tag.getShort("CookTime");
		Inventories.fromTag(tag, items);
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		Inventories.toTag(tag, items);
		tag.putShort("CookTime", (short)this.cookTime);
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