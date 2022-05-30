package com.github.blacksabin.orphic.anima;

public class ManaManager {

    private int manaCurrent = 0;
    private int manaMax = 0;
    private int manaRegen = 0;


    public int getManaCurrent() {
        return manaCurrent;
    }

    public int getManaMax() {
        return manaMax;
    }

    public int getManaRegen() {
        return manaRegen;
    }

    public float getManaRatio(){
        return (float)this.manaCurrent / this.manaMax;
    }

    public void setManaCurrent(int manaCurrent) {
        this.manaCurrent = manaCurrent;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }

    public void regenMana(){
        this.addMana(this.manaRegen);
    }

    public void addMana(int incoming){
        this.manaCurrent = Math.min(this.manaCurrent + incoming, this.manaMax);
    }

    public int spendMana(int amount){
        int overload = this.manaCurrent - amount;
        this.manaCurrent = Math.max(overload, 0);
        return Math.min(overload, 0);
    }

}
