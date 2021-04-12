package model.strategy.player;

import controller.GamePlay;
import model.Player;

/**
 * Interface class holding the different types of player.
 *
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public abstract class PlayerStrategy {
    abstract public String createCommand();
    Player d_player = null;


    PlayerStrategy(Player p_player){
        d_player = p_player;
    }
    /**
     * Method which returns the class holding the player gameplay
     * logic based on strategy chosen.
     *
     * @param p_Strategy Player strategy provided
     * @return the respective strategy class
     */
    static PlayerStrategy getStrategy(String p_Strategy,Player d_player) {
        switch (p_Strategy) {
            case "human": {
                return new HumanStrategy();
            }
            case "benevolent": {
                return new BenevolentStrategy(d_player);
            }
        }
        throw new IllegalStateException("not a valid player type");
    }
}
