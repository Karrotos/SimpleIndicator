package org.karrotos.me.simpleindicator;

import org.bukkit.plugin.java.JavaPlugin;
import org.karrotos.me.simpleindicator.Listeners.Indicator;

public final class SimpleIndicator extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Indicator(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
