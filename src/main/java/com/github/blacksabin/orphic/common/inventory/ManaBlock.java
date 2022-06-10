package com.github.blacksabin.orphic.common.inventory;

import net.minecraft.block.entity.BlockEntity;

public interface ManaBlock extends MachineInventory {

    boolean hasManaCell();
    boolean hasManaStorage();
    int getManaCurrent();
    void setManaCurrent(int manaCurrent);
    int getManaMax();
    void setManaMax(int manaMax);
    int getManaRegen();
    void setManaRegen(int manaRegen);
    float getManaRatio();
    void regenMana();
    void addMana(int amount);
    boolean canSpendMana(int amount);
    int spendMana(int amount);

    void runManaFunction(BlockEntity be);


}
