package com.github.blacksabin.orphic.blocks;

import com.github.blacksabin.orphic.OrphicInit;
import com.github.blacksabin.orphic.common.ManaUtil;
import com.github.blacksabin.orphic.common.inventory.ManaBlock;
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
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static com.github.blacksabin.orphic.OrphicInit.STONE_KEY;

public class BlockEntityMineralSynthesizer extends BlockEntity implements ManaBlock, NamedScreenHandlerFactory, SidedInventory {

    private int blockGenRate = 4;
    private int timer = 0;
    private int tickRate = 20;
    private int manaCurrent = 0;
    private int manaMax = 0;
    private int manaRegen = 0;
    private int manaCost = 25;

    private DefaultedList<ItemStack> items = DefaultedList.ofSize(5, ItemStack.EMPTY);

    public BlockEntityMineralSynthesizer(BlockPos pos, BlockState state) {
        super(OrphicInit.BLOCK_ENTITY_MINERAL_SYNTHESIZER, pos, state);
    }

    public void updateManaCell(){
        ItemStack stack = this.items.get(0);
        if(stack.isEmpty()){
            this.setManaCurrent(0);
            this.setManaMax(0);
            this.setManaRegen(0);
        }else if(this.manaMax == 0){
            NbtCompound nbt = stack.getOrCreateNbt().getCompound("Mana");
            this.setManaCurrent(nbt.getInt("manaCurrent"));
            this.setManaMax(nbt.getInt("manaMax"));
            this.setManaRegen(nbt.getInt("manaRegen"));
        }
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntityMineralSynthesizer be) {
        be.timer++;
        be.regenMana();
        if(be.timer >= be.tickRate){
            be.timer = 0;
            if(be.canSpendMana(be.manaCost)){
                be.spendMana(be.manaCost);
                be.runManaFunction(be);
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new ScreenHandlerMineralSynthesizer(syncId, playerInventory, this);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        NbtCompound invTag = new NbtCompound();
        Inventories.writeNbt(invTag, items);

        NbtCompound manaTag = new NbtCompound();
        ManaUtil.writeManaNbt(manaTag, this);

        nbt.put("Inventory",invTag);
        nbt.put("Mana",manaTag);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.items = DefaultedList.ofSize(5, ItemStack.EMPTY);

        NbtCompound invTag = nbt.getCompound("Inventory");
        Inventories.readNbt(invTag, items);

        NbtCompound manaTag = nbt.getCompound("Mana");
        ManaUtil.readManaNbt(manaTag, this);

        this.updateManaCell();
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

    @Override
    public boolean hasManaCell() {
        return !this.items.get(0).isEmpty();
    }

    @Override
    public boolean hasManaStorage() {
        return false;
    }

    @Override
    public int getManaCurrent() {
        return this.manaCurrent;
    }

    @Override
    public void setManaCurrent(int manaCurrent) {
        this.manaCurrent = manaCurrent;
    }

    @Override
    public int getManaMax() {
        return this.manaMax;
    }

    @Override
    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    @Override
    public int getManaRegen() {
        return this.manaRegen;
    }

    @Override
    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }

    @Override
    public float getManaRatio() {
        if(this.manaMax > 0){
            return (float)(this.manaCurrent / this.manaMax);
        }
        return 0;
    }

    @Override
    public void regenMana() {
        this.addMana(this.manaRegen);
    }

    @Override
    public void addMana(int amount) {
        this.manaCurrent = Math.min(amount, this.manaMax);
    }

    @Override
    public boolean canSpendMana(int amount) {
        return this.manaCurrent >= amount;
    }

    @Override
    public int spendMana(int amount) {
        int overload = this.manaCurrent - amount;
        this.manaCurrent = Math.max(0,overload);
        return overload;
    }

    @Override
    public void runManaFunction(BlockEntity blockEntity) {
        BlockEntityMineralSynthesizer be = (BlockEntityMineralSynthesizer)blockEntity;
        ItemStack itemStackToMimic = be.getStack(1);
        Item itemToMimic = itemStackToMimic.getItem();
        if (itemStackToMimic.isIn(STONE_KEY)) {
            for (int i = 2; i < 5; i++) {
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


}
