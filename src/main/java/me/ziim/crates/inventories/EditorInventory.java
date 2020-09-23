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

public class EditorInventory implements IHelper {
    private final Inventory inv;
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);

    public EditorInventory(String title) {
        inv = plugin.getServer().createInventory(this, 27, title);

        initItems();
    }

    public void initItems() {
        for (int j = 0; j <= 26; j++) {
            if (j == 9 || j == 17) {
                continue;
            }
            inv.setItem(j, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
        inv.setItem(9, newItem(Material.GOLDEN_SWORD, "See crate rewards"));
        inv.setItem(17, newItem(Material.TRIPWIRE_HOOK, "Set crate key"));
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
        player.openInventory(inv);
    }

    public Inventory getInv() {
        return inv;
    }

    @Override
    public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, InventoryView view, Inventory inventory, ItemStack cursor) {
        if (clickedItem == null) {
            return;
        }
        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String title = Config.get().getString(keys + ".Title");
            if (view.getTitle().equals(title + ChatColor.BLUE + " editor")) {
                if (clickedItem.getType().equals(Material.GOLDEN_SWORD)) {
                    ItemsInventory itemsInv = new ItemsInventory(title + " items");
                    itemsInv.openInv(whoClicked);
                    break;
                } else if (clickedItem.getType().equals(Material.TRIPWIRE_HOOK)) {
                    KeyInventory keysInv = new KeyInventory(title + " key");
                    keysInv.openInv(whoClicked);
                    break;
                }
            }
        }
    }


    @Override
    public Inventory getInventory() {
        return inv;
    }
}

