package me.wertik.commands;

import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class EscortBlockCommand extends SubCommand {

    private Utils uts = new Utils();

    @Override
    public String name() {
        return "setescort";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        p.sendMessage("Setescort command executed!");

        Block b = p.getTargetBlock(null, 100);

        Arena arena = uts.getArena(args[1], p.getWorld());

        arena.setEscortBlock(b.getLocation());

        p.sendMessage("The escort block may be set!");

    }

    @Override
    public String args() {
        return "<arena>";
    }
}
