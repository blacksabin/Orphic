package com.github.blacksabin.orphic.anima;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class AnimaPropertiesContainer {

    public AnimaInventory inventory = new AnimaInventory();

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

    public void tick(){

    }

    public ItemStack getCore(){
        return this.inventory.getStack(5);
    }

    public void writeAnimaToNbt(NbtCompound nbt){
        if(!this.initialized){
            this.inventory.initializeInventory();
            this.initialized = true;
        }
        this.inventory.writeInventoryToTag(nbt);
    }

    public void readAnimaFromNbt(NbtCompound nbt){
        if(!this.initialized){
            this.inventory.initializeInventory();
            this.initialized = true;
        }
        this.inventory.readInventoryFromTag(nbt);

    }


}
