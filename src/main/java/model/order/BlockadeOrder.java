package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;


/**
 * This class helps in executing the Blockade Card
 *
 * @author Dhananjay
 */
public class BlockadeOrder extends Order {
    /**
     * A Gamemap object
     */
    private final GameMap d_GameMap;
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Constructor for class Blockade Order
     */
    public BlockadeOrder() {
        super();
        setType("blockade");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Execute the Blockade Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();
        if (validateCommand()) {
            l_Country.setArmies(l_Country.getArmies() * 3);
            l_Player.getCapturedCountries().remove(l_Country);
            l_Country.setPlayer(null);
            d_Logger.log("The order: " + getType() + " " + l_Country.getName());
            l_Player.removeCard(CardType.BLOCKADE);
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
        Country l_Country = getOrderInfo().getTargetCountry();

        if (l_Player == null) {
            System.err.println("The Player is not valid.");
            d_Logger.log("The Player is not valid.");
            return false;
        }

        if (l_Country.getPlayer() != l_Player) {
            System.err.println("The target country does not belong to the player");
            d_Logger.log("The target country does not belong to the player");
            return false;
        }
        if (!l_Player.checkIfCardAvailable(CardType.BLOCKADE)) {
            System.err.println("Player doesn't have Blockade Card.");
            d_Logger.log("Player doesn't have Blockade Card.");
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Blockade on " + getOrderInfo().getTargetCountry().getName() + " by " + getOrderInfo().getPlayer().getName());
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}