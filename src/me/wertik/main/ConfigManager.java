package me.wertik.main;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigManager {

    private static Main plugin = Main.getInstance();

    public static File cffile;
    public static File affile;
    public static FileConfiguration cf;
    public static YamlConfiguration af;

    public ConfigManager() {}

    public void setPaths() {

        cffile = new File(plugin.getDataFolder()+"/config.yml");
        affile = new File(plugin.getDataFolder()+"/arenas.yml");

        af = YamlConfiguration.loadConfiguration(affile);
    }

    public YamlConfiguration af(){return af;};

    public FileConfiguration cf(){return cf;};

    public void saveConfig() {
        cf.options().copyDefaults(true);
        plugin.saveConfig();
    }

    public void saveArenas() {
        try {
            af.options().copyDefaults(true);
            af.save(affile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateArena(String id, List<Location> spawnpoints, Location lobby, Location joinblock) {

        ConfigurationSection arena = af.createSection(id);

        ConfigurationSection sps = arena.createSection("SpawnPoints");

        int i = 0;

        for (Location loc : spawnpoints) {
            i++;
            ConfigurationSection spss = sps.createSection("S" + i);
            spss.set("X", loc.getX());
            spss.set("Y", loc.getY());
            spss.set("Z", loc.getZ());
            spss.set("W", loc.getWorld());
        }

        ConfigurationSection ls = arena.createSection("Lobby");
        ls.set("X", lobby.getX());
        ls.set("Y", lobby.getY());
        ls.set("Z", lobby.getZ());
        ls.set("W", lobby.getWorld());

        ConfigurationSection jbs = arena.createSection("JoinBlock");
        ls.set("X", joinblock.getX());
        ls.set("Y", joinblock.getY());
        ls.set("Z", joinblock.getZ());
        ls.set("W", joinblock.getWorld());

        saveArenas();
    }
}
