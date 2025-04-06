package com.github.iiscoolso123.mcmonster.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class BlockOverlays {
    public static void drawBlockOverlay(BlockPos pos, float red, float green, float blue) {
        Minecraft mc = Minecraft.getMinecraft();

        double x = mc.getRenderManager().viewerPosX;
        double y = mc.getRenderManager().viewerPosY;
        double z = mc.getRenderManager().viewerPosZ;

        AxisAlignedBB boundingBox = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock().getSelectedBoundingBox(Minecraft.getMinecraft().theWorld,pos);
        AxisAlignedBB expandedBoundingBox = expandBoundingBox(boundingBox,0.002);
        AxisAlignedBB translatedBox = expandedBoundingBox.offset(-x,-y,-z);

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770,771,1,0);
        GlStateManager.color(red,green,blue,0.4f);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        drawSolidBoundingBox(translatedBox);

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawSolidBoundingBox(AxisAlignedBB bb) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION);

        worldrenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.minX, bb.minY, bb.maxZ).endVertex();

        worldrenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();

        worldrenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.minZ).endVertex();

        worldrenderer.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();

        worldrenderer.pos(bb.minX, bb.minY, bb.minZ).endVertex();
        worldrenderer.pos(bb.minX, bb.minY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.minX, bb.maxY, bb.minZ).endVertex();

        worldrenderer.pos(bb.maxX, bb.minY, bb.minZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.minZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.maxY, bb.maxZ).endVertex();
        worldrenderer.pos(bb.maxX, bb.minY, bb.maxZ).endVertex();

        tessellator.draw();
    }

    private static AxisAlignedBB expandBoundingBox(AxisAlignedBB original, double expansionFactor) {
        return new AxisAlignedBB(
                original.minX - expansionFactor, // Expand minX
                original.minY - expansionFactor, // Expand minY
                original.minZ - expansionFactor, // Expand minZ
                original.maxX + expansionFactor, // Expand maxX
                original.maxY + expansionFactor, // Expand maxY
                original.maxZ + expansionFactor  // Expand maxZ
        );
    }

}
