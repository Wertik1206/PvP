package me.wertik.commands;

import me.wertik.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;

public class CommandManager implements CommandExecutor {

    private Main plugin = Main.getInstance();

    public static ArrayList<SubCommand> scmds = new ArrayList<SubCommand>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command is only for players bcs of block to block selection.");
            return true;
        }
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("pvp")) {

            if (args.length == 0) {

                p.sendMessage("Not enough arguments.");
                return true;
            }

            if (scmds.contains(this.get(args[0]))) {

                this.get(args[0]).onCommand(p, args);

            } else
                p.sendMessage("Unknown command!");
        }
        return true;
    }

    public void setup() {
        plugin.getCommand("pvp").setExecutor(this);

        // Command handler list
        scmds.add(new CreateCommand());
        scmds.add(new AddDropCommand());
        scmds.add(new InventoryCommand());
        scmds.add(new RemoveCommand());
        scmds.add(new HelpCommand());
        scmds.add(new AddSpawnPointCommand());
        scmds.add(new SetJoinBlockCommand());
        scmds.add(new SetLobbyCommand());
        scmds.add(new SetHeldSlotCommand());
        scmds.add(new EscortBlockCommand());

        Bukkit.getLogger().info("Settings up executors.");
    }

    public SubCommand get(String name) {

        Iterator<SubCommand> scmds1 = scmds.iterator();

        while (scmds1.hasNext()) {

            SubCommand sc = (SubCommand) scmds1.next();

            if (sc.name().equalsIgnoreCase(name)) {
                return sc;
            }

        }
        return null;
    }
}
