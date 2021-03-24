package model;

import controller.IssueOrder;
import model.order.Order;
import model.order.OrderCreater;
import utils.LogEntryBuffer;

import java.util.*;
import java.util.stream.Collectors;

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
    private final Deque<Order> d_Orders = new ArrayDeque<>();
    private int d_ReinforcementArmies;
    private final List<Card> d_PlayerCards = new ArrayList<>();
    private final List<Player> d_NeutralPlayers = new ArrayList<>();
    LogEntryBuffer d_leb = new LogEntryBuffer();

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
     * A function to add the orders to the issue order list
     *
     * @param p_Order The order to be added
     */
    public void addOrder(Order p_Order) {
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
     * A function to get list of all cards for the player
     *
     * @return list of all cards
     */
    public List<Card> getPlayerCards() {
        return d_PlayerCards;
    }

    /**
     * Add the card to the player on conquering the territory
     *
     * @param p_Card card to be added to player
     */
    public void addPlayerCard(Card p_Card) {
        d_PlayerCards.add(p_Card);
    }

    /**
     * Get the list of all players you cannot attack
     *
     * @return list of players
     */
    public List<Player> getNeutralPlayers() {
        return d_NeutralPlayers;
    }

    /**
     * Add the neutral player to the list
     *
     * @param p_NeutralPlayer The player you cannot attack
     */
    public void addNeutralPlayers(Player p_NeutralPlayer) {
        if (!d_NeutralPlayers.contains(p_NeutralPlayer)) {
            d_NeutralPlayers.add(p_NeutralPlayer);
        }
    }

    /**
     * A function to get the issue order from player and add to the order list
     */
    public void issueOrder() {
        Order l_Order = OrderCreater.CreateOrder(IssueOrder.d_Commands.split(" "), this);
        addOrder(l_Order);
    }


    /**
     * A function to return the next order for execution
     *
     * @return order for executing for each player
     */
    public Order nextOrder() {
        return d_Orders.poll();
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
     * A function to create a list of countries assigned to player in a formatted string
     *
     * @param p_Capture The list of countries of the player
     * @return the formatted string
     */
    public String createACaptureList(List<Country> p_Capture) {
        String l_Result = "";
        for (Country l_Capture : p_Capture) {
            l_Result += l_Capture.getName() + "-";
        }
        return l_Result.length() > 0 ? l_Result.substring(0, l_Result.length() - 1) : "";
    }


    public void calculateReinforcementArmies(GameMap p_gameMap) {
        if (getCapturedCountries().size() > 0) {
            int reinforcements = (int) Math.floor(getCapturedCountries().size() / 3f);
            reinforcements += getBonusIfKingOfContinents(p_gameMap);
            setReinforcementArmies(reinforcements > 2 ? reinforcements : 3);
            System.out.println("The Player:" + getName() + " is assigned with " + getReinforcementArmies() + " armies.");
            d_leb.logInfo("The Player:" + getName() + " is assigned with " + getReinforcementArmies() + " armies.");
        } else {
            setReinforcementArmies(3);
            System.out.println("The Player:" + getName() + " is assigned with " + getReinforcementArmies() + " armies.");
            d_leb.logInfo("The Player:" + getName() + " is assigned with " + getReinforcementArmies() + " armies.");
        }
    }

    private int getBonusIfKingOfContinents(GameMap p_gameMap) {
        int reinforcements = 0;
        Map<String, List<Country>> l_CountryMap = getCapturedCountries()
                .stream()
                .collect(Collectors.groupingBy(Country::getContinent));
        for (String continent : l_CountryMap.keySet()) {
            if (p_gameMap.getContinent(continent).getCountries().size() == l_CountryMap.get(continent).size()) {
                reinforcements += p_gameMap.getContinent(continent).getAwardArmies();
            }
        }
        return reinforcements;
    }

    /**
     * A function to check if the country exists in the list of player captured countries
     *
     * @param p_Country The country to be checked if present
     * @return true if country exists in the assigned country list else false
     */
    public boolean isCaptured(Country p_Country) {
        return d_CapturedCountries.contains(p_Country);
    }
}
