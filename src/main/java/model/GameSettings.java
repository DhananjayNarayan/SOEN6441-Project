package model;

import model.strategy.GameStrategy;

import java.util.Objects;

public class GameSettings {
    private static GameSettings SETTINGS;
    private GameStrategy d_Strategy;
    public final double ATTACKER_PROBABILITY = 60 / 100d;
    public final double DEFENDER_PROBABILITY = 70 / 100d;

    private GameSettings() {
    }

    public static GameSettings getInstance() {
        if (Objects.isNull(SETTINGS)) {
            SETTINGS = new GameSettings();
        }
        return SETTINGS;
    }

    public GameStrategy getStrategy() {
        return d_Strategy;
    }

    public void setStrategy(GameStrategy p_strategy) {
        d_Strategy = p_strategy;
    }

}
