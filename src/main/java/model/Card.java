package model;

import java.util.Random;

/**
 * This is the class to create card and assign the Card Type randomly
 *
 * @author Prathika Suvarna
 * @version 1.0.0
 *
 */
public class Card {

    private final String d_CardType;

    /**
     * This is an Enum for Card Type
     *
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

        public static String getRandomCard()
        {
            Random d_Random = new Random();
            CardType d_Type= values()[d_Random.nextInt(values().length)];
            return d_Type.name();}
    }

    /**
     * This is a Constructor for Class Card
     *
     */

    public Card()
    {
            d_CardType=CardType.getRandomCard();
    }

    /**
     * This method is used to get the Card Type
     *
     * @return the Card Type
     */
    public String getCardType() {

            return d_CardType;

    }
}

