package org.zskv.smpshowdown.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.zskv.smpshowdown.TeamManager;

public class TeamCommand implements CommandExecutor {

    private final TeamManager teamManager;

    public TeamCommand(TeamManager teamManager) {
        this.teamManager = teamManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage(ChatColor.YELLOW + "Usage: /team <team_name>");
            return true;
        }

        String teamName = args[0].toLowerCase();

        teamManager.removePlayerFromTeams(player);

        if (teamManager.addPlayerToTeam(player, teamName)) {
            sender.sendMessage(ChatColor.GREEN + "Added to team " + teamName);
        } else {
            sender.sendMessage(ChatColor.RED + "Team does not exist.");
        }

        return true;
    }
}
