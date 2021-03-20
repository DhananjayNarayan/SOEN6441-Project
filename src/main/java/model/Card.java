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


    Card(String p_CardType){
        this.d_CardType = p_CardType;
    }

    public static Card getCard(Class<Card> p_CardObject)
    {
        return p_CardObject.getEnumConstants()[new Random().nextInt(p_CardObject.getEnumConstants().length)];
    }
}

