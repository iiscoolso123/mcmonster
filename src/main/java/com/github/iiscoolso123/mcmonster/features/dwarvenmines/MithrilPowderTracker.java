package com.github.iiscoolso123.mcmonster.features.dwarvenmines;

import com.github.iiscoolso123.mcmonster.utils.GuiUtils;
import com.github.iiscoolso123.mcmonster.utils.TabListUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.text.NumberFormat;
import java.text.ParseException;

public class MithrilPowderTracker {
    static int mithril = 0;
    static int mithrilGained = 0;
    static int mithrilPerHour = 0;
    static int counter = 0;
    static long startingTime = 0;
    static long elapsedTime;
    static float elapsedHours;
    static public int x = 10;
    static public int y = 10;
    public static boolean showTracker = false;
    NumberFormat nf = NumberFormat.getInstance();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (showTracker) {
            if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;

            Minecraft mc = Minecraft.getMinecraft();
            FontRenderer fr = mc.fontRendererObj;
            ScaledResolution sr = new ScaledResolution(mc);

            int height = 20;
            int width = 200;

            GuiUtils.drawRect(x,y, x + width, y + height, 0x90000000);

            fr.drawString("§2Mithril gain per hour: " + nf.format(((mithrilPerHour + 99) / 100) * 100),x + 5, y + 6, 0xFFFFFF);
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) throws ParseException {
        counter++;
        if (counter % 40 == 0) {
            TabListUtils.parseTabEntries();

            /*List<NetworkPlayerInfo> data = TabListUtils.fetchTabEntries();
            for (int i = 0; i < data.size(); i++) {
                System.out.println(data.get(i).getDisplayName());
            }*/
            if (TabListUtils.mithril != 0) {
                updateMithril(TabListUtils.mithril);
            }
            elapsedTime = System.currentTimeMillis() - startingTime;

            if (elapsedTime != 0) {
                elapsedHours = (float) elapsedTime / (3600 * 1000);
                mithrilPerHour = (int)(mithrilGained / elapsedHours);
            }
        }
    }
    private static void updateMithril(int newMithril) {
        if (mithril != 0) {
            if (newMithril > mithril) {
                mithrilGained += newMithril - mithril;
                mithril = newMithril;
                if (startingTime == 0) {
                    startingTime = System.currentTimeMillis();
                }
            }else {
                mithril = newMithril;
            }
        }else {
            mithril = newMithril;
        }
    }
}
