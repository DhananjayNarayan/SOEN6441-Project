package model;

import java.util.Random;

/**
 * This is the enum class for card type
 *
 */
public enum CardType {
    /**
     * Bomb Card Type
     */
    BOMB,
    /**
     * Blockade Card Type
     */
    BLOCKADE,
    /**
     * Airlift Card Type
     */
    AIRLIFT,
    /**
     * Diplomacy Card Type
     */
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