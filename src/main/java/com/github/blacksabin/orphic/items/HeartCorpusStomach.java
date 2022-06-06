package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.common.BaseItem;
import com.github.blacksabin.orphic.components.HeartInternal;
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
