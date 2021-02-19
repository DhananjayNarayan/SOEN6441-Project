package model;

/**
 * Class Continent to set and get all the properties of Continent.
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public abstract class AbstractContinent {
    private int d_id;
    private String d_name;
    private int d_award_armies;
    private boolean d_credited;

    /**
     * Get the continent ID also known as continent value
     *
     * @return d_id The continent ID also known as continent value
     */
    public int getId() {
        return d_id;
    }

    /**
     * Set the continent ID also known as continent value
     *
     * @param p_id Continent ID also known as continent value
     */
    public void setId(int p_id) {
        this.d_id = p_id;
    }

    /**
     * Get the continent name
     *
     * @return d_name Continent name which is of type string
     */
    public String getName() {
        return d_name;
    }

    /**
     *Set the continent name
     *
     * @param p_name Continent name
     */
    public void setName(String p_name) {
        this.d_name = p_name;
    }

    /**
     * Get the Awarded armies
     *
     * @return d_award_armies  The Awarded armies assigned to the continent
     */
    public int getAwardArmies() {
        return d_award_armies;
    }

    /**
     * Set the Awarded armies for the continent
     *
     * @param p_award_armies Awarded armies
     */
    public void setAwardArmies(int p_award_armies) {
        this.d_award_armies = p_award_armies;
    }

    /**
     * Check if Armies are credited or not
     *
     * @return true if armies are credited else false if not credited
     */
    public boolean isCredited() {
        return d_credited;
    }

    /**
     * Set the number of armies credited
     *
     * @param p_credited Credited armies
     */
    public void setCredited(boolean p_credited) {
        this.d_credited = p_credited;
    }
}
