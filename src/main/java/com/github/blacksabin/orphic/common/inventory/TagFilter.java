package com.github.blacksabin.orphic.common.inventory;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;

public class TagFilter implements SlotFilter{

    private final TagKey<Item> myTagKey;

    public TagFilter(TagKey<Item> newTagKey){
        this.myTagKey = newTagKey;
    }

    public boolean isAllowed(ItemStack stack){
        return stack.isIn(myTagKey);
    }

    public boolean isAllowed(Item item){
        return item.getDefaultStack().isIn(myTagKey);
    }

}
