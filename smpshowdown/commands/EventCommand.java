package org.zskv.smpshowdown.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.zskv.smpshowdown.SMPShowdown;

public class EventCommand implements CommandExecutor {

    private final SMPShowdown plugin;

    public EventCommand(SMPShowdown plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPlugin();
            sender.sendMessage(ChatColor.GREEN + "[SMP Showdown] Reloaded team configuration.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "/event reload - Reloads team assignments");
        return true;
    }
}
