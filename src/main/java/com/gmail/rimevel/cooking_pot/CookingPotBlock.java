package com.gmail.rimevel.cooking_pot;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class CookingPotBlock extends BlockWithEntity
{
	public static Identifier ID = new Identifier("cooking_pot", "cooking_pot");

	public static final BooleanProperty COOKING =  BooleanProperty.of("cooking");
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public CookingPotBlock(Settings settings)
	{
		super(settings);
		setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(COOKING, false));
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(FACING);
		builder.add(COOKING);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world)
	{
		return new CookingPotBlockEntity();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,BlockHitResult hit)
	{
		if(state.get(COOKING))
		{
			if(ItemStack.areItemsEqual(player.getMainHandStack(), Items.BOWL.getStackForRender()))
			{
				CookingPotBlockEntity entity = (CookingPotBlockEntity) world.getBlockEntity(pos);
				if(entity.cookTime <= 0)
				{
					if(ItemStack.areEqual(new ItemStack(Items.BOWL), player.getMainHandStack()))
					{
						ItemStack result = entity.checkRecipe();
						PlayerInventory playerInv = player.inventory;
						playerInv.main.set(playerInv.selectedSlot, result.copy());
						return ActionResult.SUCCESS;
					}
				}
			}
		}
		else
		{
			CookingPotBlockEntity entity = (CookingPotBlockEntity) world.getBlockEntity(pos);
			for (ItemStack stack : entity.items)
			{
				System.out.println(stack.getItem().getName());
			}
			
			player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
			return ActionResult.SUCCESS;
		}
		return ActionResult.FAIL;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing());
	}
}