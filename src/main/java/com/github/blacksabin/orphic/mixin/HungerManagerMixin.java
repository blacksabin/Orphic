package com.github.blacksabin.orphic.mixin;


import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HungerManager.class)
public class HungerManagerMixin {

    @Unique
    private String mode = "default";}
    /*
        "default": Regular hunger system.
        "energy": Use an energy system. Exhaustion unchanged, but health regen is slower but consistent.
        "blood": Healing and exhaustion don't use blood. Vulnerable damage uses blood.
        "lead_belly": No saturation, high max hunger. Bar shows a percentage of full.
        "anima": All actions consume anima. Anima regens based on the AnimaCore.


    @Unique private PlayerEntity player;

    @Inject(method = "getFoodLevel", at = @At("HEAD"), cancellable = true)
    void haema$getBloodLevel(CallbackInfoReturnable<Integer> context) {
        if(this.mode == "energy"){
            context.setReturnValue((int) AnimaUtil.)
        }
        if (this.isVampire) {
            context.setReturnValue((int) Math.floor(VampirableKt.getVampireComponent(player).getBlood()));
        }
    }

    @Inject(method = "getSaturationLevel", at = @At("HEAD"), cancellable = true)
    void haema$fakeSaturationLevel(CallbackInfoReturnable<Float> cir) {
        if (this.isVampire) {
            cir.setReturnValue(0f);
        }
    }

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    void haema$setVampire(PlayerEntity player, CallbackInfo context) {
        this.isVampire = VampirableKt.isVampire(player);
        this.player = player;

        if (this.isVampire) {
            context.cancel();
        }
    }

}
*/