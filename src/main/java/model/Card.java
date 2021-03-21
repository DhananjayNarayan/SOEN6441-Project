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
            Random random = new Random();
            CardType c= values()[random.nextInt(values().length)];
            return c.name();}
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

