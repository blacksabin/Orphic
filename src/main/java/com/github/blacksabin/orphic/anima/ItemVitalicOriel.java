package com.github.blacksabin.orphic.anima;

import com.github.blacksabin.orphic.anima.screens.ScreenHandlerAnimaModifier;
import com.github.blacksabin.orphic.common.BaseItem;
import com.github.blacksabin.orphic.mixin.LivingEntityMixin;
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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import javax.annotation.Nullable;

import java.util.UUID;


public class ItemVitalicOriel extends BaseItem {


    public ItemVitalicOriel(Settings settings) {
        super(settings);
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {

        if(!user.getWorld().isClient()){

            NbtCompound nbt = new NbtCompound();
            user.writeCustomDataToNbt(nbt);
            user.openHandledScreen(new ExtendedScreenHandlerFactory() {
                @Override
                public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
                    NbtCompound nbt = new NbtCompound();
                    ((AnimaInterface)entity).orphic$getAnimaProperties().writeAnimaToNbt(nbt);
                    buf.writeNbt(nbt);
                }

                @Override
                public Text getDisplayName() {
                    return new TranslatableText("item.orphic.vitalic_oriel");
                }

                @Override
                public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity user) {
                    return new ScreenHandlerAnimaModifier(syncId, inv, ((AnimaInterface)entity).orphic$getAnimaProperties());
                }
            });

        }
        return ActionResult.SUCCESS;
    }




}
