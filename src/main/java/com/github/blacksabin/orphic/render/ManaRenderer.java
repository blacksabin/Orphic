package com.github.blacksabin.orphic.render;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ManaRenderer {

    private static final Identifier GAUGE = new Identifier("orphic", "textures/gui/container/mana_gauge_base.png");
    private static final Identifier MANA_CELL_SLOT = new Identifier("orphic", "textures/gui/container/mana_cell_item_slot.png");

    // Draws both the graphic for the Mana Bar and the accompanying Mana Cell Item Slot
    public static void drawManaGauge(MatrixStack matrices, int x, int y){
        RendererUtil.renderTexture(matrices,GAUGE,x,y,21,47 );
        drawManaCellSlot(matrices, x+1, y+48);
    }

    // Draws the specific graphic for the item slot filtered for Mana Cell items.
    public static void drawManaCellSlot(MatrixStack matrices, int x, int y){
        RendererUtil.renderTexture(matrices,MANA_CELL_SLOT,x,y,18,18 );
    }

}
