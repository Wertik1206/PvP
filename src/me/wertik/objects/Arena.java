package me.wertik.objects;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.wertik.main.ConfigManager;
import me.wertik.main.Main;
import me.wertik.main.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arena {

    private Main plugin = Main.getInstance();
    private ConfigManager cfm = new ConfigManager();
    private Utils uts = new Utils();
    private WorldGuardPlugin wg = plugin.getWorldGuard();

    private String name;
    private Inventory inv;
    private List<ItemStack> drops;
    private ProtectedRegion region;
    private World w;

    private static HashMap<String, ItemStack[]> invs = new HashMap<>();
    private static HashMap<String, Integer> food = new HashMap<>();
    private static HashMap<String, Double> health = new HashMap<>();
    private List<Player> pls = new ArrayList<>();

    public Arena(ProtectedRegion region, World w) {
        this.region = region;
        this.w = w;
        this.name = region.getId().replace("arena_", "");
        this.inv = Bukkit.createInventory(null, 45);
    }

    public String name() {
        return name;
    }

    public ProtectedRegion region() {
        return region;
    }

    public World world() {
        return w;
    }

    public void addDrop(ItemStack item) {

    }

    public void removeDrop(String id) {

    }

    public void delete() {
        wg.getRegionManager(w).removeRegion(region.getId());
        ConfigManager.af.set(name, null);

        cfm.saveArenas();
    }

    public void setInventory(Inventory inv) {

        ConfigurationSection sec = cfm.af().getConfigurationSection(name).getConfigurationSection("Inventory");

        HashMap<ItemStack, Integer> ninv = new HashMap<>();

        for (String key : sec.getKeys(false)) {

            int i = Integer.parseInt(key.replace("IS", ""));

            ItemStack is = sec.getItemStack(key);

            ninv.put(is, i);
        }

        this.inv = uts.convertIHash(ninv);
    }

    public void addSpawnPoint(Location loc) {

        ConfigurationSection spsec = checkSection(ConfigManager.af.getConfigurationSection(name), "Spawnpoints");

        ConfigurationSection spc = spsec.createSection("S" + (getSpawnpoints().size()));

        spc.set("X", loc.getX());
        spc.set("Y", loc.getY());
        spc.set("Z", loc.getZ());
        spc.set("W", loc.getWorld().getName());

        cfm.saveArenas();
    }

    public List<Location> getSpawnpoints() {

        List<Location> sps = new ArrayList<>();

        ConfigurationSection spsec = ConfigManager.af.getConfigurationSection(name + ".Spawnpoints");

        List<String> spcs = new ArrayList<>(spsec.getKeys(false));

        for (String spc : spcs) {

            double x = spsec.getDouble(spc + ".X");
            double y = spsec.getDouble(spc + ".Y");
            double z = spsec.getDouble(spc + ".Z");
            World world = plugin.getServer().getWorld(spsec.getString(spc + ".W"));

            sps.add(new Location(world, x, y, z));
        }
        return sps;
    }

    public void removeSpawnPoint(String id) {

    }

    public void setLoby(Location loc) {

        ConfigurationSection as = checkSection(ConfigManager.af.getConfigurationSection(name), "Lobby");

        as.set("X", loc.getX());
        as.set("Y", loc.getY());
        as.set("Z", loc.getZ());
        as.set("W", loc.getWorld().getName());

        cfm.saveArenas();
    }

    public void setEscortBlock(Location loc) {

        ConfigurationSection sec = checkSection(ConfigManager.af.getConfigurationSection(name), "EscortBlock");

        sec.set("X", loc.getX());
        sec.set("Y", loc.getY());
        sec.set("Z", loc.getZ());
        sec.set("W", loc.getWorld().getName());

        cfm.saveArenas();

    }

    public Location getEscortBlock() {

        ConfigurationSection as = ConfigManager.af.getConfigurationSection(name + ".EscortBlock");

        double x = as.getDouble("X");
        double y = as.getDouble("Y");
        double z = as.getDouble("Z");
        World world = plugin.getServer().getWorld(as.getString("W"));

        return new Location(world, x, y, z);
    }

    public Location getLobby() {

        ConfigurationSection as = ConfigManager.af.getConfigurationSection(name + ".Lobby");

        double x = as.getDouble("X");
        double y = as.getDouble("Y");
        double z = as.getDouble("Z");
        World world = plugin.getServer().getWorld(as.getString("W"));

        return new Location(world, x, y, z);
    }

    public void showSpawnPoints() {

    }

    public ProtectedRegion getRegion() {
        return region;
    }

    public void setJoinBlock(Location loc) {

        ConfigurationSection as = checkSection(ConfigManager.af.getConfigurationSection(name), "JoinBlock");

        as.set("X", loc.getX());
        as.set("Y", loc.getY());
        as.set("Z", loc.getZ());
        as.set("W", loc.getWorld().getName());

        cfm.saveArenas();
    }

    public void setHeldSlot(int i) {

        ConfigManager.af.set(name + ".HeldSlot", i);

        cfm.saveArenas();
    }

    public int getHeldSlot() {
        if (ConfigManager.af.getConfigurationSection(name).contains("HeldSlot"))
            return ConfigManager.af.getInt(name + ".HeldSlot");
        else
            return 0;
    }

    public Location getJoinBlock() {

        ConfigurationSection as = ConfigManager.af.getConfigurationSection(name + ".JoinBlock");

        double x = as.getDouble("X");
        double y = as.getDouble("Y");
        double z = as.getDouble("Z");
        World world = plugin.getServer().getWorld(as.getString("W"));

        return new Location(world, x, y, z);
    }

    public ConfigurationSection checkSection(ConfigurationSection up, String s) {

        ConfigurationSection sec;

        if (up.contains(s))
            sec = up.getConfigurationSection(s);
        else
            sec = up.createSection(s);

        return sec;
    }

    private static HashMap<Player, Integer> counter = new HashMap<>();
    private static HashMap<Player, GameMode> gm = new HashMap<>();

    public void join(Player p) {

        p.sendMessage("Joining protocol tho!");

        pls.add(p);

        Arena arena = uts.getArena(this.name, p.getWorld());

        food.put(p.getName(), p.getFoodLevel());
        health.put(p.getName(), p.getHealth());

        p.setFoodLevel(20);
        p.setHealth(20);

        gm.put(p, p.getGameMode());

        p.setGameMode(GameMode.SURVIVAL);

        savePlayerInventory(p);
        setPlayerInventory(p);
        teleportToSpawn(arena, p);

        new BukkitRunnable() {
            @Override
            public void run() {

                if (pls.size() != 0) {

                    Location loc = p.getLocation();

                    loc.setX((int) loc.getX());
                    loc.setZ((int) loc.getZ());

                    if (loc.getX() == getEscortBlock().getX() && loc.getZ() == getEscortBlock().getZ()) {

                        if (counter.containsKey(p))
                            counter.put(p, counter.get(p) + 1);
                        else
                            counter.put(p, 1);

                        p.sendMessage("On the escort block! " + counter.get(p));

                        if (counter.get(p) == 5) {
                            leave(p);
                            p.sendMessage("Escorted!");
                            counter.remove(p);
                        }

                    } else
                        counter.remove(p);
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 20);
    }

    public void leave(Player p) {

        p.sendMessage("Leaving protocol bro!");

        p.setHealth(health.get(p.getName()));
        p.setFoodLevel(food.get(p.getName()));

        food.remove(p.getName());
        health.remove(p.getName());

        retrieveInventory(p);
        teleportToLobby(p);

        p.setGameMode(gm.get(p));
        gm.remove(p);

        p.sendMessage("You lost! Looser! Your inventory is back, huh.");
    }

    public void teleportToSpawn(Arena arena, Player p) {

        List<Location> ss = arena.getSpawnpoints();

        double i = Math.random() * ss.size();

        i = i - (i % 1);

        p.teleport(ss.get((int) i));

        p.sendMessage("Probably teleported to spawnpoint n." + i);
    }

    public void savePlayerInventory(Player p) {

        p.sendMessage("Saving!");

        if (!invs.containsKey(p.getName()))
            invs.put(p.getName(), p.getInventory().getContents());

        Inventory inv = Bukkit.createInventory(null, 45);

        inv.setContents(invs.get(p.getName()));

        p.openInventory(inv);

        p.sendMessage("Opened!");
    }

    public void setPlayerInventory(Player p) {

        p.sendMessage("Seting!");

        p.getInventory().clear();

        HashMap<ItemStack, Integer> ninv = uts.getISectionH(ConfigManager.af.getConfigurationSection(name + ".Inventory"));

        // here
        if (this.inv.getItem(36) != null)
            p.getInventory().setHelmet(this.inv.getItem(36));
        if (this.inv.getItem(37) != null)
            p.getInventory().setChestplate(this.inv.getItem(37));
        if (this.inv.getItem(38) != null)
            p.getInventory().setLeggings(this.inv.getItem(38));
        if (this.inv.getItem(39) != null)
            p.getInventory().setBoots(this.inv.getItem(39));
        if (this.inv.getItem(40) != null)
            p.getInventory().setItemInOffHand(this.inv.getItem(40));

        p.getInventory().setHeldItemSlot(getHeldSlot());

        int i = 0;
        for (ItemStack is : ninv.keySet()) {
            i++;
            if (i == 35)
                return;
            p.getInventory().setItem(ninv.get(is), is);
        }
    }

    public void retrieveInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setContents(invs.get(p.getName()));
        invs.remove(p.getName());
    }

    public void teleportToLobby(Player p) {
        p.teleport(uts.getArena(name, p.getWorld()).getLobby());
    }
}