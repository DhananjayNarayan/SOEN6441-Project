package model.order;

import model.Country;
import model.Player;

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
    private Country d_Departure;
    private String d_Destination;
    private int d_NumberOfArmy;

    /**
     * A function to get the player information
     * @return the object of player
     */
    public Player getPlayer() {

        return d_Player;
    }

    /**
     * A function to set the player information
     * @param d_Player the object of player
     */
    public void setPlayer(Player d_Player) {

        this.d_Player = d_Player;
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
     * @param d_Departure departure country object
     */
    public void setDeparture(Country d_Departure) {

        this.d_Departure = d_Departure;
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
     * @param d_Destination the destination of armies
     */
    public void setDestination(String d_Destination) {

        this.d_Destination = d_Destination;
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
     * @param d_NumberOfArmy the number of armies
     */
    public void setNumberOfArmy(int d_NumberOfArmy) {

        this.d_NumberOfArmy = d_NumberOfArmy;
    }

}
