package model;

import java.util.Random;

/**
 * This is an Enum for Cards.
 *
 */
public class Card {

    private final String d_CardType;

    public enum CardType {
        BOMB,
        BLOCKADE,
        AIRLIFT,
        DIPLOMACY;

        public static String getRandomCard()
        {
            Random d_Random = new Random();
            CardType d_Type= values()[d_Random.nextInt(values().length)];
            return d_Type.name();}
    }


        /**
         *
         *
         */

        public Card() {
            d_CardType=CardType.getRandomCard();
        }

        /**
         *
         *
         * @return the card type
         */
        public String getCardType() {

            return d_CardType;

        }
}

