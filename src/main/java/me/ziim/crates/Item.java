package me.ziim.crates;

import org.bukkit.inventory.ItemStack;

public class Item {
    public ItemStack item;
    public int prob;

    public Item(int prob, ItemStack item) {
        this.prob = prob;
        this.item = item;
    }
}
