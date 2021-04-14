package model.strategy.player;

import model.Country;
import model.Player;
import model.order.AdvanceOrder;
import model.order.DeployOrder;
import model.order.Order;
import model.*;
import model.order.*;
import utils.logger.LogEntryBuffer;

import java.io.Serializable;
import java.util.*;


/**
 *	Class that implements the Benevolent Player Strategy
 */
public class BenevolentStrategy extends PlayerStrategy implements Serializable {

    /**
     *  a random number
     */
    /**
     * Random variable
     */
    private static final Random d_Random = new Random();
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    private static final GameMap d_GameMap = GameMap.getInstance();

    /**
     * constructor for  BenevolentStrategy
     * @param p_player given Player
     */

    public BenevolentStrategy(Player p_player) {
        super(p_player);
    }

    /**
     *  Method to get the conquered country which has minimum army number
     *  @return the weakest conquered country
     */
    public Country getWeakestConqueredCountry(Player p_player) {
        List<Country> countryList = p_player.getCapturedCountries();
        Country l_WeakestCountry = countryList.get(0);
        for (Country l_Country : countryList) {
            if (l_Country.getArmies() < l_WeakestCountry.getArmies())
                l_WeakestCountry = l_Country;
        }
        return l_WeakestCountry;
    }

    public String createCommand() {
        d_Logger.log("Issuing Orders for the Aggressive Player - "+ d_Player.getName());
        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;
        Country l_WeakestCountry = getWeakestConqueredCountry(d_Player);
        int l_armiesReinforce= d_Player.getReinforcementArmies();

        // Deploy armies to weakest Country
        l_Commands.add(0,"deploy");
        l_Commands.add(1,l_WeakestCountry.getName());
        l_Commands.add(2,String.valueOf((l_armiesReinforce))) ;
        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
        l_Order = new DeployOrder();
        l_Order.setOrderInfo(OrderCreater.GenerateDeployOrderInfo(l_CommandsArr, d_Player));



        //if have a negotiate card, use it
        if(d_Player.getPlayerCards().size() > 0){
            for(Card l_card: d_Player.getPlayerCards()){
                if (l_card.getCardType() == CardType.DIPLOMACY) {
                    l_Commands.add(0, "diplomacy");
                    l_Commands.add(1, getRandomPlayer(d_Player).getName());
                    l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    l_Order = new NegotiateOrder();
                    l_Order.setOrderInfo(OrderCreater.GenerateNegotiateOrderInfo(l_CommandsArr, d_Player));
                }
            }
        }
//
        for (Country l_C : l_WeakestCountry.getNeighbors()) {
            if(l_C.getPlayer().getName().equals(d_Player.getName())) {
                l_Commands.add(0, "advance");
                l_Commands.add(1, l_C.getName());
                l_Commands.add(2, l_WeakestCountry.getName());
                l_Commands.add(3, String.valueOf(l_C.getArmies()));
                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
                l_WeakestCountry = l_C;
            }
        }
        return null;
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

    /**
     * Method to implement the Order
     * @return Order
     */
    /**
     * implementation of createOrder
     * @return Order
     */



}
