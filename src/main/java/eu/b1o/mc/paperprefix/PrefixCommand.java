package eu.b1o.mc.paperprefix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PrefixCommand implements CommandExecutor {

    private final PaperPrefix plugin;
    private static final Map<String, String> COLOR_MAP = new HashMap<>();
    
    static {
        COLOR_MAP.put("black", "&0");
        COLOR_MAP.put("darkblue", "&1");
        COLOR_MAP.put("darkgreen", "&2");
        COLOR_MAP.put("darkaqua", "&3");
        COLOR_MAP.put("darkred", "&4");
        COLOR_MAP.put("darkpurple", "&5");
        COLOR_MAP.put("gold", "&6");
        COLOR_MAP.put("gray", "&7");
        COLOR_MAP.put("darkgray", "&8");
        COLOR_MAP.put("blue", "&9");
        COLOR_MAP.put("green", "&a");
        COLOR_MAP.put("aqua", "&b");
        COLOR_MAP.put("red", "&c");
        COLOR_MAP.put("lightpurple", "&d");
        COLOR_MAP.put("purple", "&d");
        COLOR_MAP.put("yellow", "&e");
        COLOR_MAP.put("white", "&f");
    }

    public PrefixCommand(PaperPrefix plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            
            if (args.length > 0) {
                // Handle "set" subcommand
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length >= 2) {
                        String prefixText = args[1];
                        String colorCode = "";
                        
                        // Check if third argument is a color
                        if (args.length >= 3) {
                            String colorName = args[2].toLowerCase();
                            if (COLOR_MAP.containsKey(colorName)) {
                                colorCode = COLOR_MAP.get(colorName);
                            }
                        }
                        
                        String fullPrefix = "[" + colorCode + prefixText + "&r]";
                        String formattedPrefix = ChatColor.translateAlternateColorCodes('&', fullPrefix);
                        
                        // Save prefix
                        plugin.setPlayerPrefix(player.getUniqueId(), formattedPrefix);
                        
                        // Apply prefix (this will also update nametag)
                        plugin.applyPrefix(player);
                        
                        player.sendMessage(ChatColor.GREEN + "Your prefix has been set to " + formattedPrefix);
                    } else {
                        player.sendMessage(ChatColor.RED + "Usage: /prefix set <text> [color]");
                        player.sendMessage(ChatColor.GRAY + "Colors: red, blue, green, yellow, gold, aqua, purple, white, black, gray");
                    }
                }
                // Handle "remove" subcommand
                else if (args[0].equalsIgnoreCase("remove")) {
                    plugin.removePlayerPrefix(player.getUniqueId());
                    player.setDisplayName(player.getName());
                    player.setPlayerListName(player.getName());
                    player.sendMessage(ChatColor.GREEN + "Your prefix has been removed.");
                }
                // Handle "join" subcommand
                else if (args[0].equalsIgnoreCase("join")) {
                    if (args.length >= 2) {
                        String targetName = args[1];
                        Player targetPlayer = plugin.getServer().getPlayer(targetName);
                        
                        if (targetPlayer != null) {
                            String targetPrefix = plugin.getPlayerPrefix(targetPlayer.getUniqueId());
                            
                            if (targetPrefix != null) {
                                // Copy the target's prefix
                                plugin.setPlayerPrefix(player.getUniqueId(), targetPrefix);
                                plugin.applyPrefix(player);
                                player.sendMessage(ChatColor.GREEN + "You copied " + targetPlayer.getName() + "'s prefix: " + targetPrefix);
                            } else {
                                player.sendMessage(ChatColor.RED + targetPlayer.getName() + " doesn't have a prefix.");
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Player not found: " + targetName);
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Usage: /prefix join <username>");
                    }
                }
                else {
                    player.sendMessage(ChatColor.RED + "Usage: /prefix <set|remove|join>");
                    player.sendMessage(ChatColor.GRAY + "/prefix set <text> [color] - Set a prefix");
                    player.sendMessage(ChatColor.GRAY + "/prefix remove - Remove your prefix");
                    player.sendMessage(ChatColor.GRAY + "/prefix join <username> - Copy another player's prefix");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /prefix <set|remove|join>");
                player.sendMessage(ChatColor.GRAY + "/prefix set <text> [color] - Set a prefix");
                player.sendMessage(ChatColor.GRAY + "/prefix remove - Remove your prefix");
                player.sendMessage(ChatColor.GRAY + "/prefix join <username> - Copy another player's prefix");
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
        }
        return true;
    }
}
