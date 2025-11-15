package eu.b1o.mc.paperprefix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NametagListener implements Listener {

    private final PaperPrefix plugin;

    public NametagListener(PaperPrefix plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        updateNametag(event.getPlayer());
    }

    public void updateNametag(Player player) {
        String prefix = plugin.getPlayerPrefix(player.getUniqueId());
        if (prefix == null) {
            return;
        }

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String teamName = "prefix_" + player.getName();
        
        Team team = scoreboard.getTeam(teamName);
        if (team == null) {
            team = scoreboard.registerNewTeam(teamName);
        }
        
        team.setPrefix(prefix + " ");
        team.addEntry(player.getName());
    }
}
