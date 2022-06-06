package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.components.HeartInternal;
import com.github.blacksabin.orphic.hungermanagers.ManaHungerManager;
import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.player.HungerManager;

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
