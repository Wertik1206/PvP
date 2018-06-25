package me.wertik.main;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.wertik.commands.CommandManager;
import me.wertik.listeners.ClickListener;
import me.wertik.listeners.DeathListener;
import me.wertik.listeners.InventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin {

    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }

    public CommandManager cman;

    public ConfigManager cfman;

    @Override
    public void onEnable() {

        setInstance(this);

        if (this.getWorldGuard() == null) {
            Bukkit.getLogger().warning("Missing worldGuard, shuting down!");
            this.getPluginLoader().disablePlugin(this);
            return;
        } else
            Bukkit.getLogger().info("WorldGuard API hooked!");

        if (this.getWorldEdit() == null) {
            Bukkit.getLogger().warning("Missing worldEdit, shuting down!");
            this.getPluginLoader().disablePlugin(this);
            return;
        } else
            Bukkit.getLogger().info("WorldEdit API hooked!");

        // getServer().getPluginManager().registerEvents(new me.wertik., this);


        // files
        File arenas = new File(getDataFolder() + "/arenas.yml");
        YamlConfiguration ac = YamlConfiguration.loadConfiguration(arenas);

        if (!arenas.exists()) {
            try {
                ac.options().copyDefaults(true);
                ac.save(arenas);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        File cffile = new File(getDataFolder() + "/config.yml");
        FileConfiguration cf = getConfig();

        if (!cf.contains("config")) {

            cf.createSection("config");

            cf.options().copyDefaults(true);
            saveConfig();
        }

        cman = new CommandManager();

        cfman = new ConfigManager();

        cfman.setPaths();

        this.getServer().getPluginManager().registerEvents(new DeathListener(), this);
        this.getServer().getPluginManager().registerEvents(new ClickListener(), this);
        this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);


        cman.setup();
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldGuard");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public WorldEditPlugin getWorldEdit() {
        Plugin plugin = getServer().getPluginManager().getPlugin("WorldEdit");

        // WorldGuard may not be loaded
        if (plugin == null || !(plugin instanceof WorldEditPlugin)) {
            return null;
        }

        return (WorldEditPlugin) plugin;
    }

    @Override
    public void onDisable() {

    }
}
