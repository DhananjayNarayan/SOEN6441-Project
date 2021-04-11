package model.strategy.player;

import java.util.Random;

    /**
     * This is the enum class for Player Strategy type
     *
     */
    public enum PlayerStrategyType {
        /**
         * Human Strategy Type
         */
        HUMAN,
        /**
         * Aggressive Player Strategy Type
         */
        AGGRESSIVE,
        /**
         * Benevolent Player Strategy Type
         */
        BENEVOLENT,
        /**
         *  Random Player Strategy Type
         */
        RANDOM,
        /**
         * Cheater Player Strategy Type
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


