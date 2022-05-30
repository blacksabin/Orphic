package com.github.blacksabin.orphic.anima.components;

import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class HeartCorpusStomach extends BaseItem implements HeartInternal {



    public HeartCorpusStomach(Settings settings) {
        super(settings);
    }

    public String getComponentType(){
        return "heart";
    }

    public HungerManager getHungerManager(){
        return new HungerManager();
    }


}
