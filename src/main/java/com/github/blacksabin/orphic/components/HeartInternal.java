package com.github.blacksabin.orphic.components;

import net.minecraft.entity.player.HungerManager;

public interface HeartInternal extends AnimaInternal {


    default String getComponentType(){
        return "heart";
    }

    HungerManager getHungerManager();






}
