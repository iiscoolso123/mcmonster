package com.github.iiscoolso123.mcmonster.commands;

import com.github.iiscoolso123.mcmonster.commands.dwarvenMines.changePosCommand;
import com.github.iiscoolso123.mcmonster.commands.dwarvenMines.toggleMithrilCommand;
import net.minecraftforge.client.ClientCommandHandler;

public class Commands {
    public Commands() {
        ClientCommandHandler.instance.registerCommand(new changePosCommand());
        ClientCommandHandler.instance.registerCommand(new toggleMithrilCommand());
    }
}
