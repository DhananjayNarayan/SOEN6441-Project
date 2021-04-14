package model.strategy.player;

import model.*;
import model.order.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AggressiveStrategy extends PlayerStrategy implements  Serializable  {

    private static final GameMap d_GameMap = GameMap.getInstance();

    AggressiveStrategy(Player p_Player) {
        super(p_Player);
    }

    public Country determineStrongestCountry(Player p_player) {
        List<Country> countryList = p_player.getCapturedCountries();
        Country l_strongestCountry = countryList.get(0);
        for (Country country : countryList) {
            if (country.getArmies() > l_strongestCountry.getArmies())
                l_strongestCountry = country;
        }

        return l_strongestCountry;
    }

    public String createCommand(){
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
            for(Card l_card: d_Player.getPlayerCards()){
                if(l_card.getCardType() == CardType.BOMB) {
                    for (Country l_c : d_Player.getCapturedCountries()) {
                        for (Country l_b : l_c.getNeighbors()) {
                            if ( !l_b.getPlayer().equals(d_Player) && l_b.getArmies() > 0 ) {
                                l_Commands.add(0, "bomb");
                                l_Commands.add(1,l_b.getName());
                                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                                l_Order = new BombOrder();
                                l_Order.setOrderInfo(OrderCreater.GenerateBombOrderInfo(l_CommandsArr, d_Player));

                            }
                        }
                    }
                }
            }
        }




             return null;
    }

}
