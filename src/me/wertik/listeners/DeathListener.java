package me.wertik.listeners;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.wertik.main.Main;
import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DeathListener implements Listener {

    private Main plugin = Main.getInstance();
    private WorldGuardPlugin wg = plugin.getWorldGuard();
    private Utils uts = new Utils();

    @EventHandler
    public void PlayerDamageReceive(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();

            if (uts.isInArena(p)) {
                if ((p.getHealth() - e.getDamage()) <= 0) {

                    e.setCancelled(true);

                    p.setHealth((double) 20);

                    p.setFoodLevel(20);

                    Arena a = uts.getArena(p);

                    a.leave(p);

                    Player killer = (Player) e.getDamager();
                    if (e.getDamager() instanceof Arrow && ((Arrow) e.getDamager()).getShooter() instanceof Player)
                        killer = ((Player) ((Arrow) e.getDamager()).getShooter());

                    Bukkit.broadcastMessage(killer.getName() + ">" + p.getName());
                }
            }
        }
    }
}
