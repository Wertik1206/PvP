package me.wertik.commands;

import org.bukkit.entity.Player;

public class AddDropCommand extends SubCommand {

    @Override
    public String name() {
        return "addDrop";
    }

    @Override
    public String args() {
        return "";
    }

    @Override
    public void onCommand(Player p, String[] args) {

    p.sendMessage("AddDropComamnd executed!");

    }
}
