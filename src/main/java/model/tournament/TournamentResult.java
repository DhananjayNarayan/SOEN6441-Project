package model.tournament;

/**
 * Class to implement the tournament result
 *
 * @author Madhuvanthi
 */
public class TournamentResult {
    /**
     * string for map
     */
    private String d_Map;
    /**
     * game number
     */
    private int d_Game;
    /**
     * string for winner of the game
     */
    private String d_Winner;

    /**
     * method to get map
     * @return the map
     */
    public String getMap() {
        return d_Map;
    }

    /**
     * method to set map
     *
     * @param p_Map the map
     */
    public void setMap(String p_Map) {
        d_Map = p_Map;
    }
    /**
     * method to get game
     * @return the game
     */
    public int getGame() {
        return d_Game;
    }
    /**
     * method to set game
     *
     * @param p_Game the game
     */
    public void setGame(int p_Game) {
        d_Game = p_Game;
    }
    /**
     * method to get game winner
     * @return the winner
     */
    public String getWinner() {
        return d_Winner;
    }
    /**
     * method to set winner
     *
     * @param p_Winner the winner
     */
    public void setWinner(String p_Winner) {
        d_Winner = p_Winner;
    }
}
