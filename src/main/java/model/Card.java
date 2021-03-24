package model;

import java.util.Objects;

/**
 * This is the class to create card and assign the Card Type randomly
 *
 * @author Prathika Suvarna
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

    public void setCardType(CardType p_cardType) {
        d_CardType = p_cardType;
    }

    @Override
    public boolean equals(Object p_obj) {
        if (this == p_obj) return true;
        if (!(p_obj instanceof Card)) return false;
        Card l_l_card = (Card) p_obj;
        return d_CardType == l_l_card.d_CardType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(d_CardType);
    }
}

