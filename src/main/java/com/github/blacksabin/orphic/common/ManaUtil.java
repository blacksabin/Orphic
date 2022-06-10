package com.github.blacksabin.orphic.common;

import com.github.blacksabin.orphic.common.inventory.ManaBlock;
import net.minecraft.nbt.NbtCompound;

public class ManaUtil {

    public static void writeManaNbt(NbtCompound nbt, ManaBlock manaBlock){
        int manaCurrent = manaBlock.getManaCurrent();
        int manaMax = manaBlock.getManaMax();
        int manaRegen = manaBlock.getManaRegen();

        NbtCompound subTag = new NbtCompound();

        subTag.putInt("manaCurrent",manaCurrent);
        subTag.putInt("manaMax",manaMax);
        subTag.putInt("manaRegen",manaRegen);

        nbt.put("Mana",subTag);
    }

    public static void readManaNbt(NbtCompound nbt, ManaBlock manaBlock){
        if(nbt.contains("Mana")){
            NbtCompound manaTag = nbt.getCompound("Mana");
            manaBlock.setManaCurrent(manaTag.getInt("manaCurrent"));
            manaBlock.setManaMax(manaTag.getInt("manaMax"));
            manaBlock.setManaRegen(manaTag.getInt("manaRegen"));
        }else{
            manaBlock.setManaCurrent(0);
            manaBlock.setManaMax(0);
            manaBlock.setManaRegen(0);
        }
    }



}
