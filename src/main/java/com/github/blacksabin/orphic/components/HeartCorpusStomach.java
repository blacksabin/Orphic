package com.github.blacksabin.orphic.components;

import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.player.HungerManager;

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
