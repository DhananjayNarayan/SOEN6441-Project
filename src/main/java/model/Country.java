package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Concrete Class to set and get all the properties of country.
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 */
public class Country {
    private String d_Id;
    private String d_Name;
    private String d_Continent;
    private Player d_Player;
    private int d_Armies;
    private Set<Country> d_Neighbors;
    private Set<String> d_NeighborsName;

    /**
     * Get the country ID
     *
     * @return d_Id Country ID of type int
     */
    public String getId() {

        return d_Id;
    }

    /**
     * Set the country ID
     *
     * @param p_Id Country ID
     */
    public void setId(String p_Id) {

        this.d_Id = p_Id;
    }

    /**
     * Get the country name
     *
     * @return d_Name The country name
     */
    public String getName() {

        return d_Name;
    }

    /**
     * Set the country name
     *
     * @param p_Name Country name
     */
    public void setName(String p_Name) {

        this.d_Name = p_Name;
    }

    /**
     * Get the continent name, the country belongs to
     *
     * @return d_Continent Continent name
     */
    public String getContinent() {

        return d_Continent;
    }

    /**
     * Set the continent name, the country belongs to
     *
     * @param p_Continent Continent name
     */
    public void setContinent(String p_Continent) {

        this.d_Continent = p_Continent;
    }

    /**
     * Get the player instance for the game play
     *
     * @return d_Player Player instance
     */
    public Player getPlayer() {

        return d_Player;
    }

    /**
     * Set the player instance for the game play
     *
     * @param p_Player Player instance
     */
    public void setPlayer(Player p_Player) {

        this.d_Player = p_Player;
    }

    /**
     * Get the number of armies for the country
     *
     * @return d_Armies Number of armies for the country
     */
    public int getArmies() {

        return d_Armies;
    }

    /**
     * deploy the armies for the player
     *
     * @param p_armies number of armies to be deployed
     */
    public void deployArmies(int p_armies) {

        d_Armies += p_armies;
    }

    /**
     * Set the armies for the country
     *
     * @param p_Armies Number of armies for the country
     */
    public void setArmies(int p_Armies) {

        this.d_Armies = p_Armies;
    }

    /**
     * A function to get the list of neighbors
     * @return set of neighbors
     */
    public Set<Country> getNeighbors() {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        return d_Neighbors;
    }

    /**
     * A function to set the list of neighbours
     * @param p_Neighbor An object of the Country class
     */
    public void setNeighbors(Country p_Neighbor) {
        if (d_Neighbors == null) {
            d_Neighbors = new HashSet<>();
        }
        d_Neighbors.add(p_Neighbor);
    }

    /**
     * A function to store the names of the neighbours of a country
     * @param p_NeighborCountryName the name of the neighbour country being added to the set
     */
    public void setNeighborsName(String p_NeighborCountryName) {
        if (d_NeighborsName == null) {
            d_NeighborsName = new HashSet<>();
        }
        d_NeighborsName.add(p_NeighborCountryName);
    }

    /**
     * A function to fetch the set of neighbors of a country
     * @return The set of neighbors of a country
     */
    public Set<String> getNeighborsName() {
        if (d_NeighborsName == null) {
            d_NeighborsName = new HashSet<>();
        }
        return d_NeighborsName;
    }

    /**
     * A function to remove a neighbour from the list of neighbours of a country
     * @param p_NeighborCountryName the name of the neighbor to be removed from the set
     */
    public void removeNeighborsName(String p_NeighborCountryName) {
        if (d_NeighborsName == null) {
            d_NeighborsName = new HashSet<>();
        }
        d_NeighborsName.remove(p_NeighborCountryName);
    }

    /**
     * A function to store the list of neighbors for a country
     * @param p_Neighbors the set of neighbors
     * @return the neighbors as String
     */
    public String createANeighborList(Set<Country> p_Neighbors){
        String result = "";
        for (Country l_Neighbor : p_Neighbors ){
            result += l_Neighbor.getName() + "-";
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";
    }
}
