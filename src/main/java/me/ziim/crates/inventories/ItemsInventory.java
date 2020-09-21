package me.ziim.crates.inventories;

import me.ziim.crates.ZiiMCrates;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class ItemsInventory {
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);

    public void itemsInventory(Player player, String title) {
        Inventory i = plugin.getServer().createInventory(null, 36, title + ChatColor.BLUE + " rewards");

        ItemStack glassPane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
        ItemMeta meta = glassPane.getItemMeta();
        meta.setDisplayName(" ");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        glassPane.setItemMeta(meta);

        ItemStack confirmPane = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        meta = confirmPane.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Save");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        confirmPane.setItemMeta(meta);

        ItemStack denyPane = new ItemStack(Material.RED_STAINED_GLASS_PANE, 1);
        meta = denyPane.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Cancel");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        denyPane.setItemMeta(meta);

        for (int j = 0; j <35 ; j++) {
            if ((j >= 9 && j <= 26) || j == 30 || j ==32) {
                continue;
            }
            i.setItem(j, glassPane);
        }

        i.setItem(30, confirmPane);
        i.setItem(32, denyPane);

        player.openInventory(i);

    }
}
