package model.strategy.player;

import model.Player;

/**
 * Interface class holding the different types of player.
 *
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public abstract class PlayerStrategy {
    static Player d_Player;
    PlayerStrategy(){

    }
    PlayerStrategy(Player p_Player){
        d_Player = p_Player;
    }
    public abstract String createCommand();

    /**
     * Method which returns the class holding the player gameplay
     * logic based on strategy chosen.
     *
     * @param p_Strategy Player strategy provided
     * @return the respective strategy class
     */
    public static PlayerStrategy getStrategy(String p_Strategy) {
        switch (p_Strategy) {
            case "human": {
                return new HumanStrategy(d_Player);
            }
            case "random":
            {
                return new RandomStrategy(d_Player);
            }
        }
        throw new IllegalStateException("not a valid player type");
    }
}
