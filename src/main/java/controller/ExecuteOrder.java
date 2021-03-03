package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.Player;

/**
 * This is a class which contains the Execute Order phase
 *
 * @author Prathika Suvarna
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class ExecuteOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.Reinforcement;
    GamePhase d_GamePhase = GamePhase.ExecuteOrder;
    GameMap d_GameMap;

    /**
     * This is the default constructor
     *
     */
    public ExecuteOrder(){
        d_GameMap = GameMap.getInstance();
    }
    /**
     * This method starts the current game phase
     *
     * @param p_GamePhase the current game phase
     * @return the next game phase
     * @throws Exception when execution fails
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        d_GamePhase = p_GamePhase;
        ExecuteOrders();
        return p_GamePhase.nextState(d_NextGamePhase);
    }

    /**
     * This method executes each order in the order list
     *
     * @return true if execution is successful
     */
    private void ExecuteOrders()
    {
        while(true){
            for (Player player : d_GameMap.getPlayers().values()) {
                if (player.nextOrder() != null) {
                    player.nextOrder().execute();
                }
            }
        }
    }
}
