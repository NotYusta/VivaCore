package me.yusta.vivacore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Util {
    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public static void log(String message) {
        Bukkit.getLogger().info(message);
    }
}
