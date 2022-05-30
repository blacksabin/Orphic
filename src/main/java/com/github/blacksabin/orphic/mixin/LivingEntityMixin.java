package com.github.blacksabin.orphic.mixin;

import com.github.blacksabin.orphic.anima.AnimaComponent;
import com.github.blacksabin.orphic.anima.AnimaPropertiesContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements AnimaComponent {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private final AnimaPropertiesContainer animaPropertiesContainer = new AnimaPropertiesContainer();

    public AnimaPropertiesContainer orphic$getAnimaProperties(){
        return this.animaPropertiesContainer;
    }

    @Inject(at = @At("TAIL"), method = "writeCustomDataToNbt")
    private void writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci){
        NbtCompound newNbt = new NbtCompound();
        this.animaPropertiesContainer.writeAnimaToNbt(newNbt);
        nbt.put("Anima",newNbt);
    }

    @Inject(at = @At("TAIL"), method = "readCustomDataFromNbt")
    private void readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci){
        NbtCompound newNbt = nbt.getCompound("Anima");
        this.animaPropertiesContainer.readAnimaFromNbt(newNbt);
    }

/*
    @Shadow
    public abstract float getHealth();

    @Unique
    private DamageSource currentSource;

    @Inject(method = "damage", at = @At(value = "HEAD"))
    void setCurrentSource(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        currentSource = source;
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isDead()Z", ordinal = 1))
    void haema$setDeadVampireAsKilled(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        //noinspection ConstantConditions
        if (((Object) this) instanceof PlayerEntity && VampirableKt.isVampire((LivingEntity) (Object) this)
                && source != null && VampirableKt.getAbilityLevel((LivingEntity) (Object) this, AbilityModule.INSTANCE.getIMMORTALITY()) > 0) {
            if (this.getHealth() <= 0 && DamageSourceModule.INSTANCE.isEffectiveAgainstVampires(source, this.world)) {
                VampirableKt.getVampireComponent((LivingEntity) (Object) this).setAbsoluteBlood(0.0);
                VampirableKt.setKilled((LivingEntity) (Object) this, true);
            }
        }
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Hand;values()[Lnet/minecraft/util/Hand;"), cancellable = true)
    void haema$keepVampireAlive(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        //noinspection ConstantConditions
        if (((Object) this) instanceof PlayerEntity && VampirableKt.isVampire((LivingEntity) (Object) this)
                && source != null && (VampirableKt.getAbilityLevel((LivingEntity) (Object) this, AbilityModule.INSTANCE.getIMMORTALITY()) > 0)) {
            if (!(this.getHealth() <= 0 && DamageSourceModule.INSTANCE.isEffectiveAgainstVampires(source, this.world))) {
                cir.setReturnValue(true);
            }
        }
    }

    @Redirect(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isSleeping()Z"))
    boolean dontWakeForFeeding(LivingEntity livingEntity) {
        return livingEntity.isSleeping() && currentSource != BloodLossDamageSource.Companion.getInstance();
    }

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;setPose(Lnet/minecraft/entity/EntityPose;)V"))
    void alertVampireHuntersToDeath(DamageSource source, CallbackInfo ci) {
        if (source == BloodLossDamageSource.Companion.getInstance() && world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(new DustParticleEffect(DustParticleEffect.RED, 3.0f), getX(), getY()+1, getZ(), 30, 1.0, 1.0, 1.0, 0.1);

            if (random.nextDouble() < world.getGameRules().get(HaemaGameRules.INSTANCE.getVampireHunterNoticeChance()).get()) {
                //noinspection ConstantConditions
                if ((Object) this instanceof ServerPlayerEntity) {
                    VampireHunterTriggerCriterion.INSTANCE.trigger((ServerPlayerEntity) (Object) this);
                }
                VampireHunterSpawner.Companion.getInstance().trySpawnNear((ServerWorld) world, random, getBlockPos());
            }
        }
    }


    @Inject(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = ))
*/
}
