package com.github.blacksabin.orphic.anima.components;

import com.github.blacksabin.orphic.common.BaseItem;

public class ItemOrphicHeart extends BaseItem implements AnimaComponent {



    public ItemOrphicHeart(Settings settings) {
        super(settings);
    }

    @Override
    public String getComponentType(){
        return "heart";
    }


}
