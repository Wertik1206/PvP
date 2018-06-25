package me.wertik.commands;

import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {

    private static CommandManager cman = new CommandManager();

    @Override
    public String name() {
        return "help";
    }

    @Override
    public String args() {
        return "";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        p.sendMessage("Help command executed!");
        for (SubCommand sc : CommandManager.scmds){
            p.sendMessage("Command: /pvp " + sc.name() + " " + sc.args());
        }
    }
}
