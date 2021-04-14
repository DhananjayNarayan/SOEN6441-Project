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
     * constructor of CheaterStrategy
     * @param p_Player given Player
     */
    CheaterStrategy(Player p_Player){
        super(p_Player);

    }

    /**
     * Implementation of create command
     * @return null
     */
    public String createCommand() {
        d_Logger.log("Issuing Orders for the Cheater Player - "+ d_Player.getName());
        Player l_NeighborOwner = null;

        //find and conquer neighbor countries
        for(Country l_Country : d_Player.getCapturedCountries()){
            for( Country l_Neighbor : l_Country.getNeighbors()){
                if(l_Neighbor.getPlayer() != d_Player)
                {
                        l_NeighborOwner = l_Neighbor.getPlayer();
                        l_NeighborOwner.getCapturedCountries().remove(l_Neighbor);
                        l_Neighbor.setPlayer(d_Player);
                        d_Logger.log("Conquered the neighbor country of enemy - "  + l_Neighbor.getName());
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
