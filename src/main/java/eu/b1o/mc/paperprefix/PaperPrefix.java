package eu.b1o.mc.paperprefix;

import org.bukkit.plugin.java.JavaPlugin;

public final class PaperPrefix extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("prefix").setExecutor(new PrefixCommand());
        this.getCommand("status").setExecutor(new PrefixCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
