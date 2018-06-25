package me.wertik.commands;

import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.entity.Player;

public class AddSpawnPointCommand extends SubCommand {

    private Utils uts = new Utils();

    @Override
    public String name() {
        return "addspawn";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        if (uts.isInArena(p)) {

            Arena arena = uts.getArena(p);

            arena.addSpawnPoint(p.getLocation());

            p.sendMessage("Spawnpoint saved to arena " + arena.name());

        }
    }

    @Override
    public String args() {
        return "<arena>";
    }
}
