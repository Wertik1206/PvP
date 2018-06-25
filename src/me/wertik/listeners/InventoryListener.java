package me.wertik.listeners;

import me.wertik.main.ConfigManager;
import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryListener implements Listener {

    public static HashMap<String, Arena> pls = new HashMap<String, Arena>();
    private static ConfigManager cfm = new ConfigManager();
    private static Utils uts = new Utils();

    public InventoryListener() {
    }

    @EventHandler
    public void invClose(InventoryCloseEvent e) {

        if (pls.containsKey(e.getPlayer().getName())) {

            e.getPlayer().sendMessage("Processing!");

            Arena a = pls.get(e.getPlayer().getName());

            ConfigurationSection isec = cfm.af().createSection(a.name() + ".Inventory");

            uts.saveISection(isec, uts.convertHash(e.getInventory()));

            InventoryListener.pls.remove(e.getPlayer().getName());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        e.getWhoClicked().sendMessage("Slot: " + e.getSlot() + " Type: " + e.getInventory().getItem(e.getSlot()));
    }
}
