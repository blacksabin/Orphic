package com.github.blacksabin.orphic.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

public class RendererUtil {

    public static void renderTexture(MatrixStack matrices, double x0, double y0, double width, double height, float u, float v, double regionWidth, double regionHeight, double textureWidth, double textureHeight) {
        double x1 = x0 + width;
        double y1 = y0 + height;
        double z = 0;
        renderTexturedQuad(matrices.peek().getPositionMatrix(),
                x0,
                x1,
                y0,
                y1,
                z,
                (u + 0.0F) / (float) textureWidth,
                (u + (float) regionWidth) / (float) textureWidth,
                (v + 0.0F) / (float) textureHeight,
                (v + (float) regionHeight) / (float) textureHeight);
    }

    private static void renderTexturedQuad(Matrix4f matrix, double x0, double x1, double y0, double y1, double z, float u0, float u1, float v0, float v1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(matrix, (float) x0, (float) y1, (float) z).texture(u0, v1).next();
        bufferBuilder.vertex(matrix, (float) x1, (float) y1, (float) z).texture(u1, v1).next();
        bufferBuilder.vertex(matrix, (float) x1, (float) y0, (float) z).texture(u1, v0).next();
        bufferBuilder.vertex(matrix, (float) x0, (float) y0, (float) z).texture(u0, v0).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

    }

    /**
     * <p>Renders a texture</p>
     * <p>Make sure to link your texture using {@link RenderSystem#setShaderTexture(int, Identifier)} before using this</p>
     *
     * @param matrices The context MatrixStack
     * @param x        The X coordinate
     * @param y        The Y coordinate
     * @param width    The width of the texture
     * @param height   The height of the texture
     */
    public static void renderTexture(MatrixStack matrices, double x, double y, double width, double height) {
        renderTexture(matrices, x, y, width, height, 0, 0, width, height, width, height);
    }

    /**
     * <p>Renders a texture</p>
     * <p>Does the binding for you, call this instead of {@link #renderTexture(MatrixStack, double, double, double, double)} or {@link #renderTexture(MatrixStack, double, double, double, double, float, float, double, double, double, double)} for ease of use</p>
     *
     * @param matrices The context MatrixStack
     * @param texture  The texture to render
     * @param x        The X coordinate
     * @param y        The Y coordinate
     * @param width    The width of the texture
     * @param height   The height of the texture
     */
    public static void renderTexture(MatrixStack matrices, Identifier texture, double x, double y, double width, double height) {
        RenderSystem.setShaderTexture(0, texture);
        renderTexture(matrices, x, y, width, height);
    }


}
