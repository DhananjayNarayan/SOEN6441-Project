package model.strategy.player;

import model.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheaterStrategy extends PlayerStrategy implements  Serializable {

    private static final GameMap d_GameMap = GameMap.getInstance();

    List<Country> l_cheatCountryList;

    CheaterStrategy(Player p_player){
        super(p_player);
        l_cheatCountryList = new ArrayList<>();

    }

    public String createCommand() {

        Player neighborOwner = null;

        for(Country l_Country : d_Player.getCapturedCountries()){
            for( Country l_Neighbor : l_Country.getNeighbors()){
                if(l_Neighbor.getPlayer() != d_Player){
                    if(!l_cheatCountryList.contains(l_Neighbor)) {
                        neighborOwner = l_Neighbor.getPlayer();
                        neighborOwner.getCapturedCountries().remove(l_Neighbor);
                        l_Neighbor.setPlayer(d_Player);
                        System.out.println("Conquered the neighbor country of enemy - "  + l_Neighbor.getName());
                    }
                }
            }
        }

        for(Country l_Country : d_Player.getCapturedCountries()){
            for( Country l_Neighbor : l_Country.getNeighbors()) {
                if (l_Neighbor.getPlayer() != d_Player) {
                    l_Country.setArmies(l_Country.getArmies() * 2);
                    System.out.println("Armies doubled in Cheater Player's country"  + l_Country.getName());
                }
            }
        }

        return null;
    }
}
