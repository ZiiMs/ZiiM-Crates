package me.ziim.crates.inventoryHelper;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class IEH implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getHolder() instanceof IHelper) {
            e.setCancelled(true);
            IHelper gui = (IHelper) e.getClickedInventory().getHolder();
            gui.onGUIClick((Player) e.getWhoClicked(), e.getRawSlot(), e.getCurrentItem(), e.getView(), e.getClickedInventory(), e.getCursor());
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof IHelper) {
            e.setCancelled(true);
        }
    }
}
