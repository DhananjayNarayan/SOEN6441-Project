package model.strategy.player;

import java.util.Random;

    /**
     * This is the enum class for card type
     *
     */
    public enum PlayerStrategyType {
        HUMAN,
        /**
         * Bomb Card Type
         */
        AGGRESSIVE,
        /**
         * Blockade Card Type
         */
        BENEVOLENT,
        /**
         * Airlift Card Type
         */
        RANDOM,
        /**
         * Diplomacy Card Type
         */
        CHEATER;

        /**
         * This method assigns a Random Card from the Enum of Card types
         *
         * @return The random Card Name
         */
       /*/ public static model.CardType getRandomCard() {
            Random d_Random = new Random();
            model.CardType d_Type = values()[d_Random.nextInt(values().length)];
            return d_Type;
        }*/
    }


