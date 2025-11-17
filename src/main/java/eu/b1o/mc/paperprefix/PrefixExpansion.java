package eu.b1o.mc.paperprefix;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PrefixExpansion extends PlaceholderExpansion {

    private final PaperPrefix plugin;

    public PrefixExpansion(PaperPrefix plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "paperprefix";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Link_1503";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1-Beta";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("prefix")) {
            String prefix = plugin.getPlayerPrefix(player.getUniqueId());
            return prefix != null ? prefix : "";
        }

        return null;
    }
}
