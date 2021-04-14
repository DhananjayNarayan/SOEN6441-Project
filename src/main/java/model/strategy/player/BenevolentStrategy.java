package model.strategy.player;

import model.*;
import model.order.*;
import utils.logger.LogEntryBuffer;
import java.io.Serializable;
import java.util.*;

/**
 *	Class that implements the Benevolent Player Strategy
 *
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class BenevolentStrategy extends PlayerStrategy implements Serializable {

    /**
     *  a random number
     */
    private static final Random d_Random = new Random();
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    /**
     * An instance of gamemap object
     */
    private static final GameMap d_GameMap = GameMap.getInstance();


    /**
     * A function to determine the weakest country from the list of captured countries
     * @param p_Player player object
     * @return The weakest country
     */
    public Country getWeakestConqueredCountry(Player p_Player) {
        List<Country> l_CountryList = p_Player.getCapturedCountries();
        Country l_WeakestCountry = l_CountryList.get(0);
        for (Country l_Country : l_CountryList) {
            if (l_Country.getArmies() < l_WeakestCountry.getArmies())
                l_WeakestCountry = l_Country;
        }
        return l_WeakestCountry;
    }

    /**
     * A function to create the commands for deploying, negotiating and advancing for a Benevolent player
     *
     * @return null if empty
     */
    public String createCommand() {
        d_Player = GameMap.getInstance().getCurrentPlayer();
        d_Logger.log("Issuing Orders for the Benevolent Player - "+ d_Player.getName());
        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;
        Country l_WeakestCountry = getWeakestConqueredCountry(d_Player);
        int l_ArmiesReinforce= d_Player.getReinforcementArmies();

        // Deploy armies to weakest Country
        l_Commands.add(0,"deploy");
        l_Commands.add(1,l_WeakestCountry.getName());
        l_Commands.add(2,String.valueOf((l_ArmiesReinforce))) ;
        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
        l_Order = new DeployOrder();
        l_Order.setOrderInfo(OrderCreater.GenerateDeployOrderInfo(l_CommandsArr, d_Player));

        //if Player has a diplomacy card,then use it
        if(d_Player.getPlayerCards().size() > 0){
            for(Card l_Card: d_Player.getPlayerCards()){
                if (l_Card.getCardType() == CardType.DIPLOMACY) {
                    l_Commands.add(0, "negotiate");
                    l_Commands.add(1, getRandomPlayer(d_Player).getName());
                    l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    l_Order = new NegotiateOrder();
                    l_Order.setOrderInfo(OrderCreater.GenerateNegotiateOrderInfo(l_CommandsArr, d_Player));
                }
            }
        }

// move armies to the weakest country from the other neighbouring countries of the same player
        for (Country l_Country : l_WeakestCountry.getNeighbors()) {
            if(l_Country.getPlayer().getName().equals(d_Player.getName())) {
                l_Commands.add(0, "advance");
                l_Commands.add(1, l_Country.getName());
                l_Commands.add(2, l_WeakestCountry.getName());
                l_Commands.add(3, String.valueOf(l_Country.getArmies()));
                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
                l_WeakestCountry = l_Country;
            }
        }
        return "pass";
    }

/**
 * Get a random player other than itself
 *
 * @param p_Player current player
 * @return Random Player
 */
    protected  Player getRandomPlayer(Player p_Player){
        int l_Index = d_Random.nextInt(d_GameMap.getPlayers().size());
        Player l_Player = (Player) d_GameMap.getPlayers().values().toArray()[l_Index];
        while(l_Player.equals(p_Player)){
            l_Index = d_Random.nextInt(d_GameMap.getPlayers().size());
            l_Player = (Player) d_GameMap.getPlayers().values().toArray()[l_Index];
        }
        return l_Player;
    }
}
