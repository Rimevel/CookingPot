package com.gmail.rimevel.cooking_pot;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandlerType;

public class CookingPotGui extends SyncedGuiDescription
{
	private static final int INVENTORY_SIZE = 4;

	public CookingPotGui(ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory)
	{
		super(type, syncId, playerInventory);

		WGridPanel root = new WGridPanel();
		setRootPanel(root);
		root.setSize(300, 200);

		root.validate(this);
	}
}
