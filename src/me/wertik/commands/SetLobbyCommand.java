package me.wertik.commands;

import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.entity.Player;

public class SetLobbyCommand extends SubCommand {

    private Utils uts = new Utils();

    @Override
    public String name() {
        return "setlobby";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        Arena arena = uts.getArena(args[1], p.getWorld());

        arena.setLoby(p.getLocation());

        p.sendMessage("Probably set!");
    }

    @Override
    public String args() {
        return "<arena>";
    }
}
