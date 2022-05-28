package com.github.blacksabin.orphic.anima.components;

import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeartOrphicSource extends BaseItem implements HeartComponent {



    public HeartOrphicSource(Settings settings) {
        super(settings);
    }

    public String getComponentType(){
        return "heart";
    }

    @Override
    public void orphic$hm_add(int food, float saturationModifier) {

    }

    @Override
    public void orphic$hm_eat(Item item, ItemStack stack) {

    }

    @Override
    public void orphic$hm_update(PlayerEntity player) {

    }

    @Override
    public int orphic$hm_getFoodLevel() {
        return 20;
    }

    @Override
    public float orphic$hm_getExhaustion() {
        return 0;
    }

    @Override
    public float orphic$hm_getSaturationLevel() {
        return 20;
    }


}
