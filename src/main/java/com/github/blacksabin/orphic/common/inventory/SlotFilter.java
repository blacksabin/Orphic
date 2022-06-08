package com.github.blacksabin.orphic.common.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface SlotFilter {

    boolean isAllowed(ItemStack stack);
    boolean isAllowed(Item item);


}
