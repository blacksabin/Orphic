package com.github.blacksabin.orphic.anima;

import com.github.blacksabin.orphic.components.HeartInternal;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class AnimaPropertiesContainer {

    public AnimaInventory inventory = new AnimaInventory();

    public HungerManager hungerManager = new HungerManager();
    public ManaManager manaManager = new ManaManager();

    public boolean initialized = false;

    public AnimaPropertiesContainer(NbtCompound nbt){
        this.readAnimaFromNbt(nbt);

    }

    public AnimaPropertiesContainer(){

    }

    /*
    this.addSlot(new SlotAnimaComponent(this.inventory,0,10,10,"brain")); // Brain
    this.addSlot(new SlotAnimaComponent(this.inventory,1,20,10,"brain-aug")); // Brain Augment
    this.addSlot(new SlotAnimaComponent(this.inventory,2,30,10,"vision")); // Vision
    this.addSlot(new SlotAnimaComponent(this.inventory,3,10,20,"muscle")); // Muscle Fiber
    this.addSlot(new SlotAnimaComponent(this.inventory,4,10,30,"skeleton")); // Skeleton
    this.addSlot(new SlotAnimaComponent(this.inventory,5,10,40,"heart")); // Core
    this.addSlot(new SlotAnimaComponent(this.inventory,6,10,56,"extra")); // Extra Organ
    this.addSlot(new SlotAnimaComponent(this.inventory,7,10,56,"extra")); // Extra Organ
    this.addSlot(new SlotAnimaComponent(this.inventory,8,10,56,"extra")); // Extra Organ
     */


    public AnimaInventory getAnimaInventory(){
        return this.inventory;
    }

    public HungerManager getHungerManager(){
        return this.hungerManager;
    }

    public ManaManager getManaManager(){
        return this.manaManager;
    }

    public void setHungerManager(){
        Item heart = this.getHeart().getItem();
        if(heart instanceof HeartInternal) {
            this.hungerManager = ((HeartInternal) heart).getHungerManager();
        }else{
            this.hungerManager = new HungerManager();
        }
    }

    public void tick(){

    }

    public ItemStack getHeart(){
        return this.inventory.getStack(5);
    }

    public void writeAnimaToNbt(NbtCompound nbt){
        if(!this.initialized){
            this.inventory.initializeInventory();
            this.initialized = true;
        }
        this.inventory.writeInventoryToTag(nbt);
        this.hungerManager.writeNbt(nbt);
    }

    public void readAnimaFromNbt(NbtCompound nbt){
        if(!this.initialized){
            this.inventory.initializeInventory();
            this.initialized = true;
        }
        this.inventory.readInventoryFromTag(nbt);
        this.setHungerManager();
        this.hungerManager.readNbt(nbt);

    }


}
