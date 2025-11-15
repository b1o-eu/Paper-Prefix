package eu.b1o.mc.paperprefix;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PrefixChatListener implements Listener {
    
    private final PaperPrefix plugin;
    
    public PrefixChatListener(PaperPrefix plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String displayName = player.getDisplayName();
        
        // Set format to [Prefix] <username>: message
        event.setFormat(displayName + ": %2$s");
    }
}
