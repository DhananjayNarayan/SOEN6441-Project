package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * This class gives the order to execute AirliftOrder, from one country to another.
 */
public class AirliftOrder extends Order {

    LogEntryBuffer d_Leb = new LogEntryBuffer();
    private final GameMap d_GameMap;

    /**
     * Constructor class for Airlift Order
     */
    public AirliftOrder() {
        super();
        setType("airlift");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Negotiate Order
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
            System.out.println("The Player is not valid.");
            return false;
        }
        //check if the player has an airlift card
        if (!l_Player.checkIfCardAvailable(CardType.AIRLIFT)) {
            System.out.println("Player doesn't have Airlift Card.");
            return false;
        }
        //check if countries belong to the player
        if (!l_Player.getCapturedCountries().contains(l_fromCountry) || !l_Player.getCapturedCountries().contains(l_toCountry)) {
            System.out.println("Source or target country do not belong to the player.");
            return false;

        }
        //check if army number is more than 0
        if (p_armyNumberToAirLift <= 0) {
            System.out.println("The number of airlift army should be greater than 0");
            return false;
        }
        //check if army number is more that they own
        if (l_fromCountry.getArmies() < p_armyNumberToAirLift) {
            System.out.println("Player has less no. of army in country " + getOrderInfo().getDeparture());
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Airlifted " + getOrderInfo().getNumberOfArmy() + " armies from " + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_Leb.logInfo("Airlifted " + getOrderInfo().getNumberOfArmy() + " armies from " + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
    }
}

