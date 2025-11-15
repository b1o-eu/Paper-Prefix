package eu.b1o.mc.paperprefix;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrefixCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String prefix = args[0];
                player.setDisplayName(ChatColor.translateAlternateColorCodes('&', "[" + prefix + "] <" + player.getName() + ">"));
                player.setPlayerListName(ChatColor.translateAlternateColorCodes('&', "[" + prefix + "] <" + player.getName() + ">"));
                player.sendMessage(ChatColor.GREEN + "Your prefix has been set to " + prefix);
            } else {
                player.sendMessage(ChatColor.RED + "Please specify a prefix.");
            }
        } else {
            sender.sendMessage("This command can only be run by a player.");
        }
        return true;
    }
}
