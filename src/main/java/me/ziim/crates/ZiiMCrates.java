package me.ziim.crates;

import me.ziim.crates.commands.CommandCrateTool;
import me.ziim.crates.events.ChestEvents;
import me.ziim.crates.events.CrateInventoryEvents;
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
        this.getServer().getPluginManager().registerEvents(new ChestEvents(), this);
        this.getServer().getPluginManager().registerEvents(new CrateInventoryEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
