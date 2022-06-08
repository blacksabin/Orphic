package com.github.blacksabin.orphic.screens;

import com.github.blacksabin.orphic.render.RendererUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.github.blacksabin.orphic.render.ManaRenderer.drawManaGauge;

public class ScreenMineralSynthesizer extends HandledScreen<ScreenHandlerMineralSynthesizer> {

    //A path to the gui texture. In this example we use the texture from the dispenser
    private static final Identifier TEXTURE = new Identifier("orphic", "textures/gui/container/inventory_base.png");
    private static final Identifier GAUGE = new Identifier("orphic", "textures/gui/container/mana_gauge_base.png");
    private static final Identifier ITEM_SLOT = new Identifier("orphic", "textures/gui/container/item_slot.png");
    private static final Identifier MANA_CELL_SLOT = new Identifier("orphic", "textures/gui/container/mana_cell_item_slot.png");


    public ScreenMineralSynthesizer(ScreenHandlerMineralSynthesizer handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        // Background
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        // Item slots
        ScreenHandlerMineralSynthesizer thisScreenHandler = this.getScreenHandler();
        for(Slot slot : thisScreenHandler.slots){
            RendererUtil.renderTexture(matrices,ITEM_SLOT,x + slot.x - 1, y + slot.y - 1,18,18 );
        }

        // Mana gauge
        drawManaGauge(matrices,x+4,y+4);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices,mouseX,mouseY,delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }


    @Override
    protected void init() {
        super.init();
        // Center the title
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }

}




