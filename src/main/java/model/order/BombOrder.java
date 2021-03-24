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
public class BombOrder extends Order{

    private GameMap d_GameMap;
    LogEntryBuffer d_Leb = new LogEntryBuffer();

    /**
     * This is the Constructor for Bomb Order class
     */
    public BombOrder() {
        super();
        setType("bomb");
        d_GameMap = GameMap.getInstance();
    }

    /**
     *  This is the execute method for bomb order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_TargetCountry = getOrderInfo().getTargetCountry();
        if (validateCommand()) {
            int l_Armies = l_TargetCountry.getArmies();
            int l_NewArmies = l_Armies/2;
            if (l_NewArmies<0){
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
            System.out.println("The Player is not valid.");
            return false;
        }
        // validate that the player has the bomb card
        if (!l_Player.checkIfCardAvailable(CardType.BOMB)) {
            System.out.println("Player doesn't have Bomb Card.");
            return false;
        }
        //check whether the target country belongs to the player
        if(l_Player.getCapturedCountries().contains(l_TargetCountry)){
            System.out.println("The player cannot destroy armies in his own country.");
            return false;
        }

        //diplomacy
        if (l_Player.getNeutralPlayers().contains(l_TargetCountry.getPlayer())) {
            System.out.printf("Truce between %s and %s\n", l_Player.getName(), l_TargetCountry.getPlayer().getName());
            l_Player.getNeutralPlayers().remove(l_TargetCountry.getPlayer());
            l_TargetCountry.getPlayer().getNeutralPlayers().remove(l_Player);
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
        if (!l_Adjacent){
            System.out.println("The target country is not adjacent to one of the countries that belong to the player.");
            return false;
        }
        return true;
    }

    /**
     * This is the method to print
     *
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Bomb Order issued by player: " + getOrderInfo().getPlayer().getName() + " on Country: " + getOrderInfo().getTargetCountry().getName());
        System.out.println("---------------------------------------------------------------------------------------------");
        d_Leb.logInfo("Bomb on" + getOrderInfo().getTargetCountry().getName() + " by " + getOrderInfo().getPlayer().getName());
    }
}

