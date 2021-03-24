package model.order;

import model.CardType;
import model.GameMap;
import model.Player;
import utils.LogEntryBuffer;

public class NegotiateOrder extends Order {
    private GameMap d_GameMap;
    LogEntryBuffer d_leb = new LogEntryBuffer();

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
        System.out.println("The order: " + getType() + " " + l_NeutralPlayer.getName());
        if (validateCommand()) {
            Player l_Player = getOrderInfo().getPlayer();
            l_Player.addNeutralPlayers(l_NeutralPlayer);
            l_NeutralPlayer.addNeutralPlayers(l_Player);
            //set card as used. Used cards will be removed
            l_Player.removeCard(CardType.AIRLIFT);
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
            System.out.println("Player doesn't have the card to be used.");
            return false;
        }
        //check if player is valid
        if (l_NeutralPlayer == null || l_Player == null) {
            System.out.println("The Player is not valid.");
            return false;
        }
        // check if the player exists
        if (!d_GameMap.getPlayers().containsKey(l_NeutralPlayer.getName())) {
            System.out.println("The Player name doesn't exist.");
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
        d_leb.logInfo("Negotiated with" + getOrderInfo().getNeutralPlayer().getName() + ".");
    }
}
