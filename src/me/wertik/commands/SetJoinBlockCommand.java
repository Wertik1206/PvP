package me.wertik.commands;

import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class SetJoinBlockCommand extends SubCommand {

    private Utils uts = new Utils();

    @Override
    public String name() {
        return "addblock";
    }

    @Override
    public String args() {
        return "<arena>";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        p.sendMessage("AddBlock command executed!");

        Block b = p.getTargetBlock(null, 100);

        Arena arena = uts.getArena(args[1], p.getWorld());

        arena.setJoinBlock(b.getLocation());

        p.sendMessage("The join block may be set!");
    }
}
