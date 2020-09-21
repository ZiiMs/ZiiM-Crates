package me.ziim.crates.events;

import me.ziim.crates.Config;
import me.ziim.crates.inventories.ItemsInventory;
import me.ziim.crates.inventories.KeyInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class EditorInventoryEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        InventoryView view = e.getView();
        Inventory inv = e.getClickedInventory();
        if (clickedItem == null) {
            return;
        }
        System.out.println(inv.getLocation().getX());
        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String title = Config.get().getString(keys + ".Title");
            if (view.getTitle().equals(title + ChatColor.BLUE + " Editor")) {
                e.setCancelled(true);
                if(clickedItem.getType().equals(Material.GOLDEN_SWORD)) {
                    ItemsInventory itemsInv = new ItemsInventory();
                    itemsInv.itemsInventory(player, title);
                    break;
                } else if(clickedItem.getType().equals(Material.TRIPWIRE_HOOK)) {
                    KeyInventory keysInv = new KeyInventory();
                    keysInv.keyInventory(player, title);
                    break;
                }
            }
        }
    }
}
