package model;

import java.util.Random;

/**
 * This is an Enum for Cards.
 *
 */
public enum Card{
    BOMB("Bomb"),
    BLOCKADE("Blockade"),
    AIRLIFT("Airlift"),
    DIPLOMACY("Diplomacy");

    private final String d_CardType;

    /**
     * Instantiates a new card.
     *
     * @param p_CardType the card type
     */

    Card(String p_CardType){
        this.d_CardType = p_CardType;
    }

    /**
     * Every time player conquer one country, he can get a random card by this method
     * getEnumConstants function can get the list of cards.
     *
     * @param p_CardObject the card object
     * @return one random kind of card
     */
    public static Card getCard(Class<Card> p_CardObject)
    {
        return p_CardObject.getEnumConstants()[new Random().nextInt(p_CardObject.getEnumConstants().length)];
    }
}

