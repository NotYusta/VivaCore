package me.yusta.vivacore;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;

public class Events implements Listener {
    @EventHandler
    public void decreaseBalance(PlayerDeathEvent event) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(event.getEntity().getUniqueId());
        Economy economy = Main.getEconomy();
        double playerBalance = economy.getBalance(player);
        if(playerBalance > 1000) {
            economy.withdrawPlayer(player, playerBalance * 0.1);
        }
    }
    @EventHandler
    public void removeVillagerTrades(InventoryOpenEvent event) {
        if(event.getView().getType() != InventoryType.MERCHANT) {
            return;
        }
        Merchant merchant = ((MerchantInventory) event.getInventory()).getMerchant();
        List<MerchantRecipe> recipes = new ArrayList<MerchantRecipe>(merchant.getRecipes());
        recipes.removeIf(recipe -> recipe.getResult().getType() == Material.ENCHANTED_BOOK && recipe.getResult().getEnchantments().containsKey(Enchantment.MENDING));
        merchant.setRecipes(recipes);
        if(recipes.size() < 1) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void addMobBalanceValue(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        EntityType entityType = event.getEntity().getType();
        if(killer != null) {
            Economy economy = Main.getEconomy();
            OfflinePlayer player = Bukkit.getOfflinePlayer(killer.getUniqueId());
            switch(entityType) {
                case SKELETON:
                    economy.depositPlayer(player, 7);
                    break;
                case CREEPER:
                    economy.depositPlayer(player, 8);
                    break;
                case PILLAGER:
                    economy.depositPlayer(player, 9);
                    break;
                case VEX:
                    economy.depositPlayer(player, 4);
                    break;
                case VINDICATOR:
                    economy.depositPlayer(player, 15);
                    break;
                case PHANTOM: case ZOMBIE:
                    economy.depositPlayer(player, 5);
                    break;
                case ENDERMITE:
                    economy.depositPlayer(player, 2);
                    break;
                case ENDERMAN:
                    economy.depositPlayer(player, 10);
                    break;
                case IRON_GOLEM:
                    economy.depositPlayer(player, 100);
                    break;
                case WITHER:
                    economy.depositPlayer(player, 5000);
                    break;
            }
        }
    }
}
