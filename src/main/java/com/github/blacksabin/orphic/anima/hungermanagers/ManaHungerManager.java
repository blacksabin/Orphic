package com.github.blacksabin.orphic.anima.hungermanagers;

import com.github.blacksabin.orphic.anima.AnimaComponent;
import com.github.blacksabin.orphic.anima.AnimaPropertiesContainer;
import com.github.blacksabin.orphic.anima.ManaManager;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public class ManaHungerManager extends HungerManager {

    private int foodLevel = 20;
    private float saturationLevel = 5.0F;
    private float exhaustion;
    private int foodTickTimer;
    private int prevFoodLevel = 20;
    private int maxFoodLevel = 20;
    private ManaManager manaManager;

    public ManaHungerManager(){}

    public void setManaManager(ManaManager newManaManager) {
        this.manaManager = newManaManager;
    }

    public void add(int food, float saturationModifier) {

    }

    public void eat(Item item, ItemStack stack) {

    }

    public void update(PlayerEntity player) {
        AnimaPropertiesContainer animaProperties = ((AnimaComponent)player).orphic$getAnimaProperties();
        this.setManaManager(animaProperties.getManaManager());
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
        if (bl && player.canFoodHeal() && this.manaManager.getManaRatio() >= 1F) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 10) {
                float f = Math.min(this.saturationLevel, 6.0F);
                player.heal(f / 6.0F);
                this.addExhaustion(f);
                this.foodTickTimer = 0;
            }
        } else if (bl && this.manaManager.getManaRatio() >= 0.9F && player.canFoodHeal()) {
            ++this.foodTickTimer;
            if (this.foodTickTimer >= 80) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.foodTickTimer = 0;
            }
        } else if (this.manaManager.getManaRatio() <= 0F) {
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

    }





}
