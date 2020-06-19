package com.monodevs.monolevels.utils;

import com.monodevs.monobukkitapi.minecraft.modules.storage.PlayerStorage;
import com.monodevs.monolevels.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Level {

    private final PlayerStorage st;
    private final Player p;
    private static final JavaPlugin pl = Main.INSTANCE;
    private final FileConfiguration cfg;
    private static HashMap<Player, Integer> level = new HashMap<>();

    public Level(Player p) {
        this.p = p;
        this.st = new PlayerStorage(p, Main.INSTANCE);
        this.cfg = st.getPlayerYAML();
    }

    public void add() {
        level.put(p, level.get(p) + 1);
    }

    public void add(int amount) {
        level.put(p, level.get(p) + amount);
    }

    public void set(int amount) {
        level.put(p, amount);
    }

    public void remove() {
        level.put(p, level.get(p) - 1);
    }

    public void remove(int amount) {
        level.put(p, level.get(p) - amount);
    }

    public void reset() {
        set(0);
    }

    public String getProgress(EntityType type) {
        String pr = Utils.getProgressBar(new XP(p).get(), MobLoader.getXPLimit(type), 5, ':', ChatColor.GREEN, ChatColor.DARK_GRAY);
        return pr;
    }

    public int get() {
        return level.get(p);
    }



    public static void save() {
        for (Player pp : level.keySet()) {
            PlayerStorage st = new PlayerStorage(pp, pl);
            st.getPlayerYAML().set("level", level.get(pp));
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
            level.put(offline_p.getPlayer(), st.getPlayerYAML().getInt("level"));
        }
    }




}
