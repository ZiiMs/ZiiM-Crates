package me.ziim.crates.commands;

import me.ziim.crates.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CommandGetKey implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println(Arrays.toString(args));
        if (sender instanceof Player) {
            if (args.length == 0) {
                return false;
            }
            String crateId = args[0];
            Player player = (Player) sender;
            for (String keys : Config.get().getConfigurationSection("").getKeys(false)) {
                if (keys.equals(crateId)) {
                    System.out.println("Founding giving key");
                    ItemStack key = Config.get().getItemStack(keys + ".Key");
                    player.getInventory().addItem(key);
                    player.sendMessage("You've been given a key to crate " + Config.get().getString(keys + ".Title"));
                    break;
                }
            }
        }
        return true;
    }
}
