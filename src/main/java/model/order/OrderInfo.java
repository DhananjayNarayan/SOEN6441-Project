package model.order;

import model.Card;
import model.Country;
import model.Player;

import java.util.List;

/**
 * A class with the information of Order details
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 */
public class OrderInfo {

    private Player d_Player;

    public String getPlayerName() {
        return d_PlayerName;
    }

    public void setPlayerName(String d_PlayerName) {
        this.d_PlayerName = d_PlayerName;
    }

    private String d_PlayerName;
    private Country d_Departure;
    private String d_Destination;
    private int d_NumberOfArmy;
    private String d_Card;

    /**
     * A function to get the player information
     * @return the object of player
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * A function to set the player information
     * @param p_Player the object of player
     */
    public void setPlayer(Player p_Player) {
        this.d_Player = p_Player;
    }

    /**
     * A function to get the departure of the armies from the order
     * @return  the departure country object
     */
    public Country getDeparture() {
        return d_Departure;
    }

    /**
     * A function to set the departure of the armies from the order
     * @param p_Departure departure country object
     */
    public void setDeparture(Country p_Departure) {
        this.d_Departure = p_Departure;
    }

    /**
     * A function to get where the army is going to.
     * @return the destination of armies
     */
    public String getDestination() {
        return d_Destination;
    }

    /**
     * A function to set the destination of the armies
     * @param p_Destination the destination of armies
     */
    public void setDestination(String p_Destination) {
        this.d_Destination = p_Destination;
    }


    /**
     * A function to get the number of armies in the order
     * @return the number of armies
     */
    public int getNumberOfArmy() {
        return d_NumberOfArmy;
    }

    /**
     * A function to set the number of armies in the order
     * @param p_NumberOfArmy the number of armies
     */
    public void setNumberOfArmy(int p_NumberOfArmy) {
        this.d_NumberOfArmy = p_NumberOfArmy;
    }

    public void setTheCard(String p_ListOfCards){
        d_Card = p_ListOfCards;
    }

    public String getTheCard(){
        return d_Card;
    }

}
