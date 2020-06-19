package com.monodevs.monolevels.main;

import com.monodevs.monobukkitapi.minecraft.helpers.LoggerHelper;
import com.monodevs.monolevels.utils.Level;
import com.monodevs.monolevels.utils.MobLoader;
import com.monodevs.monolevels.utils.XP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {

    public static JavaPlugin INSTANCE;



    @Override
    public void onEnable() {
        INSTANCE = this;
        this.getConfig().options().copyDefaults(true);
        this.saveDefaultConfig();
        Level.load();
        XP.load();
        MobLoader.loadMobs();
    }

    @Override
    public void onDisable() {
        Level.save();
        XP.save();
    }


}
