package model.order;

import model.CardType;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * The class is a extended from Order, and overrides the methods from Order
 */
public class NegotiateOrder extends Order {
    /**
     * A gamemap object
     */
    private final GameMap d_GameMap;
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Constructor for class Negotiate Order
     */
    public NegotiateOrder() {
        super();
        setType("negotiate");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Negotiate Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_NeutralPlayer = getOrderInfo().getNeutralPlayer();
        d_Logger.log("---------------------------------------------------------------------------------------------");
        d_Logger.log(getOrderInfo().getCommand());
        if (validateCommand()) {
            Player l_Player = getOrderInfo().getPlayer();
            l_Player.addNeutralPlayers(l_NeutralPlayer);
            l_NeutralPlayer.addNeutralPlayers(l_Player);
            l_Player.removeCard(CardType.DIPLOMACY);
            return true;
        }
        return false;
    }

    /**
     * Validate the command
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Player l_NeutralPlayer = getOrderInfo().getNeutralPlayer();
        //check if the player has the card
        if (!l_Player.checkIfCardAvailable(CardType.DIPLOMACY)) {
            System.err.println("Player doesn't have the card to be used.");
            d_Logger.log("Player doesn't have the card to be used.");
            return false;
        }
        //check if player is valid
        if (l_NeutralPlayer == null) {
            System.err.println("The Player is not valid.");
            d_Logger.log("The Player is not valid.");
            return false;
        }
        // check if the player exists
        d_Logger.log("player exists:" + d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName()));
        if (!d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName())) {
            System.err.println("The Player name doesn't exist.");
            d_Logger.log("The Player name doesn't exist.");
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Negotiated with " + getOrderInfo().getNeutralPlayer().getName() + ".");
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}
