package controller;

import model.*;
import model.order.Order;
import utils.logger.LogEntryBuffer;

import java.util.HashMap;

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
    /**
     * Reinforcement Phase enum keyword
     */
    GamePhase d_ReinforcementGamePhase = GamePhase.Reinforcement;
    /**
     * Exit Phase enum keyword
     */
    GamePhase d_ExitGamePhase = GamePhase.ExitGame;
    /**
     * GamePhase
     */
    GamePhase d_GamePhase;
    /**
     * GameMap instance
     */
    GameMap d_GameMap;
    /**
     * Log entry Buffer Object
     */
    LogEntryBuffer d_Leb = new LogEntryBuffer();

    /**
     * This is the default constructor
     */
    public ExecuteOrder() {
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
        d_Leb.logInfo("\n EXECUTE ORDER PHASE \n");
        executeOrders();
        clearAllNeutralPlayers();
        return  checkIfPlayerWon(p_GamePhase);
    }

    /**
     * This method  executes each order in the order list
     */
    private void executeOrders() {
        int l_Counter = 0;
        while (l_Counter < d_GameMap.getPlayers().size()) {
            l_Counter = 0;
            for (Player player : d_GameMap.getPlayers().values()) {
                Order l_Order = player.nextOrder();
                if (l_Order == null) {
                    l_Counter++;
                } else {
                    if (l_Order.execute()) {
                        l_Order.printOrderCommand();
                    }
                }
            }
        }
    }

    /**
     * This method Clears the neutral players
     *
     */
    private void clearAllNeutralPlayers() {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            l_Player.removeNeutralPlayer();
        }
    }

    /**
     * Check if the player won the game after every turn
     *
     * @param p_GamePhase the next phase based on the status of player
     * @return the gamephase it has to change to based on the win
     */
    public GamePhase checkIfPlayerWon(GamePhase p_GamePhase){
        HashMap<String, Country> l_ListOfAllCountries = d_GameMap.getCountries();
        for(Player l_Player : d_GameMap.getPlayers().values()){
            if(l_Player.getCapturedCountries().equals(l_ListOfAllCountries.values())){
                System.out.println("The Player " + l_Player.getName() + " won the game.");
                System.out.println("Exiting the game...");
                return p_GamePhase.nextState(d_ExitGamePhase);
            }
        }
        return  p_GamePhase.nextState(d_ReinforcementGamePhase);
    }

}
