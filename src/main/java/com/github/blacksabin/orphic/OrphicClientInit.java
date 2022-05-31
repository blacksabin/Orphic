package com.github.blacksabin.orphic;

import com.github.blacksabin.orphic.anima.screens.ScreenAnimaModifier;
import com.github.blacksabin.orphic.screens.ScreenMineralSynthesizer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class OrphicClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(OrphicInit.SCREEN_HANDLER_ANIMA_MODIFIER, ScreenAnimaModifier::new);
        ScreenRegistry.register(OrphicInit.SCREEN_HANDLER_MACHINE, ScreenMineralSynthesizer::new);
    }



}
