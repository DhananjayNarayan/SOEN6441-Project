package model.strategy.player;
/**
 * Interface class holding the different types of player.
 *
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public interface PlayerStrategy {


    String createCommand();

    /**
     * Method which returns the class holding the player gameplay
     * logic based on strategy chosen.
     *
     * @param p_Strategy Player strategy provided
     * @return the respective strategy class
     */
    static PlayerStrategy getStrategy(String p_Strategy) {
        switch (p_Strategy) {
            case "human": {
                return new HumanStrategy();
            }
            case "random":
            {
//                return new RandomStrategy();
            }
        }
        throw new IllegalStateException("not a valid player type");
    }
}
