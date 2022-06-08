package com.github.blacksabin.orphic.common.inventory;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class FilteredSlot extends Slot {

    private List<SlotFilter> filters = new ArrayList<SlotFilter>();

    public FilteredSlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    public FilteredSlot setFilters(List<SlotFilter> newFilters){
        this.filters = newFilters;
        return this;
    }

    public FilteredSlot addFilter(SlotFilter newFilter){
        this.filters.add(newFilter);
        return this;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        for(SlotFilter filter : this.filters){
            if(!filter.isAllowed(stack)){
                return false;
            }
        }
        return true;
    }

}
