package com.github.blacksabin.orphic.anima.hungermanagers;

import com.github.blacksabin.orphic.anima.AnimaComponent;
import com.github.blacksabin.orphic.anima.AnimaPropertiesContainer;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class MasterHungerManager extends HungerManager {
    private AnimaPropertiesContainer anima;
    private HungerManager realHungerManager = new HungerManager();


    public void add(int food, float saturationModifier){
        this.realHungerManager.add(food, saturationModifier);
    }

    public void eat(Item item, ItemStack stack){
        this.realHungerManager.eat(item, stack);
    }

    public void update(PlayerEntity player){
        this.anima = ((AnimaComponent)player).orphic$getAnimaProperties();
        this.realHungerManager = this.anima.getHungerManager();
        this.realHungerManager.update(player);
    }

    public int getFoodLevel(){
        return this.realHungerManager.getFoodLevel();
    }

    public int getPrevFoodLevel() {
        return this.realHungerManager.getPrevFoodLevel();
    }

    public float getExhaustion(){
        return this.realHungerManager.getExhaustion();
    }

    public float getSaturationLevel(){
        return this.realHungerManager.getSaturationLevel();
    }




}
