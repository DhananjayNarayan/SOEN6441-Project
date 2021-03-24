package model.order;

import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;
import utils.LogEntryBuffer;

/**
 * This class implements the bomb order card
 * 
 * @author Prathika 
 */
public class BombOrder extends Order{

    private GameMap d_GameMap;

    LogEntryBuffer d_leb = new LogEntryBuffer();

    public BombOrder() {
        super();
        setType("bomb");
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();
        if (validateCommand()) {
            int l_Armies = l_Country.getArmies();
            int l_NewArmies = l_Armies/2;
            if (l_NewArmies<0){
                l_NewArmies = 0;
            }
            l_Country.setArmies(l_NewArmies);
            l_Player.removeCard(CardType.BOMB);
            return true;
        }
        return false;
    }

    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();

        if (l_Player == null) {
            System.out.println("The Player is not valid.");
            return false;
        }
        if (!l_Player.checkIfCardAvailable(CardType.BOMB)) {
            System.out.println("Player doesn't have Blockade Card.");
            return false;
        }
        //check whether the target country belongs to the player
        if(l_Player.getCapturedCountries().contains(l_Country)){
            System.out.println("The player cannot destroy armies in his own country.");
            return false;
        }

        //check if DIPLOMACY



        // validate that the country is adjacent to one of the current player’s neighbors.
        Boolean l_Adjacent = false;
        for (Country l_PCountry : l_Player.getCapturedCountries()) {
            for (Country l_NCountry : l_PCountry.getNeighbors()) {
                if (l_NCountry.getName().equals(l_Country.getName())) {
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

    @Override
    public void printOrderCommand() {
        System.out.println("Bomb Order issued by player:" + getOrderInfo().getPlayer().getName() + " on Country" + getOrderInfo().getTargetCountry().getName());
        System.out.println("---------------------------------------------------------------------------------------------");

    }
}

