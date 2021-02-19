package model;

import java.util.List;

/**
 * Concrete Class to set and get all the properties of the Map.
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class Map {
    private List<Continent> d_continents;
    private List<Country> d_countries;
    private List<Player> d_players;

    /**
     * Get the list of all the continents
     *
     * @return d_continents List of the continents
     */
    public List<Continent> getContinents() {
        return d_continents;
    }

    /**
     * Set the list of Continents
     *
     * @param p_continents List of continents
     */
    public void setContinents(List<Continent> p_continents) {
        this.d_continents = p_continents;
    }

    /**
     * Get the list of countries
     *
     * @return d_countries List of the countries
     */
    public List<Country> getCountries() {
        return d_countries;
    }

    /**
     * Set the list of countries
     *
     * @param p_countries List of countries
     */
    public void setCountries(List<Country> p_countries) {
        this.d_countries = p_countries;
    }

    /**
     * Get the list of players
     *
     * @return d_players List of players
     */
    public List<Player> getPlayers() {
        return d_players;
    }

    /**
     * Set the list of players
     *
     * @param p_players List of players
     */
    public void setPlayers(List<Player> p_players) {
        this.d_players = p_players;
    }
}
