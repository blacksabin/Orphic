package com.github.blacksabin.orphic.common.inventory;

import com.github.blacksabin.orphic.items.ItemManaCell;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

public class SlotManaCell extends Slot {

    public SlotManaCell(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return stack.getItem() instanceof ItemManaCell;
    }

}
