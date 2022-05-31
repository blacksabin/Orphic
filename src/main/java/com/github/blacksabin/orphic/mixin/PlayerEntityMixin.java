package com.github.blacksabin.orphic.mixin;


import com.github.blacksabin.orphic.OrphicInit;
import com.github.blacksabin.orphic.anima.hungermanagers.MasterHungerManager;
import com.github.blacksabin.orphic.common.InventoryUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow
    protected HungerManager hungerManager;

    @Shadow public abstract PlayerInventory getInventory();

    @Shadow public abstract ItemCooldownManager getItemCooldownManager();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    public void initOrphicHunger(World world, BlockPos pos, float yaw, GameProfile profile, CallbackInfo ci){
        this.hungerManager = new MasterHungerManager();
    }


    @ModifyVariable(method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentsToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"),
            argsOnly = true,
            require = 1,
            index = 2
    )
    float forcefieldEffect(float amount, DamageSource source) {
        List<ItemStack> canBlock = InventoryUtil.find(this.getInventory(), OrphicInit.ITEM_ATU_CHARM);
        if(canBlock.size() > 0 && !this.getItemCooldownManager().isCoolingDown(OrphicInit.ITEM_ATU_CHARM)){
            this.getItemCooldownManager().set(OrphicInit.ITEM_ATU_CHARM,(int)Math.ceil(20*amount));
            return 0f;
        }else{
            return amount;
        }
    }
    /*
    public boolean contains(ItemStack stack)
    public PlayerInventory getInventory() {
        return this.inventory;
    }
    public int getSlotWithStack(ItemStack stack) {
        for(int i = 0; i < this.main.size(); ++i) {
            if (!((ItemStack)this.main.get(i)).isEmpty() && ItemStack.canCombine(stack, (ItemStack)this.main.get(i))) {
                return i;
            }
        }

        return -1;
    }

    public int indexOf(ItemStack stack) {
        for(int i = 0; i < this.main.size(); ++i) {
            ItemStack itemStack = (ItemStack)this.main.get(i);
            if (!((ItemStack)this.main.get(i)).isEmpty() && ItemStack.canCombine(stack, (ItemStack)this.main.get(i)) && !((ItemStack)this.main.get(i)).isDamaged() && !itemStack.hasEnchantments() && !itemStack.hasCustomName()) {
                return i;
            }
        }

        return -1;
    }

     */

}




    /*

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    void addOrphicHungerManager(NbtCompound tag, CallbackInfo ci) {
        if (tag.contains("foodLevel")) {
            nbt = tag;
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tick()V"))
    void orphicTick(CallbackInfo ci) {
        if (!Float.isFinite(getHealth()) || !Float.isFinite(getAbsorptionAmount()) || getHealth() < 0 || getAbsorptionAmount() < 0) {
            setAbsorptionAmount(0.0f);
            setHealth(0.0f);
        }
        if (VampirableKt.isVampire(this) && !isDead()) {
            if (!this.world.isClient
                    && VampireBurningEvents.INSTANCE.getTRIGGER().invoker().willVampireBurn((PlayerEntity) (Object) this, world).get()
                    && VampireBurningEvents.INSTANCE.getVETO().invoker().willVampireBurn((PlayerEntity) (Object) this, world).get()
            ) {
                this.addStatusEffect(new StatusEffectInstance(SunlightSicknessEffect.Companion.getInstance(), 10, 0, false, false, true));
            }
        }
    }

    @ModifyVariable(method = "applyDamage",
            at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;applyEnchantmentsToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"),
            argsOnly = true,
            require = 1,
            index = 2
    )
    float tweakDamageOrphic(float amount, DamageSource source) {
        float result = VampirableKt.isVampire(this) && DamageSourceModule.INSTANCE.isEffectiveAgainstVampires(source, world) ?
                amount * 1.25f
                : amount;

        return Float.isFinite(result) ? result : amount;
    }

    @Redirect(method = "isInvulnerableTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z", ordinal = 1))
    boolean makeVampiresImmuneToFalling(GameRules gameRules, GameRules.Key<GameRules.BooleanRule> rule) {
        return gameRules.getBoolean(rule) && !(VampirableKt.isVampire(this) && VampirableKt.getAbilityLevel(this, AbilityModule.INSTANCE.getDASH()) >= 3);
    }

    @Redirect(method = "isInvulnerableTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z", ordinal = 0))
    boolean makeVampiresImmuneToDrowning(GameRules gameRules, GameRules.Key<GameRules.BooleanRule> rule) {
        return gameRules.getBoolean(rule) && (!VampirableKt.isVampire(this) || gameRules.getBoolean(HaemaGameRules.INSTANCE.getVampiresDrown()));
    }

    @Override // Immortality goes here
    public boolean isDead() {
        if (VampirableKt.isVampire(this) && VampirableKt.getAbilityLevel(this, AbilityModule.INSTANCE.getIMMORTALITY()) > 0)
            return super.isDead() && VampirableKt.isKilled(this);
        return super.isDead();
    }

    @Override // Immortality also goes here
    public boolean isAlive() {
        return VampirableKt.isVampire(this) ? !isDead() : super.isAlive();
    }
*/
