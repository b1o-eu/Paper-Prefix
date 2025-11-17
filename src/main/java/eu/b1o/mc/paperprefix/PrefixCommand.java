package eu.b1o.mc.paperprefix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PrefixCommand implements CommandExecutor {
    // Bedrock color code to hex mapping for Paper
    private static final Map<Character, String> BEDROCK_HEX_MAP = new HashMap<>();
    static {
        BEDROCK_HEX_MAP.put('g', "#DDD605"); // minecoin_gold
        BEDROCK_HEX_MAP.put('h', "#E3D4D1"); // material_quartz
        BEDROCK_HEX_MAP.put('i', "#CECACA"); // material_iron
        BEDROCK_HEX_MAP.put('j', "#443A3B"); // material_netherite
        BEDROCK_HEX_MAP.put('m', "#971607"); // material_redstone
        BEDROCK_HEX_MAP.put('n', "#B4684D"); // material_copper
        BEDROCK_HEX_MAP.put('p', "#DEB12D"); // material_gold
        BEDROCK_HEX_MAP.put('q', "#47A036"); // material_emerald
        BEDROCK_HEX_MAP.put('s', "#2CBAA8"); // material_diamond
        BEDROCK_HEX_MAP.put('t', "#21497B"); // material_lapis
        BEDROCK_HEX_MAP.put('u', "#9A5CC6"); // material_amethyst
    }


    private final PaperPrefix plugin;
    private static final Map<String, String> COLOR_MAP = new HashMap<>();
    
    static {
        // Standard Colors
        COLOR_MAP.put("black", "&0");
        COLOR_MAP.put("dark_blue", "&1");
        COLOR_MAP.put("dark_green", "&2");
        COLOR_MAP.put("dark_aqua", "&3");
        COLOR_MAP.put("dark_red", "&4");
        COLOR_MAP.put("dark_purple", "&5");
        COLOR_MAP.put("gold", "&6");
        COLOR_MAP.put("gray", "&7");
        COLOR_MAP.put("dark_gray", "&8");
        COLOR_MAP.put("blue", "&9");
        COLOR_MAP.put("green", "&a");
        COLOR_MAP.put("aqua", "&b");
        COLOR_MAP.put("red", "&c");
        COLOR_MAP.put("lightpurple", "&d");
        COLOR_MAP.put("purple", "&d");
        COLOR_MAP.put("yellow", "&e");
        COLOR_MAP.put("white", "&f");

        // Bedrock Colors (using § codes for later replacement)
        COLOR_MAP.put("minecoin_gold", "§g");
        COLOR_MAP.put("quartz", "§h");
        COLOR_MAP.put("iron", "§i");
        COLOR_MAP.put("netherite", "§j");
        COLOR_MAP.put("redstone", "§m");
        COLOR_MAP.put("copper", "§n");
        COLOR_MAP.put("emerald", "§q");
        COLOR_MAP.put("diamond", "§s");
        COLOR_MAP.put("lapis", "§t");
        COLOR_MAP.put("amethyst", "§u");
    }

    public PrefixCommand(PaperPrefix plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length > 0) {
                String subCommand = args[0].toLowerCase();
                switch (subCommand) {
                    case "set":
                        if (args.length >= 2) {
                            String prefixText = args[1];
                            String colorCode = "";
                            if (args.length >= 3) {
                                String colorName = args[2].toLowerCase();
                                if (COLOR_MAP.containsKey(colorName)) {
                                    colorCode = COLOR_MAP.get(colorName);
                                }
                            }
                            String fullPrefix = "[" + colorCode + prefixText + "&r]";
                            String formattedPrefix = ChatColor.translateAlternateColorCodes('&', fullPrefix);
                            formattedPrefix = ChatColor.translateAlternateColorCodes('§', formattedPrefix);
                            formattedPrefix = replaceBedrockCodesWithHex(formattedPrefix);
                            plugin.setPlayerPrefix(player.getUniqueId(), formattedPrefix);
                            plugin.applyPrefix(player);
                            player.sendMessage(ChatColor.GREEN + "Your prefix has been set to " + ChatColor.WHITE + formattedPrefix);
                        } else {
                            player.sendMessage(ChatColor.RED + "Usage: /prefix set <text> [color]");
                            player.sendMessage(ChatColor.GRAY + "Colors: red, blue, green, yellow, gold, aqua, purple, white, black, gray");
                            player.sendMessage(ChatColor.GRAY + "Formatting: use & or § codes, e.g. §l for bold");
                        }
                        break;
                    case "remove":
                        plugin.removePlayerPrefix(player.getUniqueId());
                        player.setDisplayName(player.getName());
                        player.setPlayerListName(player.getName());
                        player.sendMessage(ChatColor.GREEN + "Your prefix has been removed.");
                        break;
                    case "join":
                        if (!player.hasPermission("paperprefix.set")) {
                            player.sendMessage(ChatColor.RED + "You don't have permission to set a prefix.");
                        } else if (args.length >= 2) {
                            String targetName = args[1];
                            Player targetPlayer = plugin.getServer().getPlayer(targetName);
                            if (targetPlayer != null) {
                                String targetPrefix = plugin.getPlayerPrefix(targetPlayer.getUniqueId());
                                if (targetPrefix != null) {
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
                        break;
                    default:
                        sendUsage(sender);
                        break;
                }
            } else {
                sendUsage(sender);
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
        }
        return true;
    }

    // Replace Bedrock color codes (e.g., §g) with Paper hex color codes (e.g., §x§D§D§D§6§0§5)
    private String replaceBedrockCodesWithHex(String input) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if ((c == '§' || c == '&') && i + 1 < input.length()) {
                char code = Character.toLowerCase(input.charAt(i + 1));
                if (BEDROCK_HEX_MAP.containsKey(code)) {
                    String hex = BEDROCK_HEX_MAP.get(code);
                    sb.append(toPaperHexColor(hex));
                    i++; // skip code
                    continue;
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }

    // Convert #RRGGBB to Paper hex color code (e.g., §x§R§R§G§G§B§B)
    private String toPaperHexColor(String hex) {
        if (hex.startsWith("#")) hex = hex.substring(1);
        if (hex.length() != 6) return "";
        StringBuilder sb = new StringBuilder("§x");
        for (char ch : hex.toCharArray()) {
            sb.append('§').append(ch);
        }
        return sb.toString();
    }
    private void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Prefix Command Usage:");
        sender.sendMessage(ChatColor.YELLOW + "/prefix set <text> [color] " + ChatColor.GRAY + "- Set your prefix with optional color.");
        sender.sendMessage(ChatColor.YELLOW + "/prefix remove " + ChatColor.GRAY + "- Remove your current prefix.");
        sender.sendMessage(ChatColor.YELLOW + "/prefix join <username> " + ChatColor.GRAY + "- Copy another player's prefix.");
    }
}
