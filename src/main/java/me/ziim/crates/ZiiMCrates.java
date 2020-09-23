package me.ziim.crates;

import me.ziim.crates.commands.CommandCrateTool;
import me.ziim.crates.commands.CommandGetKey;
import me.ziim.crates.events.ChestEvents;
import me.ziim.crates.inventoryHelper.IEH;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZiiMCrates extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ZiiM Crates loaded");

//        getConfig().options().copyDefaults();
//        saveDefaultConfig();

        Config.setup();
        Config.save();

        this.getCommand("cratetool").setExecutor(new CommandCrateTool());
        this.getCommand("getkey").setExecutor(new CommandGetKey());
        this.getServer().getPluginManager().registerEvents(new ChestEvents(), this);
        this.getServer().getPluginManager().registerEvents(new IEH(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
