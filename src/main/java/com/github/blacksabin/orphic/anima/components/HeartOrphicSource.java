package com.github.blacksabin.orphic.anima.components;

import com.github.blacksabin.orphic.anima.hungermanagers.ManaHungerManager;
import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;

public class HeartOrphicSource extends BaseItem implements HeartInternal {



    public HeartOrphicSource(Settings settings) {
        super(settings);
    }

    public String getComponentType(){
        return "heart";
    }

    @Override
    public HungerManager getHungerManager() {
        return new ManaHungerManager();
    }





}
