package model;

import java.util.HashSet;
import java.util.Set;

/**
 * Concrete Class to set and get all the properties of Continent.
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class Continent {
    private String d_Id;
    private String d_Name;
    private int d_AwardArmies;
    private boolean d_Credited;
    private Set<Country> d_Countries;

    /**
     * Get the continent ID also known as continent value
     *
     * @return d_Id The continent ID also known as continent value
     */
    public String getId() {
        return d_Id;
    }

    /**
     * Set the continent ID also known as continent value
     *
     * @param p_Id Continent ID also known as continent value
     */
    public void setId(String p_Id) {
        this.d_Id = p_Id;
    }

    /**
     * Get the continent name
     *
     * @return d_Name Continent name which is of type string
     */
    public String getName() {
        return d_Name;
    }

    /**
     * Set the continent name
     *
     * @param p_name Continent name
     */
    public void setName(String p_name) {
        this.d_Name = p_name;
    }

    /**
     * Get the Awarded armies
     *
     * @return d_AwardArmies  The Awarded armies assigned to the continent
     */
    public int getAwardArmies() {
        return d_AwardArmies;
    }

    /**
     * Set the Awarded armies for the continent
     *
     * @param p_AwardArmies Awarded armies
     */
    public void setAwardArmies(int p_AwardArmies) {
        this.d_AwardArmies = p_AwardArmies;
    }

    /**
     * Check if Armies are credited or not
     *
     * @return true if armies are credited else false if not credited
     */
    public boolean isCredited() {
        return d_Credited;
    }

    /**
     * Set the number of armies credited
     *
     * @param p_Credited Credited armies
     */
    public void setCredited(boolean p_Credited) {
        this.d_Credited = p_Credited;
    }

    /**
     * Returns the list of countries belonging to the continent
     *
     * @return list of countries
     */
    public Set<Country> getCountries() {
        if (d_Countries == null) {
            d_Countries = new HashSet<>();
        }
        return d_Countries;
    }
}
