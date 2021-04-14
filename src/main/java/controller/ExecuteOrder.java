package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.Player;
import model.order.Order;
import utils.logger.LogEntryBuffer;

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
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

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
        executeOrders();
        clearAllNeutralPlayers();
        return checkIfPlayerWon(p_GamePhase);
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
     */
    private void clearAllNeutralPlayers() {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            l_Player.removeNeutralPlayer();
        }
    }

    /**
     * Check if the player won the game after every execution phase
     *
     * @param p_GamePhase the next phase based on the status of player
     * @return the gamephase it has to change to based on the win
     */
    public GamePhase checkIfPlayerWon(GamePhase p_GamePhase) {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            if (l_Player.getCapturedCountries().size() == d_GameMap.getCountries().size()) {
                d_Logger.log("The Player " + l_Player.getName() + " won the game.");
                d_Logger.log("Exiting the game...");
                d_GameMap.setGamePhase(d_ExitGamePhase);
                return p_GamePhase.nextState(d_ExitGamePhase);
            }
        }
        d_GameMap.setGamePhase(d_ReinforcementGamePhase);
        return p_GamePhase.nextState(d_ReinforcementGamePhase);
    }

}
