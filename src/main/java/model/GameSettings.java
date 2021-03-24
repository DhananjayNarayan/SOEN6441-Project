package model;

import model.strategy.GameStrategy;

import java.util.Objects;

/**
 * Class containing the different strategy settings for advance order calculation.
 *
 * @author Madhuvanthi Hemanathan
 */
public class GameSettings {
    /**
     * Game settings object
     */
    private static GameSettings Settings;
    /**
     * Game strategy object
     */
    private GameStrategy d_Strategy;
    /**
     * The Attacker probability
     */
    public final double ATTACKER_PROBABILITY = 60 / 100d;
    /**
     * The Defender probability
     */
    public final double DEFENDER_PROBABILITY = 70 / 100d;

    /**
     * Constructor for Game Settings
     */
    private GameSettings() {
    }

    /**
     * Method to get the instance of GameSettings
     *
     * @return game settings object
     */
    public static GameSettings getInstance() {
        if (Objects.isNull(Settings)) {
            Settings = new GameSettings();
        }
        return Settings;
    }

    /**
     * Getter for strategy
     *
     * @return the game strategy chosen
     */
    public GameStrategy getStrategy() {
        return d_Strategy;
    }

    /**
     * Setter for game strategy
     *
     * @param p_Strategy the game strategy chosen
     */
    public void setStrategy(GameStrategy p_Strategy) {
        d_Strategy = p_Strategy;
    }

}
