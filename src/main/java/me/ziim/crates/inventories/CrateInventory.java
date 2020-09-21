package me.ziim.crates.inventories;

import me.ziim.crates.Item;
import me.ziim.crates.RandomSelector;
import me.ziim.crates.ZiiMCrates;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class CrateInventory implements Listener {
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);

    public void crateInventory(Player player, String title) {
        Inventory i = plugin.getServer().createInventory(null, 27, title);

        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        glassPane.setItemMeta(meta);

        ItemStack yellowPane = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE, 1);
        meta = yellowPane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        yellowPane.setItemMeta(meta);

        ItemStack confirmPane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        meta = confirmPane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        confirmPane.setItemMeta(meta);

        ItemStack denyPane = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        meta = denyPane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        denyPane.setItemMeta(meta);

        for (int j = 0; j <= 8; j++) {
            i.setItem(j, glassPane);
        }
        for (int j = 19; j <= 20; j++) {
            i.setItem(j, glassPane);
        }
        for (int j = 24; j <= 25; j++) {
            i.setItem(j, glassPane);
        }

        i.setItem(22, glassPane);
        i.setItem(21, confirmPane);
        i.setItem(23, denyPane);
        i.setItem(26, yellowPane);
        player.openInventory(i);
    }
}
