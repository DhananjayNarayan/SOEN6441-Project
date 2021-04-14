package model.tournament;

import model.strategy.player.PlayerStrategy;

import java.util.ArrayList;
import java.util.List;

public class TournamentOptions {

    private List<String> d_Map = new ArrayList<>();
    private List<PlayerStrategy> d_PlayerStrategies = new ArrayList<>();
    private int d_Games;
    private int d_MaxTries;

    public List<String> getMap() {
        return d_Map;
    }

    public List<PlayerStrategy> getPlayerStrategies() {
        return d_PlayerStrategies;
    }

    public void setPlayerStrategies(List<PlayerStrategy> p_playerStrategies) {
        d_PlayerStrategies = p_playerStrategies;
    }

    public int getGames() {
        return d_Games;
    }

    public void setGames(int p_games) {
        d_Games = p_games;
    }

    public int getMaxTries() {
        return d_MaxTries;
    }

    public void setMaxTries(int p_maxTries) {
        d_MaxTries = p_maxTries;
    }
}
