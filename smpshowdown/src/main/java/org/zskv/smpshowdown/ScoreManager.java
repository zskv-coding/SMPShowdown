package org.zskv.smpshowdown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreManager {
    private final Map<String, Integer> teamScores = new HashMap<>();

    public ScoreManager(List<String> teamNames) {
        for (String team : teamNames) {
            teamScores.put(team.toLowerCase(), 0);
        }
    }

    public void addPoints(String team, int points) {
        team = team.toLowerCase();
        teamScores.put(team, teamScores.getOrDefault(team, 0) + points);
    }

    public void removePoints(String team, int points) {
        team = team.toLowerCase();
        teamScores.put(team, teamScores.getOrDefault(team, 0) - points);
    }

    public int getScore(String team) {
        return teamScores.getOrDefault(team.toLowerCase(), 0);
    }

    public Map<String, Integer> getAllScores() {
        return teamScores;
    }

    public List<Map.Entry<String, Integer>> getLeaderboard() {
        List<Map.Entry<String, Integer>> list = new ArrayList<>(teamScores.entrySet());
        list.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));
        return list;
    }

    public void resetScores() {
        for (String team : teamScores.keySet()) {
            teamScores.put(team, 0);
        }
    }

}
