package model.tournament;

public class TournamentResult {
    private String d_Map;
    private int d_Game;
    private String d_Winner;

    public String getMap() {
        return d_Map;
    }

    public void setMap(String p_map) {
        d_Map = p_map;
    }

    public int getGame() {
        return d_Game;
    }

    public void setGame(int p_game) {
        d_Game = p_game;
    }

    public String getWinner() {
        return d_Winner;
    }

    public void setWinner(String p_winner) {
        d_Winner = p_winner;
    }
}
