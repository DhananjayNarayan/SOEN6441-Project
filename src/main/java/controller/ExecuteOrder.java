package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.Player;
import model.order.Order;

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
     */
    private void ExecuteOrders()
    {
        int l_Counter = 0;
        while(l_Counter <= d_GameMap.getPlayers().size()){
            l_Counter=0;
            for (Player player : d_GameMap.getPlayers().values()) {
                Order l_Order = player.nextOrder();
                if (l_Order == null) {
                    l_Counter++;
                }
                else{
                    l_Order.execute();
                }
            }
        }
    }
}
