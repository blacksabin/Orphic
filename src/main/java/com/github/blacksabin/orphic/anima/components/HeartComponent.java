package com.github.blacksabin.orphic.anima.components;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public interface HeartComponent extends AnimaComponent{


    default String getComponentType(){
        return "heart";
    }

    void orphic$hm_add(int food, float saturationModifier);
    void orphic$hm_eat(Item item, ItemStack stack);
    void orphic$hm_update(PlayerEntity player);
    int orphic$hm_getFoodLevel();
    float orphic$hm_getExhaustion();
    float orphic$hm_getSaturationLevel();






}
