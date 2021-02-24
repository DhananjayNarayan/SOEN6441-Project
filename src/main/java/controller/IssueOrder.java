package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import utils.InvalidExecutionException;
import utils.ValidationException;

public class IssueOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase;
    GameMap d_GameMap;

    public IssueOrder() {
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException, InvalidExecutionException {
        d_GamePhase = p_GamePhase;
        //issue order loop goes here
        return d_NextGamePhase;
    }
}