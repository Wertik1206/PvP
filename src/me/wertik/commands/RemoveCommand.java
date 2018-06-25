package me.wertik.commands;

import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.entity.Player;

public class RemoveCommand extends SubCommand {

    private Utils uts = new Utils();

    @Override
    public String name() {
        return "delete";
    }

    @Override
    public String args() {
        return "<arena>";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        p.sendMessage("Delete command executed!");

        Arena a = uts.getArena(args[1], p.getWorld());

        a.delete();

        p.sendMessage("Deleted, i guess?");
    }
}