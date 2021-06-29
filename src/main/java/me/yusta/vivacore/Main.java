package me.yusta.vivacore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Economy econ = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Util.log("&5Enabling plugin..");
        if (!setupEconomy() ) {
            Util.log(Util.color("&cVault is not found, disabling plugin.."));
            getServer().getPluginManager().disablePlugin(this);
        }
        Util.log("&3Registering events..");
        setupEvents();
        Util.log("&aPlugin succesfully loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static Economy getEconomy() {
        return econ;
    }
    private void setupEvents() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }
}
