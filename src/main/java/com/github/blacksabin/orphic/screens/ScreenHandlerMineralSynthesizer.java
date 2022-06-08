package com.github.blacksabin.orphic.screens;

import com.github.blacksabin.orphic.OrphicInit;
import com.github.blacksabin.orphic.anima.ManaManager;
import com.github.blacksabin.orphic.common.inventory.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import static com.github.blacksabin.orphic.OrphicInit.STONE_KEY;

public class ScreenHandlerMineralSynthesizer extends ScreenHandler {

    private final Inventory inventory;
    private final ManaManager manaManager;

    public ScreenHandlerMineralSynthesizer(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(4), new ManaManager());
    }
    public ScreenHandlerMineralSynthesizer(int syncId, PlayerInventory playerInventory, Inventory inventory, ManaManager manaManager) {
        super(OrphicInit.SCREEN_HANDLER_MACHINE, syncId);
        checkSize(inventory, 4);
        this.inventory = inventory;
        this.manaManager = manaManager;
        inventory.onOpen(playerInventory.player);

        //This will place the slot in the correct locations for a 3x3 Grid. The slots exist on both server and client!
        //This will not render the background of the slots however, this is the Screens job
        int m;
        int l;

        // ManaCell Slot ; ALWAYS POSITIONED X:6 Y:53 ; drawGauge in Screen won't look right otherwise.
        this.addSlot(new FilteredSlot(this.manaManager, 0, 6, 53).addFilter(new ItemFilter(OrphicInit.ITEM_MANA_CELL)));

        // Slot Input
        this.addSlot(new FilteredSlot(inventory, 0, 62, 35).addFilter(new TagFilter(STONE_KEY)));


        // Slot Output
        this.addSlot(new OutputSlot(inventory, 1, 98, 17));
        this.addSlot(new OutputSlot(inventory, 2, 98, 35));
        this.addSlot(new OutputSlot(inventory, 3, 98, 53));

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }



    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.manaManager.size()+this.inventory.size()) {
                if (!this.insertItem(originalStack, this.manaManager.size()+this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.manaManager.size()+this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }


}
