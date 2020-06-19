package com.monodevs.monolevels.utils;

import com.monodevs.monobukkitapi.minecraft.helpers.LoggerHelper;
import com.monodevs.monolevels.main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobLoader {

    private static List<EntityType> MOB_TYPES = new ArrayList<>();
    private static HashMap<EntityType, Integer> xp_amount = new HashMap<>();
    private static HashMap<EntityType, Integer> xp_limit = new HashMap<>();

    public static void loadMobs() {
        FileConfiguration cfg = Main.INSTANCE.getConfig();
        for (String key : cfg.getConfigurationSection("mobs").getKeys(false)) {
            EntityType type = EntityType.valueOf(key);
            if (type == null) {
                new LoggerHelper().log("&4&l! &6[MonoLevels] &7Can't load &a" + key + " &7mob. It is invalid mob name.");
                return;
            }
            MOB_TYPES.add(type);
            xp_amount.put(type, cfg.getInt("mobs." + key + ".xp_amount"));
            xp_limit.put(type, cfg.getInt("mobs." + key + ".xp_limit"));
            new LoggerHelper().log("&6[MonoLevels] &7loaded &a" + key + " &7mob.");
        }
    }

    public static int getXPAmount(EntityType type) {
        return xp_amount.get(type);
    }

    public static int getXPLimit(EntityType type) {
        return xp_limit.get(type);
    }

    public static boolean containMobs(EntityType type) {
        if (getMobs().contains(type)) {
            return true;
        }
        return false;
    }

    public static List<EntityType> getMobs() {
        return MOB_TYPES;
    }

}
