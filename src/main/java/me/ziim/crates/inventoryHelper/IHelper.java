package me.ziim.crates.inventoryHelper;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public interface IHelper extends InventoryHolder {
    void onGUIClick(Player whoClicked, int slot, ItemStack clickedItem, InventoryView view, Inventory inventory, ItemStack cursor);
}
