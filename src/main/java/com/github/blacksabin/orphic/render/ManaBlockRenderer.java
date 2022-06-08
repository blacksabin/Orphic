package com.github.blacksabin.orphic.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ManaBlockRenderer {


    private static final Identifier GAUGE = new Identifier("orphic", "textures/gui/container/mana_gauge_base.png");
    private static final Identifier ITEM_SLOT = new Identifier("orphic", "textures/gui/container/item_slot.png");
    private static final Identifier MANA_CELL_SLOT = new Identifier("orphic", "textures/gui/container/mana_cell_item_slot.png");

    public static void drawManaGauge(MatrixStack matrices, int x, int y){
        RendererUtil.renderTexture(matrices,GAUGE,x,y,21,47 );
        RendererUtil.renderTexture(matrices,MANA_CELL_SLOT,x+1,y+48,18,18 );
    }

    public static void drawItemSlot(MatrixStack matrices, int x, int y){
        RendererUtil.renderTexture(matrices,ITEM_SLOT,x,y,18,18 );
    }

    public static void drawManaCellSlot(MatrixStack matrices, int x, int y){
        RendererUtil.renderTexture(matrices,MANA_CELL_SLOT,x,y,18,18 );
    }


}
