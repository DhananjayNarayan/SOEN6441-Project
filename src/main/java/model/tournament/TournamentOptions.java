package model.tournament;

import model.strategy.player.PlayerStrategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TournamentOptions {

    private List<String> d_Map = new ArrayList<>();
    private Set<PlayerStrategy> d_PlayerStrategies = new HashSet<>();
    private int d_Games;
    private int d_MaxTries;

    public List<String> getMap() {
        return d_Map;
    }

    public Set<PlayerStrategy> getPlayerStrategies() {
        return d_PlayerStrategies;
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
