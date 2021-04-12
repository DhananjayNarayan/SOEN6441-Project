package model.order;

import model.Country;
import model.Player;

import java.io.Serializable;

/**
 * A class with the information of Order details
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 */
public class OrderInfo implements Serializable {
    /**
     * Command entered by the player
     */
    private String d_Command;
    /**
     * A player object
     */
    private Player d_Player;
    /**
     * An object for neutral player
     */
    private Player d_NeutralPlayer;
    /**
     * A country object for departure
     */
    private Country d_Departure;
    /**
     * A country object for destination
     */
    private Country d_Destination;
    /**
     * A country object for a target country
     */
    private Country d_TargetCountry;
    /**
     * An integer data member to store the number of armies
     */
    private int d_NumberOfArmy;

    /**
     * function to get the Neutral player
     *
     * @return the Neutral player
     */
    public Player getNeutralPlayer() {
        return d_NeutralPlayer;
    }

    /**
     * function to set the Neutral Player
     *
     * @param d_NeutralPlayer the Neutral player
     */
    public void setNeutralPlayer(Player d_NeutralPlayer) {
        this.d_NeutralPlayer = d_NeutralPlayer;
    }

    /**
     * A function to get the player information
     *
     * @return the object of player
     */
    public Player getPlayer() {
        return d_Player;
    }

    /**
     * A function to set the player information
     *
     * @param p_Player the object of player
     */
    public void setPlayer(Player p_Player) {
        this.d_Player = p_Player;
    }

    /**
     * A function to get the departure of the armies from the order
     *
     * @return the departure country object
     */
    public Country getDeparture() {
        return d_Departure;
    }

    /**
     * A function to set the departure of the armies from the order
     *
     * @param p_Departure departure country object
     */
    public void setDeparture(Country p_Departure) {
        this.d_Departure = p_Departure;
    }

    /**
     * A function to get where the army is going to.
     *
     * @return the destination of armies
     */
    public Country getDestination() {
        return d_Destination;
    }

    /**
     * A function to set the destination of the armies
     *
     * @param p_Destination the destination of armies
     */
    public void setDestination(Country p_Destination) {
        this.d_Destination = p_Destination;
    }


    /**
     * A function to get the number of armies in the order
     *
     * @return the number of armies
     */
    public int getNumberOfArmy() {
        return d_NumberOfArmy;
    }

    /**
     * A function to set the number of armies in the order
     *
     * @param p_NumberOfArmy the number of armies
     */
    public void setNumberOfArmy(int p_NumberOfArmy) {
        this.d_NumberOfArmy = p_NumberOfArmy;
    }

    /**
     * A function to get the target country of the order
     *
     * @return the target country
     */
    public Country getTargetCountry() {
        return this.d_TargetCountry;
    }

    /**
     * A function to set the target country of the order
     *
     * @param p_TargetCountry the target country
     */
    public void setTargetCountry(Country p_TargetCountry) {
        this.d_TargetCountry = p_TargetCountry;
    }

    /**
     * Getter for Command
     *
     * @return command
     */
    public String getCommand(){
        return d_Command;
    }

    /**
     * Setter for command
     *
     * @param p_Command command
     */
    public void setCommand(String p_Command){
        d_Command = p_Command;
    }
}
