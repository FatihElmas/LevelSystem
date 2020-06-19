package com.monodevs.monolevels.events;

import com.monodevs.monobukkitapi.minecraft.helpers.ColorHelper;
import com.monodevs.monolevels.main.Main;
import com.monodevs.monolevels.utils.Level;
import com.monodevs.monolevels.utils.MobLoader;
import com.monodevs.monolevels.utils.XP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.function.UnaryOperator;

public class KillEvent implements Listener {

    FileConfiguration cfg = Main.INSTANCE.getConfig();

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();
        if (p != null) {
            EntityType type = e.getEntityType();
            if (MobLoader.containMobs(type)) {
                int limit = MobLoader.getXPLimit(type);
                int amount = MobLoader.getXPAmount(type);
                XP xp = new XP(p);
                xp.add(amount);
                if (xp.inLimit(type)) {
                    xp.reset();
                    Level lvl = new Level(p);
                    lvl.add();
                    List<String> msg = cfg.getStringList("messages.levelup");
                    msg.replaceAll(s -> {
                        String n = s;
                        if (n.contains("%new%")) {
                            n = n.replace("%new%", lvl.get() + "");
                        }
                        if (n.contains("%progress%")) {
                            n = n.replace("%progress%", lvl.getProgress(type));
                        }
                        return n;
                    });
                    msg = new ColorHelper().color(msg);
                    for (String str : msg) {
                        p.sendMessage(str);
                    }
                }
                String msg = cfg.getString("messages.earn_xp").replace("%earned%", amount + "").replace("%entity%", type.name());
                msg = new ColorHelper().color(msg);
                p.sendMessage(msg);
            }
        }
    }


}
