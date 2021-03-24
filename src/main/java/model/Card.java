package model;

/**
 * This is the class to create card and assign the Card Type randomly
 *
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class Card {

    private CardType d_CardType;
    private String d_CardId;

    /**
     * Get the card Id for the Card Object
     *
     * @return Card Id
     */
    public String getCardId() {
        return d_CardId;
    }

    /**
     * Set the Card Id for the Card Object
     *
     * @param p_CardId Card ID
     */
    public void setCardId(String p_CardId) {
        this.d_CardId = p_CardId;
    }

    /**
     * This is a Constructor for Class Card
     */
    public Card() {
        d_CardType = CardType.getRandomCard();
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

