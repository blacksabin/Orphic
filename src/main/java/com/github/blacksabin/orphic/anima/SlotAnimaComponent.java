package com.github.blacksabin.orphic.anima;

import com.github.blacksabin.orphic.anima.components.AnimaInternal;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

import java.util.Objects;

public class SlotAnimaComponent extends Slot {

    public final String myType;

    public SlotAnimaComponent(Inventory inventory, int index, int x, int y, String ComponentType) {
        super(inventory, index, x, y);
        this.myType = ComponentType;
    }


    @Override
    public boolean canInsert(ItemStack stack) {
        Item thisItem = stack.getItem();
        if(thisItem instanceof AnimaInternal){
            return Objects.equals(myType, ((AnimaInternal) thisItem).getComponentType());
        }
        return false;
    }

    public int getMaxItemCount(ItemStack stack) {
        return 1;
    }
}
