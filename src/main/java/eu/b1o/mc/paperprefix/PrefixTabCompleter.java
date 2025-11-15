package eu.b1o.mc.paperprefix;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrefixTabCompleter implements TabCompleter {

    private static final List<String> COLORS = Arrays.asList(
        "red", "blue", "green", "yellow", "gold", "aqua", "purple", 
        "white", "black", "gray", "darkred", "darkblue", "darkgreen", 
        "darkaqua", "darkpurple", "darkgray"
    );

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        
        if (args.length == 1) {
            suggestions.add("set");
            suggestions.add("remove");
            suggestions.add("join");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            suggestions.add("<prefix>");
        } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            // Filter colors based on what user is typing
            String input = args[2].toLowerCase();
            for (String color : COLORS) {
                if (color.startsWith(input)) {
                    suggestions.add(color);
                }
            }
        } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            // Suggest online player names
            String input = args[1].toLowerCase();
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().toLowerCase().startsWith(input)) {
                    suggestions.add(player.getName());
                }
            }
        }
        
        return suggestions;
    }
}
