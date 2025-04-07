package com.github.iiscoolso123.mcmonster.commands.dwarvenMines;

import com.github.iiscoolso123.mcmonster.commands.ClientCommandBase;
import com.github.iiscoolso123.mcmonster.features.dwarvenmines.MithrilPowderTracker;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class resetMithrilCommand extends ClientCommandBase {
    public resetMithrilCommand() {
        super("resetmithril tracker");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        MithrilPowderTracker.startingTime = System.currentTimeMillis();
        MithrilPowderTracker.mithrilGained = 0;
    }
}
