package eu.b1o.mc.paperprefix;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PaperPrefix extends JavaPlugin implements Listener {

    private File prefixFile;
    private FileConfiguration prefixConfig;
    private Map<UUID, String> prefixes = new HashMap<>();
    private NametagListener nametagListener;

    @Override
    public void onEnable() {
        // Setup prefix storage
        prefixFile = new File(getDataFolder(), "prefixes.yml");
        if (!prefixFile.exists()) {
            prefixFile.getParentFile().mkdirs();
            try {
                prefixFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        prefixConfig = YamlConfiguration.loadConfiguration(prefixFile);
        loadPrefixes();

        // Initialize nametag listener
        nametagListener = new NametagListener(this);

        // Register commands
        PrefixCommand prefixCommand = new PrefixCommand(this);
        PrefixTabCompleter tabCompleter = new PrefixTabCompleter();
        this.getCommand("prefix").setExecutor(prefixCommand);
        this.getCommand("prefix").setTabCompleter(tabCompleter);
        this.getCommand("status").setExecutor(prefixCommand);
        this.getCommand("status").setTabCompleter(tabCompleter);
        
        // Register events
        getServer().getPluginManager().registerEvents(new PrefixChatListener(this), this);
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(nametagListener, this);

        // Register PlaceholderAPI expansion if available
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PrefixExpansion(this).register();
        }

        // Apply prefixes to online players
        for (Player player : Bukkit.getOnlinePlayers()) {
            applyPrefix(player);
        }
    }

    @Override
    public void onDisable() {
        savePrefixes();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        applyPrefix(event.getPlayer());
    }

    public void setPlayerPrefix(UUID uuid, String prefix) {
        prefixes.put(uuid, prefix);
        prefixConfig.set(uuid.toString(), prefix);
        savePrefixes();
    }

    public void removePlayerPrefix(UUID uuid) {
        prefixes.remove(uuid);
        prefixConfig.set(uuid.toString(), null);
        savePrefixes();
    }

    public String getPlayerPrefix(UUID uuid) {
        return prefixes.get(uuid);
    }

    public void applyPrefix(Player player) {
        String prefix = getPlayerPrefix(player.getUniqueId());
        if (prefix != null) {
            player.setDisplayName(prefix + " <" + player.getName() + ">");
            player.setPlayerListName(prefix + " " + player.getName());
            nametagListener.updateNametag(player);
        }
    }

    private void loadPrefixes() {
        for (String key : prefixConfig.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                String prefix = prefixConfig.getString(key);
                prefixes.put(uuid, prefix);
            } catch (IllegalArgumentException e) {
                // Invalid UUID, skip
            }
        }
    }

    private void savePrefixes() {
        try {
            prefixConfig.save(prefixFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
