package model;

import java.util.Objects;

/**
 * This is the class to create card and assign the Card Type randomly
 *
 * @author Prathika Suvarna
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class Card {
    /**
     * Data member to hold cardtype
     */
    private CardType d_CardType;

    /**
     * This is a Constructor for Class Card
     */
    public Card() {
        d_CardType = CardType.getRandomCard();
    }

    /**
     * Constructor to create card object with specified card type
     *
     * @param p_CardType The card type
     */
    public Card(CardType p_CardType) {
        d_CardType = p_CardType;
    }


    /**
     * This method is used to get the Card Type
     *
     * @return the Card Type
     */
    public CardType getCardType() {
        return d_CardType;
    }

    /**
     * Setter for Card type
     *
     * @param p_cardType The card type
     */
    public void setCardType(CardType p_cardType) {
        d_CardType = p_cardType;
    }

    /**
     * Method to check the object equality
     *
     * @param p_Obj The object which should be compared
     * @return true if objects are equal else false
     */
    @Override
    public boolean equals(Object p_Obj) {
        if (this == p_Obj) return true;
        if (!(p_Obj instanceof Card)) return false;
        Card l_Card = (Card) p_Obj;
        return d_CardType == l_Card.d_CardType;
    }

    /**
     * Method to return the hashcode of object.
     *
     * @return object's hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(d_CardType);
    }
}

