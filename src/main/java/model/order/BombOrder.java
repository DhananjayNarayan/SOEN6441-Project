package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.logger.LogEntryBuffer;

/**
 * This class implements the bomb order card
 *
 * @author Prathika
 */
public class BombOrder extends Order {

    /**
     * the Game Map Object
     */
    private GameMap d_GameMap;
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * This is the Constructor for Bomb Order class
     */
    public BombOrder() {
        super();
        setType("bomb");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * This is the execute method for bomb order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_TargetCountry = getOrderInfo().getTargetCountry();
        d_Logger.log("---------------------------------------------------------------------------------------------");
        d_Logger.log(getOrderInfo().getCommand());
        if (validateCommand()) {
            int l_Armies = l_TargetCountry.getArmies();
            int l_NewArmies = l_Armies / 2;
            if (l_NewArmies < 0) {
                l_NewArmies = 0;
            }
            l_TargetCountry.setArmies(l_NewArmies);
            l_Player.removeCard(CardType.BOMB);
            return true;
        }
        return false;
    }

    /**
     * This method Contains the Validations for the bomb command
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_TargetCountry = getOrderInfo().getTargetCountry();

        if (l_Player == null) {
            System.err.println("The Player is not valid.");
            d_Logger.log("The Player is not valid.");
            return false;
        }
        // validate that the player has the bomb card
        if (!l_Player.checkIfCardAvailable(CardType.BOMB)) {
            System.err.println("Player doesn't have Bomb Card.");
            d_Logger.log("Player doesn't have Bomb Card.");
            return false;
        }

        //check whether the target country belongs to the player
        if (l_Player.getCapturedCountries().contains(l_TargetCountry)) {
            System.err.println("The player cannot destroy armies in his own country.");
            d_Logger.log("The player cannot destroy armies in his own country.");
            return false;
        }

        // validate that the country is adjacent to one of the neighbors of the current player
        Boolean l_Adjacent = false;
        for (Country l_PlayerCountry : l_Player.getCapturedCountries()) {
            for (Country l_NeighbourCountry : l_PlayerCountry.getNeighbors()) {
                if (l_NeighbourCountry.getName().equals(l_TargetCountry.getName())) {
                    l_Adjacent = true;
                    break;
                }
            }
        }
        if (!l_Adjacent) {
            System.err.println("The target country is not adjacent to one of the countries that belong to the player.");
            d_Logger.log("The target country is not adjacent to one of the countries that belong to the player.");
            return false;
        }

        //Check diplomacy
        if (l_Player.getNeutralPlayers().contains(l_TargetCountry.getPlayer())) {
            System.err.printf("Truce between %s and %s\n", l_Player.getName(), l_TargetCountry.getPlayer().getName());
            d_Logger.log("Truce between" + l_Player.getName() + "and " + l_TargetCountry.getPlayer().getName());
            l_Player.getNeutralPlayers().remove(l_TargetCountry.getPlayer());
            l_TargetCountry.getPlayer().getNeutralPlayers().remove(l_Player);
            return false;
        }
        return true;
    }

    /**
     * This is the method to print
     */
    @Override
    public void printOrderCommand() {
        d_Logger.log("Bomb Order issued by player: " + getOrderInfo().getPlayer().getName()
                + " on Country: " + getOrderInfo().getTargetCountry().getName());
        d_Logger.log("------------------------------------------------------------------------" +
                "---------------------");
    }
}

