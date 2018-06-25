package me.wertik.commands;

import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String name();

    public abstract void onCommand(Player p, String[] args);

    public abstract  String args();

}
