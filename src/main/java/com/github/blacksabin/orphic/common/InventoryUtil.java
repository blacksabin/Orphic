package com.github.blacksabin.orphic.common;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class InventoryUtil {

    public static List<ItemStack> find(Inventory inventory, Item item) {
        return find(inventory, is -> is.isOf(item));
    }
    public static List<ItemStack> find(Inventory inventory, Predicate<ItemStack> predicate) {
        final List<ItemStack> list = new ArrayList<>(inventory.size());
        for (int i = 0; i < inventory.size(); i++) {
            final ItemStack stack = inventory.getStack(i);
            if (predicate.test(stack)) list.add(stack);
        }
        return list;
    }

}
