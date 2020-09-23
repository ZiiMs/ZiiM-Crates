package me.ziim.crates.events;

import me.ziim.crates.Config;
import me.ziim.crates.inventories.CrateInventory;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ChestEvents implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        ItemStack crateTool = e.getItemInHand();
        if (crateTool.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Create chest tool")) {
            Location location = e.getBlockPlaced().getLocation();
            ItemMeta meta = crateTool.getItemMeta();
            List<String> lore = meta.getLore();
            Chest chestState = (Chest) e.getBlockPlaced().getState();
            String title = chestState.getCustomName() + e.getPlayer().getDisplayName();
            for (String s : lore) {

                if (s.trim().contains("Title:")) {
                    System.out.println(s);
                    String[] temp = s.split("Title:");
                    title = temp[1].trim();
                    System.out.println("Found String!");
                    break;
                }
            }

            chestState.setCustomName(title);
            String locKey = String.valueOf(location.getBlockX()) + location.getBlockY() + location.getBlockZ();
            ItemStack key = new ItemStack(Material.POLISHED_BLACKSTONE_BRICK_SLAB);
            meta = key.getItemMeta();
            lore = new ArrayList<>();
            lore.add("Key to open chest!@");
            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            key.setItemMeta(meta);


            Config.get().set(locKey + ".Location", location);
            Config.get().set(locKey + ".Title", title);
            Config.get().set(locKey + ".Key", key);
            Config.save();
            e.getPlayer().getInventory().addItem(Config.get().getItemStack(locKey + ".Key"));
        }
    }

    @EventHandler
    public void onOpenChest(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && e.getClickedBlock().getType().equals(Material.CHEST)) {
            Player player = e.getPlayer();
            Chest chestBlock = (Chest) e.getClickedBlock().getState();
            Location location = e.getClickedBlock().getLocation();
            for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
                if (keys.equals(String.valueOf(location.getBlockX()) + location.getBlockY() + location.getBlockZ())) {
                    e.setCancelled(true);
                    System.out.println("found it!" + Config.get().getString(keys + ".Title"));
                    CrateInventory i = new CrateInventory(Config.get().getString(keys + ".Title"));
                    Inventory inv = i.getInventory();
                    i.openInv(e.getPlayer());

                    ItemStack key = Config.get().getItemStack(keys + ".Key");
                    inv.setItem(18, key);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Player player = e.getPlayer();
        if (block.getType().equals(Material.CHEST)) {
            Chest chest = (Chest) block.getState();
            Location location = block.getLocation();
            for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
                if (keys.equals(String.valueOf(location.getBlockX()) + location.getBlockY() + location.getBlockZ())) {
                    System.out.println("removing it!");
                    Config.get().set(String.valueOf(location.getBlockX()) + location.getBlockY() + location.getBlockZ(), null);
                    Config.save();
                    break;
                }
            }
        }
    }
}
