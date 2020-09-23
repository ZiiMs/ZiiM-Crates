package me.ziim.crates.inventories;

import me.ziim.crates.Config;
import me.ziim.crates.ZiiMCrates;
import me.ziim.crates.inventoryHelper.IHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;

public class KeyInventory implements IHelper {
    private final Inventory inventory;
    private final String title;
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);

    public KeyInventory(String name) {
        inventory = plugin.getServer().createInventory(this, 27, name);
        title = name.split(" key")[0];
        initItems();
    }

    public void initItems() {
        for (int j = 0; j <= 26; j++) {
            if (j == 13 || j == 11 || j == 15) {
                continue;
            }
            inventory.setItem(j, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }

        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String cfgtitle = Config.get().getString(keys + ".Title");
            if (title.trim().equals(cfgtitle)) {
                System.out.println(title);
                inventory.setItem(13, Config.get().getItemStack(keys + ".Key"));
            }
        }

        inventory.setItem(11, newItem(Material.GREEN_STAINED_GLASS_PANE, "Save"));
        inventory.setItem(15, newItem(Material.RED_STAINED_GLASS_PANE, "Cancel"));

    }

    protected ItemStack newItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInv(Player player) {
        player.openInventory(inventory);
    }

    @Override
    public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, InventoryView view, Inventory inv, ItemStack cursor) {
        if (clickedItem == null) {
            return;
        }
        if (clickedItem.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) return;
        if (clickedItem.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
            System.out.println("Save");
            for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
                String foundTitle = Config.get().getString(keys + ".Title");
                if (view.getTitle().equals(foundTitle + " key")) {
                    Config.get().set(keys + ".Key", inv.getItem(13));
                    Config.save();
                    EditorInventory editInv = new EditorInventory(foundTitle + ChatColor.BLUE + " editor");
                    editInv.openInv(whoClicked);
                }
            }
        } else if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            view.close();
        } else {
            if (!cursor.getType().equals(Material.AIR)) {
                inv.setItem(slot, cursor);
                whoClicked.getInventory().addItem(clickedItem);
            }
        }
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
