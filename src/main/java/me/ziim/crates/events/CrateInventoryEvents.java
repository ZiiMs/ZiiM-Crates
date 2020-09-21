package me.ziim.crates.events;

import me.ziim.crates.Config;
import me.ziim.crates.Item;
import me.ziim.crates.RandomSelector;
import me.ziim.crates.inventories.EditorInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrateInventoryEvents implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        InventoryView view = e.getView();
        Inventory inv = e.getClickedInventory();
        if (clickedItem == null) {
            return;
        }
        for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
            String title = Config.get().getString(keys + ".Title");
            if (view.getTitle().equals(title)) {
                e.setCancelled(true);
                ItemStack key = Config.get().getItemStack(keys + ".Key");
                boolean hasKey = player.getInventory().containsAtLeast(key, 1);
                boolean hasSlot = Arrays.toString(player.getInventory().getStorageContents()).contains("null");
                if (clickedItem.getType().equals(Material.GREEN_STAINED_GLASS_PANE) && hasKey) {
                    if (!hasSlot) {
                        player.sendMessage("You have no inventory space!");
                        view.close();
                        return;
                    }
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
                    player.getInventory().removeItem(key);
                } else if (clickedItem.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
                    view.close();
                } else if (clickedItem.getType().equals(Material.YELLOW_STAINED_GLASS_PANE) && hasPerm(player, "zc.createcrate")) {
                    EditorInventory editInv = new EditorInventory();
                    editInv.editInventory(player, view.getTitle());
                }
                break;
            }
        }
    }


    public boolean hasPerm(Player player, String perm) {
        for (PermissionAttachmentInfo perms : player.getEffectivePermissions()) {
            if (perms.getPermission().equals(perm) || player.isOp()) {
                return true;
            }
        }
        return false;
    }
}
