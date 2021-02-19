package model;

/**
 * Class Continent to set and get all the properties of country.
 *   @author Neona Pinto
 *   @author Dhananjay Narayan
 *   @author Surya Manian
 *   @author Madhuvanthi Hemanathan
 *   @author Prathika Suvarna
 */
public abstract class AbstractCountry {
    private int d_id;
    private String d_name;
    private int d_continent;
    private AbstractPlayer d_player;
    private int d_armies;

    /**
     * Get the country ID
     *
     * @return d_id Country ID of type int
     */
    public int getId() {
        return d_id;
    }

    /**
     * Set the country ID
     *
     * @param p_id Country ID
     */
    public void setId(int p_id) {
        this.d_id = p_id;
    }

    /**
     * Get the country name
     *
     * @return d_name The country name
     */
    public String getName() {
        return d_name;
    }

    /**
     * Set the country name
     *
     * @param p_name Country name
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Get the count of the continents
     *
     * @return d_continent Number of continents
     */
    public int getContinent() {
        return d_continent;
    }

    /**
     * Set the continent count
     *
     * @param p_continent Number of continents
     */
    public void setContinent(int p_continent) {
        this.d_continent = p_continent;
    }

    /**
     * Get the player instance for the game play
     *
     * @return d_player Player instance
     */
    public AbstractPlayer getPlayer() {
        return d_player;
    }

    /**
     * Set the player instance for the game play
     *
     * @param p_player Player instance
     */
    public void setPlayer(AbstractPlayer p_player) {
        this.d_player = p_player;
    }

    /**
     * Get the number of armies for the country
     *
     * @return d_armies Number of armies for the country
     */
    public int getArmies() {
        return d_armies;
    }

    /**
     * Set the armies for the country
     *
     * @param p_armies Number of armies for the country
     */
    public void setArmies(int p_armies) {
        this.d_armies = p_armies;
    }
}
