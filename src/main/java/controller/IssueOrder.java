package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.Player;

public class IssueOrder implements GameController {

    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase = GamePhase.IssueOrder;
    GameMap d_GameMap;
    Player d_CurrentPlayer;

    IssueOrder(){
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        d_GamePhase = p_GamePhase;

        return null;
    }
}
