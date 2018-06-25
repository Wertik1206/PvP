package me.wertik.listeners;

import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClickListener implements Listener {

    private Utils uts = new Utils();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {

        e.getPlayer().sendMessage("Click Listener fired!");

        if (e.getClickedBlock() == null)
            return;


        if (uts.getJoinBlocks(e.getPlayer().getWorld()).contains(e.getClickedBlock().getLocation())) {

            e.getPlayer().sendMessage("Clicked my block tho!");

            Location loc1 = e.getClickedBlock().getLocation();
            Location loc = new Location(e.getPlayer().getWorld(), 0,0,0);

            // parser
            loc.setX((int)loc1.getX());
            loc.setY((int)loc1.getY());
            loc.setZ((int)loc1.getZ());

            Arena a = uts.getArena(loc, e.getPlayer().getWorld());

            e.getPlayer().sendMessage("Arena: " + a.name());

            a.join(e.getPlayer());

        } else
            e.getPlayer().sendMessage("Not my block!");
    }
}
