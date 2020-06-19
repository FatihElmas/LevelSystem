package com.monodevs.monolevels.utils;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

import java.text.DecimalFormat;

public class Utils {
    public static String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public static int getProgressPercentage() {
        int percent = 20 * 100 / 100;
        double b = Math.round(percent * 10.0) / 10.0;
        DecimalFormat format = new DecimalFormat("##.#");
        return Integer.valueOf(format.format(b));
    }

}
