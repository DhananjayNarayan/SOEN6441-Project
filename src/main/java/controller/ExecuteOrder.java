
package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import utils.InvalidExecutionException;
import utils.ValidationException;

public class ExecuteOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.Reinforcement;
    GamePhase d_GamePhase;
    GameMap d_GameMap;

    public ExecuteOrder() {
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException, InvalidExecutionException {
        d_GamePhase = p_GamePhase;
        //execute order loop goes here
        return d_NextGamePhase;
    }
}