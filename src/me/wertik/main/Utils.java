package me.wertik.main;

import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.wertik.objects.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Utils {

    private Main plugin = Main.getInstance();
    private WorldGuardPlugin wg = plugin.getWorldGuard();
    private ConfigManager cfman = new ConfigManager();

    public Utils() {
    }

    public boolean isInArena(Player p) {

        LocalPlayer lp = wg.wrapPlayer(p);
        Vector pv = lp.getPosition();
        ApplicableRegionSet regionset = wg.getRegionManager(p.getWorld()).getApplicableRegions(pv);

        for (ProtectedRegion reg : regionset) {
            if (reg.getId().startsWith("arena_")) {
                if (reg.contains(pv))
                    return true;
            }
        }
        return false;
    }

    public Arena getArena(String name, World w) {
        return new Arena(wg.getRegionManager(w).getRegion("arena_" + name), w);
    }

    public ProtectedRegion getRegion(Player p) {

        LocalPlayer lp = wg.wrapPlayer(p);
        Vector pv = lp.getPosition();
        ApplicableRegionSet regionset = wg.getRegionManager(p.getWorld()).getApplicableRegions(pv);

        for (ProtectedRegion reg : regionset) {
            if (reg.contains(pv)) {
                return reg;
            }
        }
        return null;
    }

    public Arena getArena(Player p) {

        if (isInArena(p)) {
            return this.getArena(this.getRegion(p).getId().replace("arena_", ""), p.getWorld());
        }
        return null;
    }

    public ProtectedCuboidRegion createArena(Selection sel, Player p, String id) {

        ProtectedCuboidRegion region = new ProtectedCuboidRegion("arena_" + id, new BlockVector(sel.getNativeMinimumPoint()), new BlockVector(sel.getNativeMaximumPoint()));

        region.setFlag(DefaultFlag.PVP, StateFlag.State.ALLOW);

        region.setPriority(100);

        wg.getRegionManager(p.getWorld()).addRegion(region);

        // arenas.yml
        ConfigurationSection as = ConfigManager.af.createSection(id);

        cfman.saveArenas();

        return region;
    }

    public void reload() {

    }

    public List<Arena> getArenas(World world) {
        List<Arena> as = new ArrayList<>();

        List<String> ass = new ArrayList<>(ConfigManager.af.getKeys(false));

        for (String val : ass) {
            as.add(this.getArena(val, world));
        }

        return as;
    }

    public List<Location> getJoinBlocks(World w) {
        List<Location> jbs = new ArrayList<>();

        for (Arena a : this.getArenas(w)) {
            jbs.add(a.getJoinBlock());
        }

        return jbs;
    }

    public Arena getArena(Location loc, World w) {

        for (int i = 0; i < getArenas(w).size(); i++) {
            if (getJoinBlocks(w).get(i).equals(loc)) {
                Arena a = getArenas(w).get(i);
                return a;
            }
        }

        return null;
    }

    public void saveISection(ConfigurationSection sec, HashMap<ItemStack, Integer> inv) {

        for (ItemStack is : inv.keySet()) {
            sec.set("IS" + inv.get(is), is);
        }

        cfman.saveArenas();
    }

    public HashMap<ItemStack, Integer> convertHash(Inventory inv) {
        HashMap<ItemStack, Integer> ninv = new HashMap<>();

        for (int i = 0; i < inv.getSize(); i++) {
            ItemStack i1 = inv.getItem(i);
            ninv.put(i1, i);
        }

        return ninv;
    }

    public HashMap<ItemStack, Integer> getISectionH(ConfigurationSection sec) {

        HashMap<ItemStack, Integer> ninv = new HashMap<>();

        for (String key : sec.getKeys(false)) {

            int i = Integer.parseInt(key.replace("IS", ""));

            ItemStack is = sec.getItemStack(key);

            ninv.put(is, i);
        }

        return ninv;
    }

    public Inventory convertIHash(HashMap<ItemStack, Integer> ninv) {

        Inventory inv = Bukkit.createInventory(null, 45);

        for (ItemStack is : ninv.keySet()) {
            inv.setItem(ninv.get(is), is);
        }

        return inv;
    }
}
