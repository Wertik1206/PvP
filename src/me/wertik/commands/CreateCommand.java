package me.wertik.commands;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import me.wertik.main.Main;
import me.wertik.main.Utils;
import org.bukkit.entity.Player;

public class CreateCommand extends SubCommand {

    private Main plugin = Main.getInstance();

    @Override
    public String args() {
        return "<name>";
    }

    private Utils uts = new Utils();
    private WorldEditPlugin we = plugin.getWorldEdit();
    private WorldGuardPlugin wg = plugin.getWorldGuard();

    @Override
    public void onCommand(Player p, String[] args) {

        p.sendMessage("Create command executed!");

        if (args.length == 2) {

            Selection sel = we.getSelection(p);

            if (sel == null) {
                p.sendMessage("Please make a selection first.");
                return;
            }

            ProtectedCuboidRegion region = uts.createArena(sel, p, args[1]);

            p.sendMessage("Region maybe added!");
        } else
            p.sendMessage("/pvp create <name>");
    }

    @Override
    public String name() {
        return "create";
    }

}
