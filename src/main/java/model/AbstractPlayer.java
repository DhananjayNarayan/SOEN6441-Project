package model;
import java.util.List;


/**
 *  An Abstract class with the details of the player
 *
 *  @author Neona Pinto
 *  @author Dhananjay Narayan
 *  @author Surya Manian
 *  @author Madhuvanthi Hemanathan
 *  @author Prathika Suvarna
 *  @version 1.0.0
 */

public abstract class AbstractPlayer {
    private int d_id;
    private String d_name;
    private int d_order_count;
    private List<AbstractCountry> d_captured_countries;
    private List<AbstractOrder> d_orders;

    /**
     * A function to get the player ID
     * @return the ID of player
     */
    public int getId() {
        return d_id;
    }

    /**
     * A function to set the player ID
     * @param p_id Player ID value
     */
    public void setId(int p_id) {
        this.d_id = p_id;
    }

    /**
     * A function to get the name of the Player
     * @return player name
     */
    public String getName() {
        return d_name;
    }

    /**
     * A function to set the name of the player
     * @param p_name Name of the player
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * A function to get the Order Count
     * @return the order count
     */
    public int getOrderCount() {
        return d_order_count;
    }

    /**
     * A function to set the Order Count
     * @param p_order_count the value of order count
     */
    public void setOrderCount(int p_order_count) {
        this.d_order_count = p_order_count;
    }

    /**
     * A function to get the list of captured countries
     * @return The list of captured coyntries
     */
    public List<AbstractCountry> getCapturedCountries() {
        return d_captured_countries;
    }

    /**
     * A function to record/set the captured countries
     * @param p_captured_countries List of the captured countries
     */
    public void setCapturedCountries(List<AbstractCountry> p_captured_countries) {
        this.d_captured_countries = p_captured_countries;
    }

    /**
     * A function to get the list of orders
     * @return list of orders
     */
    public List<AbstractOrder> getOrders() {
        return d_orders;
    }

    /**
     * A function to set the orders
     * @param p_orders the list of orders
     */
    public void setOrders(List<AbstractOrder> p_orders) {
        this.d_orders = p_orders;
    }
}
