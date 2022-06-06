package com.github.blacksabin.orphic.anima;

import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;

import java.util.Iterator;

import static com.github.blacksabin.orphic.OrphicInit.LOGGER;

public class AnimaInventory implements Inventory {

    private DefaultedList<ItemStack> stacks;
    private ScreenHandler handler;

    public AnimaInventory(ScreenHandler handler) {
        this.stacks = DefaultedList.ofSize(9, ItemStack.EMPTY);
        this.handler = handler;
    }

    public AnimaInventory() {
    }
    public void initializeInventory(){
        this.stacks = DefaultedList.ofSize(9, ItemStack.EMPTY);
    }


    public void informHandler(){
        if(this.handler != null){
            this.handler.onContentChanged(this);
        }
    }

    public int size() {
        return this.stacks.size();
    }

    public boolean isEmpty() {
        Iterator var1 = this.stacks.iterator();

        ItemStack itemStack;
        do {
            if (!var1.hasNext()) {
                return true;
            }

            itemStack = (ItemStack)var1.next();
        } while(itemStack.isEmpty());

        return false;
    }

    public ItemStack getStack(int slot) {
        return slot >= this.size() ? ItemStack.EMPTY : (ItemStack)this.stacks.get(slot);
    }

    public ItemStack removeStack(int slot) {
        this.informHandler();
        return Inventories.removeStack(this.stacks, slot);
    }

    public ItemStack removeStack(int slot, int amount) {
        ItemStack itemStack = Inventories.splitStack(this.stacks, slot, amount);
        if (!itemStack.isEmpty()) {
            this.informHandler();
        }

        return itemStack;
    }

    public void setStack(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        this.informHandler();
    }

    public void markDirty() {
    }

    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    public void clear() {
        this.stacks.clear();
    }


    public void writeInventoryToTag(NbtCompound tag){
        NbtList newTag = new NbtList();

        for(int i = 0; i < this.size(); i++) {
            NbtCompound stackTag = new NbtCompound();
            stackTag.putInt("Slot", i);
            stackTag.put("Stack", this.getStack(i).writeNbt(new NbtCompound()));
            newTag.add(stackTag);
        }

        tag.put("Internals",newTag);




    }

    public void readInventoryFromTag(NbtCompound tag){
        NbtList listTag = tag.getList("Internals", NbtType.COMPOUND);
        this.clear();
        listTag.forEach(element -> {
            NbtCompound stackTag = (NbtCompound) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            this.setStack(slot, stack);
        });
    }

}
