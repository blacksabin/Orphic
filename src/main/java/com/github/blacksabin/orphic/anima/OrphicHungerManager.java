package com.github.blacksabin.orphic.anima;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public class OrphicHungerManager extends HungerManager {



    private int foodLevel = 20;
    private float saturationLevel = 5.0F;
    private float exhaustion;
    private int foodTickTimer;
    private int prevFoodLevel = 20;
    private int foodLevelMax = 20;


    public void add(int food, float saturationModifier){
        this.exhaustion = 0;
        this.saturationLevel = 20;
        this.foodLevel = this.foodLevelMax;
    }

    public void eat(Item item, ItemStack stack){
        this.exhaustion = 0;
        this.saturationLevel = 20;
        this.foodLevel = this.foodLevelMax;
    }

    public void update(PlayerEntity player){

        Difficulty difficulty = player.world.getDifficulty();
        this.prevFoodLevel = this.foodLevel;
        if (this.exhaustion > 4.0F) {
            this.exhaustion -= 4.0F;
            if (this.saturationLevel > 0.0F) {
                this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.foodLevel = Math.max(this.foodLevel - 1, 0);
            }
        }

        boolean bl = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        if (bl && this.saturationLevel > 0.0F && player.canFoodHeal() && this.foodLevel >= 20) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 10) {
                float f = Math.min(this.saturationLevel, 6.0F);
                player.heal(f / 6.0F);
                this.addExhaustion(f);
                this.foodTickTimer = 0;
            }
        } else if (bl && this.foodLevel >= 18 && player.canFoodHeal()) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.foodTickTimer = 0;
            }
        } else if (this.foodLevel <= 0) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.damage(DamageSource.STARVE, 1.0F);
                }

                this.foodTickTimer = 0;
            }
        } else {
            this.foodTickTimer = 0;
        }
        this.exhaustion = 0;
        this.saturationLevel = 20;
        this.foodLevel = this.foodLevelMax;
    }

    public int getFoodLevel(){
        return 20;
    }

    public int getPrevFoodLevel() {
        return this.prevFoodLevel;
    }

    public float getExhaustion(){
        return 0;
    }

    public float getSaturationLevel(){
        return 20;
    }




}
