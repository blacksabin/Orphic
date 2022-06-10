package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.anima.AnimaComponent;
import com.github.blacksabin.orphic.screens.ScreenHandlerAnimaModifier;
import com.github.blacksabin.orphic.common.BaseItem;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class ItemVitalicOriel extends BaseItem {


    public ItemVitalicOriel(Settings settings) {
        super(settings);
    }


    public void openAnimaModiferScreen(PlayerEntity user, LivingEntity entity){
        if(!user.getWorld().isClient()){

            NbtCompound nbt = new NbtCompound();
            user.writeCustomDataToNbt(nbt);
            user.openHandledScreen(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                    NbtCompound nbt = new NbtCompound();
                    ((AnimaComponent)entity).orphic$getAnimaProperties().writeAnimaToNbt(nbt);
                    buf.writeNbt(nbt);
                }

                @Override
                public Text getDisplayName() {
                    return Text.translatable("item.orphic.vitalic_oriel");
                }

                @Override
                public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity user) {
                    return new ScreenHandlerAnimaModifier(syncId, inv, ((AnimaComponent)entity).orphic$getAnimaProperties());
                }
            });

        }
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if(user.isSneaking()){
            this.openAnimaModiferScreen(user, user);
        }
        return TypedActionResult.pass(user.getStackInHand(hand));

    }
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        this.openAnimaModiferScreen(user, entity);
        return ActionResult.SUCCESS;
    }




}
