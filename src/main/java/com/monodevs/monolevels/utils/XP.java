package com.monodevs.monolevels.utils;

import com.monodevs.monobukkitapi.minecraft.modules.storage.PlayerStorage;
import com.monodevs.monolevels.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class XP {

    private final PlayerStorage st;
    private final Player p;
    private static final JavaPlugin pl = Main.INSTANCE;
    private final FileConfiguration cfg;
    private static HashMap<Player, Integer> xp = new HashMap<>();

    public XP(Player p) {
        this.p = p;
        this.st = new PlayerStorage(p, Main.INSTANCE);
        this.cfg = st.getPlayerYAML();
    }

    public void add() {
        xp.put(p, xp.get(p) + 1);
    }

    public void add(int amount) {
        xp.put(p, xp.get(p) + amount);
    }

    public void set(int amount) {
        xp.put(p, amount);
    }

    public void remove() {
        xp.put(p, xp.get(p) - 1);
    }

    public void remove(int amount) {
        xp.put(p, xp.get(p) - amount);
    }

    public void reset() {
        set(0);
    }

    public int get() {
        return xp.get(p);
    }

    public int getLimit(EntityType type) {
        return MobLoader.getXPLimit(type);
    }

    public boolean inLimit(EntityType type) {
        int limit = getLimit(type);
        int xp = get();
        if (xp >= limit) {
            return true;
        }
        return false;
    }

    public static void save() {
        for (Player pp : xp.keySet()) {
            PlayerStorage st = new PlayerStorage(pp, pl);
            st.getPlayerYAML().set("xp", xp.get(pp));
            st.saveYAML();
        }
    }

    public static void load() {
        File folder = new File(pl.getDataFolder() + File.separator + "players");
        for (File f : folder.listFiles()) {
            String uuid = f.getName();
            if (uuid.contains(".yml")) {
                uuid = uuid.replace(".yml", "");
            }
            OfflinePlayer offline_p = Bukkit.getPlayer(UUID.fromString(uuid));
            PlayerStorage st = new PlayerStorage(offline_p.getPlayer(), pl);
            xp.put(offline_p.getPlayer(), st.getPlayerYAML().getInt("xp"));
        }
    }


}
