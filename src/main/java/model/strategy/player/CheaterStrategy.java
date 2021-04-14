package model.strategy.player;

import model.*;
import utils.logger.LogEntryBuffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A class to implement the Cheater strategy for a player
 * @author Surya Manian
 */
public class CheaterStrategy extends PlayerStrategy implements  Serializable {
    /**
     * An instance of gamemap object
     */
    private static final GameMap d_GameMap = GameMap.getInstance();

    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * save the cheat country list
     */
    List<Country> l_cheatCountryList;

    /**
     * constructor of CheaterStrategy
     * @param p_player given Player
     */
    CheaterStrategy(Player p_player){
        super(p_player);
        l_cheatCountryList = new ArrayList<>();

    }

    /**
     * Implementation of create command
     * @return null
     */
    public String createCommand() {
        d_Logger.log("Issuing Orders for the Cheater Player - "+ d_Player.getName());
        Player neighborOwner = null;

        //find and conquer neighbor countries
        for(Country l_Country : d_Player.getCapturedCountries()){
            for( Country l_Neighbor : l_Country.getNeighbors()){
                if(l_Neighbor.getPlayer() != d_Player){
                    if(!l_cheatCountryList.contains(l_Neighbor)) {
                        neighborOwner = l_Neighbor.getPlayer();
                        neighborOwner.getCapturedCountries().remove(l_Neighbor);
                        l_Neighbor.setPlayer(d_Player);
                        d_Logger.log("Conquered the neighbor country of enemy - "  + l_Neighbor.getName());
                    }
                }
            }
        }

        //double the army of a country if it has an enemy
        for(Country l_Country : d_Player.getCapturedCountries()){
            for( Country l_Neighbor : l_Country.getNeighbors()) {
                if (l_Neighbor.getPlayer() != d_Player) {
                    l_Country.setArmies(l_Country.getArmies() * 2);
                    d_Logger.log("Armies doubled in Cheater Player's country"  + l_Country.getName());
                }
            }
        }

        return null;
    }
}
