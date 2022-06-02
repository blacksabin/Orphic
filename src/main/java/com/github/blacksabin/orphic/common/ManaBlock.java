package com.github.blacksabin.orphic.common;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

public interface ManaBlock {

    ItemStack getManaCell();
    void setManaCell(ItemStack newManaCell);

    default float getManaPercent(){
        return (float)this.getManaCurrent() / this.getManaMax();
    }
    int getManaCurrent();
    int getManaMax();
    int getManaRegen();
    default void writeManaToNbt(NbtCompound nbt){
        nbt.put("ManaCell",this.getManaCell().writeNbt(new NbtCompound()));
    }

    default void readManaToNbt(NbtCompound nbt){
        NbtCompound manaTag = nbt.getCompound("ManaCell");
        this.setManaCell(ItemStack.fromNbt(manaTag));
    }

}
