package me.ziim.crates.inventories;

import me.ziim.crates.Config;
import me.ziim.crates.ZiiMCrates;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class KeyInventory {
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);

    public void keyInventory(Player player, String title) {
        Inventory i = plugin.getServer().createInventory(null, 27, title + ChatColor.BLUE + " key");

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

        for (int j = 0; j <= 26; j++) {
            if (j == 13 || j == 11 || j == 15) {
                continue;
            }
            i.setItem(j, glassPane);
        }

        i.setItem(11, confirmPane);
        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String cfgtitle = Config.get().getString(keys + ".Title");
            if (title.trim().equals(cfgtitle)) {
                System.out.println(title);
                i.setItem(13, Config.get().getItemStack(keys + ".Key"));
            }
        }
        i.setItem(15, denyPane);

        player.openInventory(i);
    }
}
