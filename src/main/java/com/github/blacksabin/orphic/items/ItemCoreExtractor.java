package com.github.blacksabin.orphic.items;

import com.github.blacksabin.orphic.common.BaseItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

import static com.github.blacksabin.orphic.OrphicInit.ITEM_ANIMA_CORE;

public class ItemCoreExtractor extends BaseItem {


    public ItemCoreExtractor(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if(!user.world.isClient() && entity.getHealth() <= 5 && !entity.isDead()){
            AttributeContainer entityAttributes = entity.getAttributes();
            float maxHP = 1;
            float damage = 0.5f;
            if(entityAttributes.hasAttribute(EntityAttributes.GENERIC_MAX_HEALTH)){
                maxHP = (float)entity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
            }
            if(entityAttributes.hasAttribute(EntityAttributes.GENERIC_ATTACK_DAMAGE)){
                damage = (float)entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
            }
            int maxAnima = (int) (maxHP * (damage + 1));
            int rand = (int)(Math.random() * 10) + 1;

            entity.kill();

            ItemStack newCore = new ItemStack(ITEM_ANIMA_CORE);
            NbtCompound freshTag = new NbtCompound();
            freshTag.putInt("manaCurrent",0);
            freshTag.putInt("manaMax",maxAnima);
            freshTag.putInt("manaRegen",rand);
            NbtCompound masterNbt = newCore.getOrCreateNbt();
            masterNbt.put("Anima",freshTag);
            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            newCore.writeNbt(masterNbt);
            user.giveItemStack(newCore);

            user.playSound(SoundEvents.BLOCK_WOOL_BREAK, 1.0F, 1.0F);
            return ActionResult.SUCCESS;

        }
        return ActionResult.PASS;
    }




}
