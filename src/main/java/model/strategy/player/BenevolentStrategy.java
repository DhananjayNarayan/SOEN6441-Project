package model.strategy.player;

import model.Country;
import model.Player;
import model.order.AdvanceOrder;
import model.order.DeployOrder;
import model.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 *	Class that implements the Benevolent Player Strategy
 */
public class BenevolentStrategy extends PlayerStrategy {

    /**
     * Armies to deploy
     */
    private int d_armiesToDeploy;

    /**
     * constructor for  BenevolentStrategy
     * @param p_player given Player
     */

    public BenevolentStrategy(Player p_player) {
        super(p_player);
        d_armiesToDeploy=p_player.getReinforcementArmies();
    }

    /**
     *  Method to get the conquered country which has minimum army number
     *  @return the weakest conquered country
     */
    protected Country getWeakestConqueredCountry() {
        Country l_weakestCountry = null;
        int l_minArmyNum=Integer.MAX_VALUE;
        for(Country l_Country : d_player.getCapturedCountries()) {
            if(l_Country.getArmies()<l_minArmyNum) {
                l_minArmyNum=l_Country.getArmies();
                l_weakestCountry=l_Country;
            }
        }
        return l_weakestCountry;
    }

    /**
     * Method to implement the Order
     * @return Order
     */
    public Order createOrder() {

        Order l_order=null;

       /*? if(!d_player.getIsAlive())
            return null;*/

        //deploy to weakest country
        if(d_armiesToDeploy>0) {
            Country l_weakestCountry=getWeakestConqueredCountry();
            l_order = new DeployOrder();
            d_armiesToDeploy-=d_armiesToDeploy;
            return l_order;
        }
        //move armies to reinforce its weaker country
        //sort by army number with ascending order
        List<Object> l_conqueredCountries=Arrays.asList(this.d_player.getCapturedCountries().toArray());
        Collections.sort(l_conqueredCountries,new Comparator<Object>() {
            @Override
            public int compare(Object l_obj1,Object l_obj2) {
                Country l_c1=(Country) l_obj1;
                Country l_c2=(Country) l_obj2;
                if (l_c1.getArmies()>l_c2.getArmies()) {
                    return 1;
                }else if (l_c1.getArmies()<l_c2.getArmies()) {
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        //move army to weaker country
        for(Object l_obj:l_conqueredCountries) {
            Country l_countryTo=(Country)l_obj;
            for(Country l_countryFrom:l_countryTo.getNeighbors()) {
                if(l_countryFrom.getArmies()>l_countryTo.getArmies() && l_countryFrom.getPlayer() == l_countryTo.getPlayer()) {
                    int diff=l_countryFrom.getArmies()-l_countryTo.getArmies();
                    if(diff>1) {
                        l_order = new AdvanceOrder();
                        return l_order;
                    }
                }
            }
        }
        //d_player.setHasFinisedIssueOrder(true);
        return null;
    }

    @Override
    public String createCommand() {
        return null;
    }
}
