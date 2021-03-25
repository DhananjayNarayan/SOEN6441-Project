package model.order;

import model.CardType;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * The class is a extended from Order, and overrides the methods from Order
 */
public class NegotiateOrder extends Order {
    LogEntryBuffer dLeb = new LogEntryBuffer();
    private final GameMap d_GameMap;

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
        if (validateCommand()) {
            System.out.println("The order: " + getType() + " " + l_NeutralPlayer.getName());
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
            dLeb.logInfo("Player doesn't have the card to be used.");
            return false;
        }
        //check if player is valid
        if (l_NeutralPlayer == null) {
            System.err.println("The Player is not valid.");
            dLeb.logInfo("The Player is not valid.");
            return false;
        }
        // check if the player exists
        System.out.println(d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName()));
        if (!d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName())) {
            System.err.println("The Player name doesn't exist.");
            dLeb.logInfo("The Player name doesn't exist.");
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Negotiated with " + getOrderInfo().getNeutralPlayer().getName() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        dLeb.logInfo("Negotiated with" + getOrderInfo().getNeutralPlayer().getName() + ".");
    }
}
