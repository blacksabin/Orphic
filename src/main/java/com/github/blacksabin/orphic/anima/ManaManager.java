package com.github.blacksabin.orphic.anima;

import com.github.blacksabin.orphic.items.ItemManaCell;
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

public class ManaManager implements Inventory {

    private int manaCurrent = 0;
    private int manaMax = 0;
    private int manaRegen = 0;

    private DefaultedList<ItemStack> stacks;
    private ScreenHandler handler;

    public ManaManager(ScreenHandler handler) {
        this.initializeInventory();
        this.handler = handler;
    }

    public ManaManager() {
        this.initializeInventory();
    }
    public void initializeInventory(){
        this.stacks = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public ItemStack getManaCell(){
        return this.stacks.get(0);
    }
    public void setManaCell(ItemStack newManaCell){
        this.stacks.set(0,newManaCell);
    }
    public void informHandler(){
        if(this.handler != null){
            this.handler.onContentChanged(this);
        }
        this.updateManaCell();
    }

    public void updateManaCell(){
        if(!this.stacks.get(0).isEmpty()){
            ItemStack myCell = this.stacks.get(0);
            if(myCell.getItem() instanceof ItemManaCell){
                NbtCompound nbt = myCell.getOrCreateNbt().getCompound("Mana");
                this.manaCurrent = nbt.getInt("manaCurrent");
                this.manaMax = nbt.getInt("manaMax");
                this.manaRegen = nbt.getInt("manaRegen");
            }
        }else{
            this.manaCurrent = 0;
            this.manaMax = 0;
            this.manaRegen = 0;
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

        tag.put("ManaCell",newTag);

        //this.informHandler();


    }

    public void readInventoryFromTag(NbtCompound tag){
        NbtList listTag = tag.getList("ManaCell", NbtType.COMPOUND);
        this.clear();
        listTag.forEach(element -> {
            NbtCompound stackTag = (NbtCompound) element;
            int slot = stackTag.getInt("Slot");
            ItemStack stack = ItemStack.fromNbt(stackTag.getCompound("Stack"));
            this.setStack(slot, stack);
        });

        this.informHandler();

    }




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

    public boolean canSpendMana(int amount){
        return this.manaCurrent >= amount;
    }

    public int spendMana(int amount){
        int overload = this.manaCurrent - amount;
        this.manaCurrent = Math.max(overload, 0);
        return Math.min(overload, 0);
    }


}
