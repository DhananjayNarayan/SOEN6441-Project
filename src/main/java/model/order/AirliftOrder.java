package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * This class gives the order to execute AirliftOrder, from one country to another.
 * @author Surya Manian
 */
public class AirliftOrder extends Order {
    /**
     * A data member to store the instance of the gamemap.
     */
    private final GameMap d_GameMap;

    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Constructor class for Airlift Order
     */
    public AirliftOrder() {
        super();
        setType("airlift");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Airlift Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_fromCountry = getOrderInfo().getDeparture();
        Country l_toCountry = getOrderInfo().getDestination();
        int p_armyNumberToAirLift = getOrderInfo().getNumberOfArmy();

        if (validateCommand()) {
            l_fromCountry.setArmies(l_fromCountry.getArmies() - p_armyNumberToAirLift);
            l_toCountry.setArmies(l_toCountry.getArmies() + p_armyNumberToAirLift);
            d_Logger.log("The order: " + getType() + " " + p_armyNumberToAirLift + " armies from "+l_fromCountry.getName()+" to "+l_toCountry.getName());
            l_Player.removeCard(CardType.AIRLIFT);
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
        Country l_fromCountry = getOrderInfo().getDeparture();
        Country l_toCountry = getOrderInfo().getDestination();
        int p_armyNumberToAirLift = getOrderInfo().getNumberOfArmy();

        //check if the player is valid
        if (l_Player == null) {
            d_Logger.log("The Player is not valid.");
            return false;
        }
        //check if the player has an airlift card
        if (!l_Player.checkIfCardAvailable(CardType.AIRLIFT)) {
            d_Logger.log("Player doesn't have Airlift Card.");
            return false;
        }
        //check if countries belong to the player
        if (!l_Player.getCapturedCountries().contains(l_fromCountry) || !l_Player.getCapturedCountries().contains(l_toCountry)) {
            d_Logger.log("Source or target country do not belong to the player.");
            return false;

        }
        //check if army number is more than 0
        if (p_armyNumberToAirLift <= 0) {
            d_Logger.log("The number of airlift army should be greater than 0");
            return false;
        }
        //check if army number is more that they own
        if (l_fromCountry.getArmies() < p_armyNumberToAirLift) {
            d_Logger.log("Player has less no. of army in country " + getOrderInfo().getDeparture().getName());
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Airlifted " + getOrderInfo().getNumberOfArmy() + " armies from " + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
        d_Logger.log("---------------------------------------------------------------------------------------------");
    }
}
