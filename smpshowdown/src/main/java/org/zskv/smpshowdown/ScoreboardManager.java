package org.zskv.smpshowdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardManager {
    private final SMPShowdown plugin;
    private final TeamManager teamManager;
    private final ScoreManager scoreManager;

    public ScoreboardManager(SMPShowdown plugin, TeamManager teamManager, ScoreManager scoreManager) {
        this.plugin = plugin;
        this.teamManager = teamManager;
        this.scoreManager = scoreManager;

        updateScoreboard();
    }

    public void updateScoreboard() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            applyScoreboard(player);
        }
    }

    public void applyScoreboard(Player player) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("smpScore", "dummy", ChatColor.GOLD + "" + ChatColor.BOLD + "SMP Showdown");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int score = teamManager.getTeamNames().size() + 1;
        for (String team : teamManager.getTeamNames()) {
            int points = scoreManager.getScore(team);
            ChatColor color = getColorForTeam(team);
            String entry = color + team.replace("_", " ") + ": " + ChatColor.WHITE + points;
            objective.getScore(entry).setScore(--score);
        }

        player.setScoreboard(scoreboard);
    }

    private ChatColor getColorForTeam(String name) {
        name = name.toLowerCase();
        if (name.contains("red")) return ChatColor.RED;
        if (name.contains("blue")) return ChatColor.BLUE;
        if (name.contains("green")) return ChatColor.GREEN;
        if (name.contains("yellow")) return ChatColor.YELLOW;
        if (name.contains("purple")) return ChatColor.DARK_PURPLE;
        if (name.contains("orange")) return ChatColor.GOLD;
        if (name.contains("pink")) return ChatColor.LIGHT_PURPLE;
        if (name.contains("cyan")) return ChatColor.AQUA;
        return ChatColor.WHITE;
    }
}
