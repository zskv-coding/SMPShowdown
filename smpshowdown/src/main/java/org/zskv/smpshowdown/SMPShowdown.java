package org.zskv.smpshowdown;

import org.bukkit.plugin.java.JavaPlugin;
import org.zskv.smpshowdown.commands.EventCommand;
import org.zskv.smpshowdown.commands.TeamCommand;
import org.zskv.smpshowdown.commands.ScoreCommand;

public class SMPShowdown extends JavaPlugin {

    private static SMPShowdown instance;

    private TeamManager teamManager;
    private ScoreManager scoreManager;
    private ScoreboardManager scoreboardManager;

    @Override
    public void onEnable() {
        this.teamManager = new TeamManager(this);
        this.scoreManager = new ScoreManager(teamManager.getTeamNames());
        this.scoreboardManager = new ScoreboardManager(this, teamManager, scoreManager);

        getCommand("score").setExecutor(new ScoreCommand(scoreManager));
        getCommand("team").setExecutor(new TeamCommand(teamManager));
        getCommand("event").setExecutor(new EventCommand(this));
    }

    public void reloadPlugin() {
        this.teamManager.reload();
        this.scoreManager = new ScoreManager(teamManager.getTeamNames());
        this.scoreboardManager.updateScoreboard();
    }

    public TeamManager getTeamManager() {
        return teamManager;
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public static SMPShowdown getInstance() {
        return instance;
    }
}
