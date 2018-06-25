package me.wertik.commands;

import me.wertik.listeners.InventoryListener;
import me.wertik.main.ConfigManager;
import me.wertik.main.Utils;
import me.wertik.objects.Arena;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryCommand extends SubCommand {

    private static ConfigManager cfm = new ConfigManager();
    private static Utils uts = new Utils();

    @Override
    public String name() {
        return "setinv";
    }

    @Override
    public void onCommand(Player p, String[] args) {

        p.sendMessage("Setinventory command executed!");

        Inventory inv;

        if (args.length == 3) {
            if (args[2].equalsIgnoreCase("my")) {

                inv = Bukkit.createInventory(null, 45);

                ItemStack[] cts = p.getInventory().getContents();

                inv.setContents(cts);
            } else if (args[2].equalsIgnoreCase("test")) {

                Arena a = uts.getArena(args[1], p.getWorld());
                a.retrieveInventory(p);

                p.sendMessage("Inv test!");
                return;
            } else
                return;

        } else {

            ConfigurationSection isec = cfm.af().getConfigurationSection(args[1] + ".Inventory");

            inv = uts.convertIHash(uts.getISectionH(isec));

        }

        InventoryListener.pls.put(p.getName(), uts.getArena(args[1], p.getWorld()));

        p.openInventory(inv);
    }

    @Override
    public String args() {
        return "<arena> (my/test)";
    }
}
