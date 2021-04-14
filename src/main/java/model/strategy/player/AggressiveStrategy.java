package model.strategy.player;

import model.*;
import model.order.*;
import utils.logger.LogEntryBuffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to implement the Aggressive strategy for a player
 * @author Dhananjay Narayan
 */
public class AggressiveStrategy extends PlayerStrategy implements  Serializable  {
    /**
     * An instance of gamemap object
     */
    private static final GameMap d_GameMap = GameMap.getInstance();
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Constructor for AggressiveStrategy
     * @param p_Player player object
     */
    AggressiveStrategy(Player p_Player) {
        super(p_Player);
    }

    /**
     * A function to determine a strongest country from the list of captured countries
     * @param p_player player object
     * @return The strongest country
     */
    public Country determineStrongestCountry(Player p_player) {
        List<Country> countryList = p_player.getCapturedCountries();
        Country l_StrongestCountry = countryList.get(0);
        for (Country l_Country : countryList) {
            if (l_Country.getArmies() > l_StrongestCountry.getArmies())
                l_StrongestCountry = l_Country;
        }

        return l_StrongestCountry;
    }

    /**
     * A function to create the commands for deploying, advancing and bombing for an Aggressive player
     * @return null
     */
    public String createCommand(){
        d_Logger.log("Issuing Orders for the Aggressive Player - "+ d_Player.getName());
        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;
        Country l_StrongCountry = determineStrongestCountry(d_Player);
        int l_armiesReinforce= d_Player.getReinforcementArmies();

        // Deploy armies to strongestCountry
        l_Commands.add(0,"deploy");
        l_Commands.add(1,l_StrongCountry.getName());
        l_Commands.add(2,String.valueOf((l_armiesReinforce))) ;
        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
        l_Order = new DeployOrder();
        l_Order.setOrderInfo(OrderCreater.GenerateDeployOrderInfo(l_CommandsArr, d_Player));


        //Bomb an opponent Country if it has more than 1 army
        if(d_Player.getPlayerCards().size() > 0){
            for(Card l_Card: d_Player.getPlayerCards()){
                if(l_Card.getCardType() == CardType.BOMB) {
                    for (Country l_C : d_Player.getCapturedCountries()) {
                        for (Country l_B : l_C.getNeighbors()) {
                            if ( !l_B.getPlayer().equals(d_Player) && l_B.getArmies() > 0 ) {
                                l_Commands.add(0, "bomb");
                                l_Commands.add(1,l_B.getName());
                                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                                l_Order = new BombOrder();
                                l_Order.setOrderInfo(OrderCreater.GenerateBombOrderInfo(l_CommandsArr, d_Player));

                            }
                        }
                    }
                }
            }
        }

        // Attack/Advance command
        for (Country l_C : l_StrongCountry.getNeighbors()) {
            if (!(l_C.getPlayer().equals(d_Player))) {
                l_Commands.add(0, "advance");
                l_Commands.add(1,l_StrongCountry.getName());
                l_Commands.add(2,l_C.getName());
                l_Commands.add(3,String.valueOf(l_StrongCountry.getArmies())) ;
                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr,d_Player));
                l_StrongCountry = l_C;
            }
        }

/*
// Moving the armies to other next countries of players. Alternate is to move the armies to a random self owned country if we have to change.
        for (Country l_C : l_StrongCountry.getNeighbors()) {
            l_Commands.add(0, "advance");
            l_Commands.add(1,l_StrongCountry.getName());
            l_Commands.add(2,l_C.getName());
            l_Commands.add(3,String.valueOf(l_StrongCountry.getArmies())) ;
            l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
            l_Order = new AdvanceOrder();
            l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr,d_Player));
            l_StrongCountry = l_C;

        }*/

// Moving the armies to other next countries of players. Alternate is to move the armies to a random self owned country if we have to change.
        for (Country l_c : l_StrongCountry.getNeighbors()) {
            if(l_c.getPlayer().equals(d_Player)) {
                l_Commands.add(0, "advance");
                l_Commands.add(1, l_StrongCountry.getName());
                l_Commands.add(2, l_c.getName());
                l_Commands.add(3, String.valueOf(l_StrongCountry.getArmies()));
                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
                l_StrongCountry = l_c;
            }

    }
        return null;
}
}
