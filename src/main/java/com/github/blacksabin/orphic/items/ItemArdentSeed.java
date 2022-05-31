package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.common.BaseItem;
import com.github.blacksabin.orphic.mixin.RecipeRemainderMixin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class ItemArdentSeed extends BaseItem {

    public ItemArdentSeed(Settings settings) {
        super(settings);
        ((RecipeRemainderMixin)this).setRecipeRemainder(this);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {

        if(!world.isClient()){
            ItemStack mainHandItem = playerEntity.getMainHandStack();
            ItemStack offHandItem = playerEntity.getOffHandStack();
            if(offHandItem.isDamageable() && !offHandItem.getItem().equals(this)){
                NbtCompound ntag = offHandItem.getNbt();
                ntag.putInt("Unbreakable",1);
                offHandItem.writeNbt(ntag);
                if (!playerEntity.getAbilities().creativeMode) {
                    mainHandItem.decrement(1);
                }
                playerEntity.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
                return TypedActionResult.success(playerEntity.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(playerEntity.getStackInHand(hand));
    }

}
