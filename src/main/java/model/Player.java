package model;

import java.util.List;


/**
 * Concrete class with the details of the player
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 * @version 1.0.0
 */

public class Player {
    private int d_Id;
    private String d_Name;
    private int d_OrderCount;
    private List<Country> d_CapturedCountries;
    private List<Order> d_Orders;

    /**
     * A function to get the player ID
     *
     * @return the ID of player
     */
    public int getId() {
        return d_Id;
    }

    /**
     * A function to set the player ID
     *
     * @param p_Id Player ID value
     */
    public void setId(int p_Id) {
        this.d_Id = p_Id;
    }

    /**
     * A function to get the name of the Player
     *
     * @return player name
     */
    public String getName() {
        return d_Name;
    }

    /**
     * A function to set the name of the player
     *
     * @param p_Name Name of the player
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * A function to get the Order Count
     *
     * @return the order count
     */
    public int getOrderCount() {
        return d_OrderCount;
    }

    /**
     * A function to set the Order Count
     *
     * @param p_OrderCount the value of order count
     */
    public void setOrderCount(int p_OrderCount) {
        this.d_OrderCount = p_OrderCount;
    }

    /**
     * A function to get the list of captured countries
     *
     * @return The list of captured countries
     */
    public List<Country> getCapturedCountries() {
        return d_CapturedCountries;
    }

    /**
     * A function to record/set the captured countries
     *
     * @param p_CapturedCountries List of the captured countries
     */
    public void setCapturedCountries(List<Country> p_CapturedCountries) {
        this.d_CapturedCountries = p_CapturedCountries;
    }

    /**
     * A function to get the list of orders
     *
     * @return list of orders
     */
    public List<Order> getOrders() {
        return d_Orders;
    }

    /**
     * A function to set the orders
     *
     * @param p_Orders the list of orders
     */
    public void setOrders(List<Order> p_Orders) {
        this.d_Orders = p_Orders;
    }
}
