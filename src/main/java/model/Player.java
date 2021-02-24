package model;

import model.order.Order;
import model.order.OrderCreater;

import java.util.Deque;
import java.util.List;
import java.util.Scanner;


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
    private Deque<Order> d_Orders;
    private int d_ReinforcementArmies;

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
    public Deque<Order> getOrders() {
        return d_Orders;
    }

    /**
     * A function to set the orders
     *
     * @param p_Orders the list of orders
     */
    public void setOrders(Deque<Order> p_Orders) {
        this.d_Orders = p_Orders;
    }

    public void addOrder(Order p_Order) {
        d_Orders.add(p_Order);
    }

    public Order nextOrder() {
        return d_Orders.poll();
    }

    public int getReinforcementArmies() {
        return d_ReinforcementArmies;
    }

    public void setReinforcementArmies(int d_AssignedArmies) {
        this.d_ReinforcementArmies = d_AssignedArmies;
    }

    public void issueOrder(String p_Commands) {
        Boolean l_IssueCommand = true;
        String[] l_CommandArr = p_Commands.split(" ");
        int l_ReinforcementArmies = Integer.parseInt(l_CommandArr[2]);
        if (!deployReinforcementArmiesFromPlayer(l_ReinforcementArmies)) {
            l_IssueCommand = false;
        }
        if (!checkIfCountryExists(l_CommandArr[1], this)) {
            l_IssueCommand = false;
        }
        if (l_IssueCommand) {
            Order l_Order = OrderCreater.createOrder(l_CommandArr, this);
            addOrder(l_Order);
        }
    }

    public boolean checkIfCountryExists(String p_Country, Player p_Player) {
        List<Country> l_ListOfCountries = p_Player.getCapturedCountries();
        for (Country l_Country : l_ListOfCountries) {
            if (l_Country.getName() == p_Country) {
                return true;
            }
        }
        return false;
    }

    public boolean deployReinforcementArmiesFromPlayer(int p_ArmyCount) {
        if (p_ArmyCount > d_ReinforcementArmies) {
            return false;
        }
        d_ReinforcementArmies -= p_ArmyCount;
        return true;
    }

}
