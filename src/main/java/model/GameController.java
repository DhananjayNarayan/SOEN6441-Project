package model;

/**
 * Interface containing {@code start} method for the
 * controllers of the game phases to implement
 *
 * @author Madhuvanthi Hemanathan
 * @version 1.0
 */

public interface GameController {
    /**
     *
     * @param p_GamePhase holding the current game phase
     * @return each phase return the next game phase after it
     * @throws Exception If an issue occurred
     */
    GamePhase start(GamePhase p_GamePhase) throws Exception;
}
