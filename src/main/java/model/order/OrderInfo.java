package model.order;

import model.Country;
import model.Player;

public class OrderInfo {
    private Player d_Player;
    private Country d_Departure;
    private String d_Destination;
    private int d_NumberOfArmy;


    public Player getPlayer() {
        return d_Player;
    }

    public void setPlayer(Player d_Player) {
        this.d_Player = d_Player;
    }



    public Country getDeparture() {
        return d_Departure;
    }

    public void setDeparture(Country d_Departure) {
        this.d_Departure = d_Departure;
    }



    public String getDestination() {
        return d_Destination;
    }

    public void setDestination(String d_Destination) {
        this.d_Destination = d_Destination;
    }




    public int getNumberOfArmy() {
        return d_NumberOfArmy;
    }

    public void setNumberOfArmy(int d_NumberOfArmy) {
        this.d_NumberOfArmy = d_NumberOfArmy;
    }





}
