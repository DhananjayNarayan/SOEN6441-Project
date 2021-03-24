package model;

/**
 * This is the class to create card and assign the Card Type randomly
 *
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class Card {

    private CardType d_CardType;
    private boolean used = false;

    /**
     * This is a Constructor for Class Card
     */
    public Card() {
        d_CardType = CardType.getRandomCard();
    }


    public boolean isUsed() {
        return used;
    }

    public void used() {
        used = true;
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
}

