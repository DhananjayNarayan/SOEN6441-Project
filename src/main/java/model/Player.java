package model;

import model.order.Order;
import model.order.OrderCreater;
import java.util.*;

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
    private List<Country> d_CapturedCountries = new ArrayList<>();
    private Deque<Order> d_Orders = new ArrayDeque<>();
    private int d_ReinforcementArmies;
    /**
     * Initialising List to hold orders
     */
    public static List<Order> OrderList = new ArrayList<>();

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
    private void setOrders(Deque<Order> p_Orders) {
        this.d_Orders = p_Orders;
    }

    /**
     * A function to add the orders to the issue order list
     *
     * @param p_Order The order to be added
     */
    private void addOrder(Order p_Order) {
        d_Orders.add(p_Order);
    }

    /**
     * A function to get the reinforcement armies for each player
     *
     * @return armies assigned to player of type int
     */
    public int getReinforcementArmies() {
        return d_ReinforcementArmies;
    }

    /**
     * A function to set the reinforcement armies for each player
     *
     * @param p_AssignedArmies armies assigned to player of type int
     */
    public void setReinforcementArmies(int p_AssignedArmies) {
        this.d_ReinforcementArmies = p_AssignedArmies;
    }

    /**
     * A function to get the issue order from player and add to the order list
     *
     * @param p_Commands the type of order issued
     */
    public void issueOrder(String p_Commands) {
        boolean l_IssueCommand = true;
        String[] l_CommandArr = p_Commands.split(" ");
        int l_ReinforcementArmies = Integer.parseInt(l_CommandArr[2]);
        if (!checkIfCountryExists(l_CommandArr[1], this)) {
            System.out.println("The country does not belong to you");
            l_IssueCommand = false;
        }
        if (!deployReinforcementArmiesFromPlayer(l_ReinforcementArmies)) {
            System.out.println("You do have enough Reinforcement Armies to deploy.");
            l_IssueCommand = false;
        }

        if (l_IssueCommand) {
            Order l_Order = OrderCreater.createOrder(l_CommandArr, this);
            OrderList.add(l_Order);
            addOrder(l_Order);
            System.out.println("Your Order has been added to the list: deploy " + l_Order.getOrderInfo().getDestination() + " with " + l_Order.getOrderInfo().getNumberOfArmy() + " armies");
            System.out.println("=========================================================================================");
        }
    }

    /**
     * A function to check if the country exists in the list of player assigned countries
     *
     * @param p_Country The country to be checked if present
     * @param p_Player The Player for whom the function is checked for
     * @return true if country exists in the assigned country list else false
     */
    public boolean checkIfCountryExists(String p_Country, Player p_Player) {
        List<Country> l_ListOfCountries = p_Player.getCapturedCountries();
        for (Country l_Country : l_ListOfCountries) {
            if (l_Country.getName().equals(p_Country)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A function to check if the army to deployed is valid
     *
     * @param p_ArmyCount The armies to be deployed to the country
     * @return true if the armies are valid and deducted from the assigned army pool else false
     */
    public boolean deployReinforcementArmiesFromPlayer(int p_ArmyCount) {
        if (p_ArmyCount > d_ReinforcementArmies || p_ArmyCount < 0) {
            return false;
        }
        d_ReinforcementArmies -= p_ArmyCount;
        return true;
    }

    /**
     *  A function to create a list of countries assigned to player in a formatted string
     *
     * @param p_Capture The list of countries of the player
     * @return the formatted string
     */
    public String createACaptureList(List<Country>  p_Capture) {
        String l_Result = "";
        for (Country l_Capture : p_Capture ){
            l_Result += l_Capture.getName() + "-";
        }
        return l_Result.length() > 0 ? l_Result.substring(0, l_Result.length() - 1): "";
    }
}
