package com.github.iiscoolso123.mcmonster.utils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class GuiUtils {

    public static void drawRect(int left, int top, int right, int bottom, int color) {
        if (left > right) {
            int i = left;
            left = right;
            right = i;
        }

        if (top > bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }

        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();

        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(left, bottom, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(right, bottom, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(right, top, 0).color(red, green, blue, alpha).endVertex();
        worldRenderer.pos(left, top, 0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
}
