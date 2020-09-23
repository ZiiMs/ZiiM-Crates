package me.ziim.crates.inventories;

import me.ziim.crates.Config;
import me.ziim.crates.ZiiMCrates;
import me.ziim.crates.inventoryHelper.IHelper;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;

public class ItemsInventory implements IHelper {
    private final String title;
    private final Inventory inventory;
    Plugin plugin = ZiiMCrates.getPlugin(ZiiMCrates.class);

    public ItemsInventory(String name) {
        inventory = plugin.getServer().createInventory(this, 54, name);
        title = name;
        initItems();
    }

    public void initItems() {
        for (int j = 0; j < 54; j++) {
            if ((j >= 9 && j <= 44) || j == 48 || j == 50) {
                continue;
            }
            inventory.setItem(j, newItem(Material.GRAY_STAINED_GLASS_PANE, " "));
        }
        ArrayList<ItemStack> itemsList = new ArrayList<>();
        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String foundTitle = Config.get().getString(keys + ".Title");
            if (title.trim().equals(foundTitle + " items")) {
                itemsList = (ArrayList<ItemStack>) Config.get().getList(keys + ".Items");
            }
        }
        int i = 9;
        if (itemsList != null) {
            for (ItemStack item : itemsList) {
                inventory.setItem(i, item);
                i++;
            }
        }


        inventory.setItem(48, newItem(Material.GREEN_STAINED_GLASS_PANE, "Save"));
        inventory.setItem(50, newItem(Material.RED_STAINED_GLASS_PANE, "Cancel"));

    }

    protected ItemStack newItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public void openInv(final HumanEntity player) {
        player.openInventory(inventory);
    }

    @Override
    public void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, InventoryView view, Inventory inv, ItemStack cursor) {
        if (clickedItem == null) {
            if (!cursor.getType().equals(Material.AIR)) {
                inv.setItem(slot, cursor);
            }
            return;
        }
        if (clickedItem.getType().equals(Material.GRAY_STAINED_GLASS_PANE)) return;
        if (clickedItem.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
            ArrayList<ItemStack> itemList = new ArrayList<>();
            for (ItemStack item : inv.getContents()) {
                if (item != null && !item.getType().equals(Material.GREEN_STAINED_GLASS_PANE) && !item.getType().equals(Material.GRAY_STAINED_GLASS_PANE) && !item.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                    itemList.add(item);
                }
            }
            itemList.toArray(new ItemStack[0]);
            if (itemList.size() != 0) {
                for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
                    String foundTitle = Config.get().getString(keys + ".Title");
                    if (view.getTitle().equals(foundTitle + " items")) {
                        Config.get().set(keys + ".Items", itemList);
                        Config.save();
                        EditorInventory editInv = new EditorInventory(foundTitle + ChatColor.BLUE + " editor");
                        editInv.openInv(whoClicked);
                    }
                }
            }
        } else if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
            view.close();
        } else {
            if (!cursor.getType().equals(Material.AIR)) {
                inv.setItem(slot, cursor);
                whoClicked.getInventory().addItem(clickedItem);
            } else {
                whoClicked.getInventory().addItem(clickedItem);
                inv.setItem(slot, null);
            }
        }
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
