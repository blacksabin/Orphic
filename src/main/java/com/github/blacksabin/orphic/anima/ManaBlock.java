package com.github.blacksabin.orphic.anima;

import com.github.blacksabin.orphic.render.RendererUtil;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public interface ManaBlock {

    ManaManager getManaManager();

    void runManaFunction(BlockEntity be);
    default int getManaCost(){
        return 100;
    }
    int getTickRate();
    int getTickTimer();

    void setTickRate(int newTickRate);
    void incrementTimer();
    void resetTimer();


    default void manaTick(BlockEntity blockEntity){
        this.incrementTimer();
        if(this.getTickTimer() >= this.getTickRate()){
            this.resetTimer();
            ManaManager mana = this.getManaManager();
            if(mana.canSpendMana(this.getManaCost())){
                mana.spendMana(this.getManaCost());
                this.runManaFunction(blockEntity);
            }
        }
    }







}
