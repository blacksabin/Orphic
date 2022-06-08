package com.github.blacksabin.orphic.common.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemFilter implements SlotFilter{

    private final Item itemType;

    public ItemFilter(Item newItemType){
        this.itemType = newItemType;
    }
    public ItemFilter(ItemStack itemStack){
        this.itemType = itemStack.getItem();
    }

    public boolean isAllowed(ItemStack stack){
        return stack.getItem() == this.itemType;
    }

    public boolean isAllowed(Item item){
        return item == this.itemType;
    }

}
