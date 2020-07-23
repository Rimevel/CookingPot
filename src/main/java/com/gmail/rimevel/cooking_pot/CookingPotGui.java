package com.gmail.rimevel.cooking_pot;

import java.util.function.Predicate;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPanel;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class CookingPotGui extends SyncedGuiDescription
{
	private static final int INVENTORY_SIZE = 4;

	public CookingPotGui(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
	{
		super(type, syncId, playerInventory);

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(160, 160);

		WItemSlot potSlots = WItemSlot.of(getBlockInventory(context, INVENTORY_SIZE), 0, 2, 2);
		root.add(potSlots, 3, 1);
		potSlots.setLocation(potSlots.getX() + 9, potSlots.getY());
		potSlots.setFilter(new SlotFilter());

		root.add(this.createPlayerInventoryPanel(), 0, 4);

		root.validate(this);
	}

	private class SlotFilter implements Predicate<ItemStack>
	{
		@Override
		public boolean test(ItemStack itemStack)
		{
			return itemStack.getItem().isFood();
		}
	}
}
