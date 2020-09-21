package me.ziim.crates.events;

import me.ziim.crates.Config;
import me.ziim.crates.Item;
import me.ziim.crates.RandomSelector;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CrateInventoryEvents implements Listener {

    public String key;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        InventoryAction action = e.getAction();
        ItemStack clickedItem = e.getCurrentItem();
        InventoryView view = e.getView();
        Inventory inv = e.getClickedInventory();
        if(clickedItem == null || !clickedItem.hasItemMeta()) {
            return;
        }
        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String title = Config.get().getString(keys + ".Title");
            if (view.getTitle().equals(title)) {
                inv.setItem(18, Config.get().getItemStack(keys + ".Key"));
                if (clickedItem.getType().equals(Material.GREEN_STAINED_GLASS_PANE)) {
                    ItemStack shovel = new ItemStack(Material.DIAMOND_SHOVEL);
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                    ItemStack wood = new ItemStack(Material.OAK_LOG);
                    ItemStack string = new ItemStack(Material.STRING);
                    ItemStack pork = new ItemStack(Material.COOKED_PORKCHOP);
                    List<Item> items = new ArrayList<>();
                    items.add(new Item(15, shovel));
                    items.add(new Item(5, sword));
                    items.add(new Item(30, wood));
                    items.add(new Item(25, string));
                    items.add(new Item(25, pork));
                    RandomSelector rngItem = new RandomSelector(items);

                    for (int i = 9; i <= 17; i++) {
                        inv.setItem(i, rngItem.getRandom().item);
                    }
                    player.getInventory().addItem(inv.getItem(13));
                } else if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                    view.close();
                }

                e.setCancelled(true);
                break;
            }
        }
    }
}