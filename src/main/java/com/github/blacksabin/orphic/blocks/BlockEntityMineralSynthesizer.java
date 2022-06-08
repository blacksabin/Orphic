package com.github.blacksabin.orphic.blocks;

import com.github.blacksabin.orphic.OrphicInit;
import com.github.blacksabin.orphic.anima.ManaBlock;
import com.github.blacksabin.orphic.anima.ManaManager;
import com.github.blacksabin.orphic.screens.ScreenHandlerMineralSynthesizer;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.github.blacksabin.orphic.OrphicInit.STONE_KEY;

public class BlockEntityMineralSynthesizer extends BlockEntity implements ManaBlock, NamedScreenHandlerFactory, MachineInventory, SidedInventory {

    private int blockGenRate = 4;
    private int timer = 0;
    private final ManaManager manaManager = new ManaManager();

    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(4, ItemStack.EMPTY);

    public BlockEntityMineralSynthesizer(BlockPos pos, BlockState state) {
        super(OrphicInit.BLOCK_ENTITY_MINERAL_SYNTHESIZER, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntityMineralSynthesizer be) {
        // Code to generate new blocks
        //stack.isIn(tag) || STONE_KEY || Registry.ITEM.getEntryList(key)
        be.manaTick(be);
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ScreenHandlerMineralSynthesizer(syncId, playerInventory, this, this.manaManager);
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, items);
        this.manaManager.writeInventoryToTag(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, items);
        this.manaManager.readInventoryFromTag(nbt);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        // Just return an array of all slots
        int[] result = new int[getItems().size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = i;
        }

        return result;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, Direction direction) {
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction direction) {
        return true;
    }

    public ManaManager getManaManager(){
        return this.manaManager;
    }

    @Override
    public void runManaFunction(BlockEntity blockEntity) {
        BlockEntityMineralSynthesizer be = (BlockEntityMineralSynthesizer)blockEntity;
        ItemStack itemToStackMimic = be.getStack(0);
        Item itemToMimic = itemToStackMimic.getItem();
        if (itemToStackMimic.isIn(STONE_KEY)) {
            for (int i = 1; i < 4; i++) {
                ItemStack newStack = new ItemStack(itemToMimic, be.blockGenRate);
                ItemStack thisStack = be.getStack(i);
                if (thisStack.getItem() == itemToMimic && thisStack.getCount() < thisStack.getMaxCount()) {
                    thisStack.increment(be.blockGenRate);
                } else if (thisStack.isEmpty()) {
                    be.setStack(i, newStack);
                }
            }

        }

    }

    @Override
    public int getTickRate() {
        return 20;
    }

    @Override
    public int getTickTimer() {
        return this.timer;
    }

    @Override
    public void setTickRate(int newTickRate) {
        // Not necessary yet.
    }

    @Override
    public void incrementTimer() {
        this.timer++;
    }

    @Override
    public void resetTimer() {
        this.timer = 0;
    }

}
