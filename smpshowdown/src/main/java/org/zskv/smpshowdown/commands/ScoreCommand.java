package org.zskv.smpshowdown.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.zskv.smpshowdown.ScoreManager;

import java.util.Map;

public class ScoreCommand implements CommandExecutor {

    private final ScoreManager scoreManager;

    public ScoreCommand(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /score <add|remove|get|reset|leaderboard> [...]");
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "add":
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /score add <team> <points>");
                    return true;
                }
                try {
                    String team = args[1].toLowerCase();
                    int points = Integer.parseInt(args[2]);
                    scoreManager.addPoints(team, points);
                    sender.sendMessage(ChatColor.GREEN + "Added " + points + " points to " + team + ".");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Points must be a number.");
                }
                return true;

            case "remove":
                if (args.length != 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /score remove <team> <points>");
                    return true;
                }
                try {
                    String team = args[1].toLowerCase();
                    int points = Integer.parseInt(args[2]);
                    scoreManager.removePoints(team, points);
                    sender.sendMessage(ChatColor.YELLOW + "Removed " + points + " points from " + team + ".");
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "Points must be a number.");
                }
                return true;

            case "get":
                if (args.length != 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /score get <team>");
                    return true;
                }
                String team = args[1].toLowerCase();
                int score = scoreManager.getScore(team);
                sender.sendMessage(ChatColor.AQUA + "Team " + team + " has " + score + " points.");
                return true;

            case "reset":
                scoreManager.resetScores();
                sender.sendMessage(ChatColor.RED + "All team scores have been reset.");
                return true;

            case "leaderboard":
                sender.sendMessage(ChatColor.GOLD + "== Leaderboard ==");
                int rank = 1;
                for (Map.Entry<String, Integer> entry : scoreManager.getLeaderboard()) {
                    String displayName = entry.getKey().replace("_", " ");
                    int teamScore = entry.getValue();
                    sender.sendMessage(ChatColor.YELLOW + "" + rank + ". " + ChatColor.WHITE + displayName + ": " + ChatColor.AQUA + teamScore);
                    rank++;
                }
                return true;

            default:
                sender.sendMessage(ChatColor.RED + "Unknown subcommand. Try: add, remove, get, reset, leaderboard");
                return true;
        }
    }
}
