package org.zskv.smpshowdown;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class TeamManager {
    private final SMPShowdown plugin;
    private final Map<String, List<String>> teams = new HashMap<>();
    private final File teamFile;
    private FileConfiguration config;
    private final Scoreboard scoreboard;

    public TeamManager(SMPShowdown plugin) {
        this.plugin = plugin;
        this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        this.teamFile = new File(plugin.getDataFolder(), "teams.yml");

        if (!teamFile.exists()) {
            plugin.saveResource("teams.yml", false);
        }

        loadTeams();
    }

    public void loadTeams() {
        config = YamlConfiguration.loadConfiguration(teamFile);
        teams.clear();

        for (Team team : scoreboard.getTeams()) {
            if (teams.containsKey(team.getName())) {
                team.unregister();
            }
        }

        for (String teamName : config.getKeys(false)) {
            List<String> members = config.getStringList(teamName);
            teams.put(teamName.toLowerCase(), members);

            Team scoreboardTeam = scoreboard.getTeam(teamName.toLowerCase());
            if (scoreboardTeam == null) {
                scoreboardTeam = scoreboard.registerNewTeam(teamName.toLowerCase());
            }

            scoreboardTeam.setDisplayName(formatTeamName(teamName));
            scoreboardTeam.setPrefix(getColorForTeam(teamName) + ""); // Add color to names
            scoreboardTeam.setColor(getColorForTeam(teamName)); // 1.13+ only

            for (String playerName : members) {
                scoreboardTeam.addEntry(playerName);
            }
        }
    }

    public void reload() {
        loadTeams();
    }

    public boolean addPlayerToTeam(Player player, String teamName) {
        Team team = scoreboard.getTeam(teamName.toLowerCase());
        if (team == null) return false;

        team.addEntry(player.getName());
        return true;
    }

    public void removePlayerFromTeams(Player player) {
        for (Team team : scoreboard.getTeams()) {
            if (team.hasEntry(player.getName())) {
                team.removeEntry(player.getName());
            }
        }
    }

    public List<String> getPlayersInTeam(String team) {
        return teams.getOrDefault(team.toLowerCase(), new ArrayList<>());
    }

    public Map<String, List<String>> getAllTeams() {
        return teams;
    }

    public List<String> getTeamNames() {
        return new ArrayList<>(teams.keySet());
    }

    public String getTeamOfPlayer(String playerName) {
        for (Map.Entry<String, List<String>> entry : teams.entrySet()) {
            if (entry.getValue().contains(playerName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private String formatTeamName(String name) {
        return name.replace("_", " ");
    }

    private org.bukkit.ChatColor getColorForTeam(String name) {
        name = name.toLowerCase();
        if (name.contains("red")) return org.bukkit.ChatColor.RED;
        if (name.contains("blue")) return org.bukkit.ChatColor.BLUE;
        if (name.contains("green")) return org.bukkit.ChatColor.GREEN;
        if (name.contains("yellow")) return org.bukkit.ChatColor.YELLOW;
        if (name.contains("purple")) return org.bukkit.ChatColor.DARK_PURPLE;
        if (name.contains("orange")) return org.bukkit.ChatColor.GOLD;
        if (name.contains("cyan")) return org.bukkit.ChatColor.AQUA;
        if (name.contains("pink")) return ChatColor.LIGHT_PURPLE;
        return org.bukkit.ChatColor.WHITE;
    }
}
