package me.wertik.commands;

import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.entity.Player;

public class SetHeldSlotCommand extends SubCommand {

    private static Utils uts = new Utils();

    @Override
    public String name() {
        return "setheld";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        int i = p.getInventory().getHeldItemSlot();

        Arena a = uts.getArena(args[1], p.getWorld());

        a.setHeldSlot(i);

        p.sendMessage("Arena " + a.name() + " held item set to " + a.getHeldSlot());
    }

    @Override
    public String args() {
        return "<arena>";
    }
}
