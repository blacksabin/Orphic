package com.github.blacksabin.orphic.anima.screens;

import com.github.blacksabin.orphic.OrphicInit;
import com.github.blacksabin.orphic.anima.AnimaInventory;
import com.github.blacksabin.orphic.anima.AnimaPropertiesContainer;
import com.github.blacksabin.orphic.anima.SlotAnimaComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import static com.github.blacksabin.orphic.OrphicInit.LOGGER;


public class ScreenHandlerAnimaModifier extends ScreenHandler {
    private final AnimaPropertiesContainer targetAnima;
    private final AnimaInventory inventory;

    public ScreenHandlerAnimaModifier(int synchronizationID, PlayerInventory playerInventory, PacketByteBuf packetByteBuf) {
        this(synchronizationID, playerInventory, new AnimaPropertiesContainer(packetByteBuf.readNbt()));
    }

    public ScreenHandlerAnimaModifier(int syncId, PlayerInventory playerInventory, AnimaPropertiesContainer targetAnima) {
        super(OrphicInit.SCREEN_HANDLER_ANIMA_MODIFIER, syncId);
        this.targetAnima = targetAnima;
        this.inventory = this.targetAnima.getAnimaInventory();


        LOGGER.info("Making ScreenHandler. Makin' slots.");

        this.addSlot(new SlotAnimaComponent(this.inventory,0,10,10,"brain")); // Brain
        this.addSlot(new SlotAnimaComponent(this.inventory,1,20,10,"brain-aug")); // Brain Augment
        this.addSlot(new SlotAnimaComponent(this.inventory,2,30,10,"vision")); // Vision
        this.addSlot(new SlotAnimaComponent(this.inventory,3,10,20,"muscle")); // Muscle Fiber
        this.addSlot(new SlotAnimaComponent(this.inventory,4,10,30,"skeleton")); // Skeleton
        this.addSlot(new SlotAnimaComponent(this.inventory,5,10,40,"heart")); // Core
        this.addSlot(new SlotAnimaComponent(this.inventory,6,10,56,"extra")); // Extra Organ
        this.addSlot(new SlotAnimaComponent(this.inventory,7,10,56,"extra")); // Extra Organ
        this.addSlot(new SlotAnimaComponent(this.inventory,8,10,56,"extra")); // Extra Organ

        int i;
        int j;

        for(i = 0; i < 3; ++i) {
            for(j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }

        LOGGER.info("End of constructor.");
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }


    @Override
    public void close(PlayerEntity player) {
        LOGGER.info("Closing screen.");
        super.close(player);
        // Take what is in the inventory, put it into anima.
        NbtCompound internalsTag = new NbtCompound();
        this.inventory.writeInventoryToTag(internalsTag);
        this.targetAnima.readAnimaFromNbt(internalsTag);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (index <= 8 && index >= 0) {
                if(canInsertIntoSlot(itemStack2,(SlotAnimaComponent)slot)) {
                    if (!this.insertItem(itemStack2, 9, 45, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.insertItem(itemStack2, 0, 8, false)) {
                return ItemStack.EMPTY;
            }

            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }

            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTakeItem(player, itemStack2);
        }

        return itemStack;
    }

}

