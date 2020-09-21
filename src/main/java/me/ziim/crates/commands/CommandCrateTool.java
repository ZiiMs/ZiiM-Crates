package me.ziim.crates.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandCrateTool implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println(Arrays.toString(args));
        if (sender instanceof Player) {
            if (args.length == 0)  {
                return false;
            }
            String title = String.join(" ", args);
            title = ChatColor.translateAlternateColorCodes('&', title);
            Player player = (Player) sender;
            sender.sendMessage("Creating crate!!");
            ItemStack crateTool = new ItemStack(Material.CHEST);

            ItemMeta meta = crateTool.getItemMeta();

            ArrayList<String> lore = new ArrayList<>();
            meta.setDisplayName(ChatColor.YELLOW + "Create chest tool");
            lore.add("Title: " + title);
            lore.add(ChatColor.WHITE + "Place the item to place a RNG chest");

            meta.setLore(lore);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            crateTool.setItemMeta(meta);

            player.getInventory().addItem(crateTool);
        }

        return true;
    }
}
