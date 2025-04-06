package com.github.iiscoolso123.mcmonster;

import com.github.iiscoolso123.mcmonster.commands.Commands;
import com.github.iiscoolso123.mcmonster.features.dwarvenmines.MithrilPowderTracker;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = "mcmonster", useMetadata=true)
public class mcmonster {

    Commands commands;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new MithrilPowderTracker());
        commands = new Commands();
    }
}
