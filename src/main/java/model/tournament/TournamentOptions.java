package model.tournament;
import model.strategy.player.PlayerStrategy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class to implement the tournament options
 *
 * @author Madhuvanthi
 */
public class TournamentOptions {

    /**
     * list for Map
     */
    private List<String> d_Map = new ArrayList<>();
    /**
     * hash set to hold player strategy
     */
    private Set<PlayerStrategy> d_PlayerStrategies = new HashSet<>();
    /**
     * number of games
     */
    private int d_Games;
    /**
     * maximum number of tries
     */
    private int d_MaxTries;

    /**
     * method to get map
     * @return the map
     */
    public List<String> getMap() {
        return d_Map;
    }

    /**
     * method to get player strategies
     *
     * @return player strategies
     */
    public Set<PlayerStrategy> getPlayerStrategies() {
        return d_PlayerStrategies;
    }

    /**
     * method to get games
     * @return number of games
     */
    public int getGames() {
        return d_Games;
    }

    /**
     * method to set game
     * @param p_Games number of games
     */
    public void setGames(int p_Games) {
        d_Games = p_Games;
    }

    /**
     * method to get maximum tried per game
     *
     * @return the maximum tries
     */
    public int getMaxTries() {
        return d_MaxTries;
    }

    /**
     * method to set maximum tries
     * @param p_MaxTries maximum tries
     */
    public void setMaxTries(int p_MaxTries) {
        d_MaxTries = p_MaxTries;
    }
}
