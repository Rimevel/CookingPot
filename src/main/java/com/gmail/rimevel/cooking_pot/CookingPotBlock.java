package com.gmail.rimevel.cooking_pot;

import java.util.Random;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
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

	public static final EnumProperty<CookingPotState> COOKING = EnumProperty.of("cooking", CookingPotState.class);
	public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

	public CookingPotBlock(Settings settings)
	{
		super(settings);
		setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(COOKING, CookingPotState.EMPTY));
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
		//if(world.isClient)
		//{
		//	if(player.isSneaking())
		//	{
		//		return ActionResult.PASS;
		//	}
		//
		//	return ActionResult.SUCCESS;
		//}

		CookingPotBlockEntity entity = (CookingPotBlockEntity) world.getBlockEntity(pos);

		if((entity instanceof CookingPotBlockEntity))
		{
			if(state.get(COOKING) == CookingPotState.COOKING)
			{
				if(player.isSneaking())
				{
					return ActionResult.PASS;
				}

				return ActionResult.FAIL;
			}

			if(state.get(COOKING) == CookingPotState.DONE)
			{
				if(player.isSneaking())
				{
					return ActionResult.PASS;
				}

				if(entity.finishAndReset(player))
				{
					return ActionResult.SUCCESS;
				}

				return ActionResult.FAIL;
			}

			if(state.get(COOKING) == CookingPotState.EMPTY)
			{
				if(player.isSneaking())
				{
					return ActionResult.PASS;
				}

				player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
				return ActionResult.SUCCESS;
			}
		}

		return ActionResult.PASS;
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx)
	{
		return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing());
	}

	@Environment(EnvType.CLIENT)
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random)
	{
		if (random.nextInt(15) == 0)
		{
			world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 0.25F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
		}

		CookingPotState cookState = state.get(COOKING);

		if(cookState == CookingPotState.COOKING)
		{
			if (random.nextInt(1) == 0)
			{
				world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, ModInit.EVENT_SOUND_POT_LID_SHAKE_1, SoundCategory.BLOCKS, 0.25F + random.nextFloat(), random.nextFloat() * 0.3F + 0.2F, false);
			}
		}
	}
}