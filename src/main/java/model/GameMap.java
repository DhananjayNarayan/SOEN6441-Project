package model;

import java.util.HashMap;

/**
 * Concrete Class to set and get all the properties of the GameMap.
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class GameMap {
    private HashMap<String, Continent> d_Continents;
    private HashMap<String, Country> d_Countries;
    private HashMap<String, Player> d_Players;

    /**
     * Get the list of all the continents
     *
     * @return d_Continents List of the continents
     */
    public HashMap<String, Continent> getContinents() {
        return d_Continents;
    }

    public Continent getContinent(String id) {
        return d_Continents.get(id);
    }

    /**
     * Set the list of Continents
     *
     * @param p_Continents List of continents
     */
    public void setContinents(HashMap<String, Continent> p_Continents) {
        this.d_Continents = p_Continents;
    }

    /**
     * Get the list of countries
     *
     * @return d_Countries List of the countries
     */
    public HashMap<String, Country> getCountries() {
        return d_Countries;
    }


    public Country getCountry(String id) {
        return d_Countries.get(id);
    }

    /**
     * Set the list of countries
     *
     * @param p_Countries List of countries
     */
    public void setCountries(HashMap<String, Country> p_Countries) {
        this.d_Countries = p_Countries;
    }

    /**
     * Get the list of players
     *
     * @return d_Players List of players
     */
    public HashMap<String, Player> getPlayers() {
        return d_Players;
    }

    public Player getPlayer(String id) {
        return d_Players.get(id);
    }

    /**
     * Set the list of players
     *
     * @param p_Players List of players
     */
    public void setPlayers(HashMap<String, Player> p_Players) {
        this.d_Players = p_Players;
    }
}
