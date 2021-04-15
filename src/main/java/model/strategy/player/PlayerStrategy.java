package model.strategy.player;

import model.Player;

/**
 * Abstract class holding the different types of player.
 *
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public abstract class PlayerStrategy {
    /**
     * player
     */
    static Player d_Player;

    /**
     * Default constructor
     */
    PlayerStrategy() {

    }

    /**
     * declaring abstract method
     *
     * @return command
     */
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
                return new HumanStrategy();
            }
            case "random": {
                return new RandomStrategy();
            }
            case "benevolent": {
                return new BenevolentStrategy();
            }
            case "aggressive": {
                return new AggressiveStrategy();
            }
            case "cheater": {
                return new CheaterStrategy();
            }
        }
        throw new IllegalStateException("not a valid player type");
    }
}
