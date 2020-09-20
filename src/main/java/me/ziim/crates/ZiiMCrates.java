package me.ziim.crates;

import org.bukkit.plugin.java.JavaPlugin;

public final class ZiiMCrates extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ZiiM Crates loaded");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
