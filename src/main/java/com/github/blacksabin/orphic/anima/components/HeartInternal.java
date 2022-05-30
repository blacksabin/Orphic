package com.github.blacksabin.orphic.anima.components;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface HeartInternal extends AnimaInternal {


    default String getComponentType(){
        return "heart";
    }

    HungerManager getHungerManager();






}
