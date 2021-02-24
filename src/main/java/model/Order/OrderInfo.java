package model;

public class OrderInfo {
    public Player getPlayer() {
        return d_Player;
    }

    public void setPlayer(Player d_Player) {
        this.d_Player = d_Player;
    }

    private Player d_Player;

    public Country getDeparture() {
        return d_Departure;
    }

    public void setDeparture(Country d_Departure) {
        this.d_Departure = d_Departure;
    }

    private Country d_Departure;

    public String getDestination() {
        return d_Destination;
    }

    public void setDestination(String d_Destination) {
        this.d_Destination = d_Destination;
    }


    private String d_Destination;

    public int getNumberOfArmy() {
        return d_NumberOfArmy;
    }

    public void setNumberOfArmy(int d_NumberOfArmy) {
        this.d_NumberOfArmy = d_NumberOfArmy;
    }

    private int d_NumberOfArmy;



}
