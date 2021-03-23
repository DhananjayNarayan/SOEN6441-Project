package model;

import java.util.Random;

/**
 * This is an Enum for Card Type
 */
public enum CardType {
    BOMB,
    BLOCKADE,
    AIRLIFT,
    DIPLOMACY;

    /**
     * This method assigns a Random Card from the Enum of Card types
     *
     * @return The random Card Name
     */
    public static CardType getRandomCard() {
        Random d_Random = new Random();
        CardType d_Type = values()[d_Random.nextInt(values().length)];
        return d_Type;
    }
}