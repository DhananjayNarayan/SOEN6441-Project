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
     * @param p_cardType The card type
     */
    public Card(CardType p_cardType) {
        d_CardType = p_cardType;
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
     * @param p_obj The object which should be compared
     * @return true if objects are equal else false
     */
    @Override
    public boolean equals(Object p_obj) {
        if (this == p_obj) return true;
        if (!(p_obj instanceof Card)) return false;
        Card l_l_card = (Card) p_obj;
        return d_CardType == l_l_card.d_CardType;
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

