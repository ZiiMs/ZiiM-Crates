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

public class EditorInventory {
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);

    public void editInventory(Player player, String title) {
        Inventory i = plugin.getServer().createInventory(null, 27, title + ChatColor.BLUE + " Editor");

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

        ItemStack keys = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        meta = keys.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Set crate key");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        keys.setItemMeta(meta);

        ItemStack items = new ItemStack(Material.GOLDEN_SWORD, 1);
        meta = items.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Set crate rewards");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        items.setItemMeta(meta);

        for (int j = 0; j <= 26; j++) {
            if (j == 9 || j == 12 || j == 14 || j == 17) {
                continue;
            }
            i.setItem(j, glassPane);
        }

        i.setItem(9, items);
        i.setItem(12, confirmPane);
        i.setItem(14, denyPane);
        i.setItem(17, keys);

        player.openInventory(i);
    }
}
