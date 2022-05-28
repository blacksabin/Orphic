package com.github.blacksabin.orphic.common;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class AnimaUtil {

    /*

    Anima Tag Structure:

    "Anima":
        "manaCurrent": int  - Current Value
        "manaMax": int      - Maximum Value
        "manaRegen": int          - Increment Current by this Value 1/s





     */



    static public boolean hasAnima(LivingEntity targetEntity){
        NbtCompound nbt = new NbtCompound();
        targetEntity.writeCustomDataToNbt(nbt);
        return nbt.contains("Anima");
    }
    static public boolean hasAnima(ItemStack stack){
        return stack.getOrCreateNbt().contains("Anima");
    }

    static public NbtCompound getAnima(LivingEntity targetEntity){
        NbtCompound nbt = new NbtCompound();
        targetEntity.writeCustomDataToNbt(nbt);
        if(nbt.contains("Anima")){
            return nbt.getCompound("Anima");
        }else{
            return new NbtCompound();
        }
    }

    static public NbtCompound getAnima(ItemStack stack){
        NbtCompound nbt = new NbtCompound();
        nbt = stack.getOrCreateNbt();
        if(nbt.contains("Anima")){
            return nbt.getCompound("Anima");
        }else{
            return new NbtCompound();
        }
    }

    static public boolean spendAnima(LivingEntity targetEntity, int amount){
        boolean ret = false;
        if(hasAnima(targetEntity)){
            NbtCompound nbt = getAnima(targetEntity);

            int store = nbt.getInt("manaCurrent");

            if( (store-amount) >= 0){
                nbt.putInt("manaCurrent",store-amount);
                ret = true;
            }


        }
        return ret;
    }

    static public void addAnima(LivingEntity targetEntity, int amount){
        if(hasAnima(targetEntity)){
            NbtCompound nbt = getAnima(targetEntity);

            int store = nbt.getInt("manaCurrent");
            int storeMax = nbt.getInt("manaMax");

            nbt.putInt("manaCurrent",Math.min(store+amount,storeMax));


        }
    }

}
