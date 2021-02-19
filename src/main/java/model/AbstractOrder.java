package model;

/**
 * A class to Manage the orders of the players
 *   @author Neona Pinto
 *   @author Dhananjay Narayan
 *   @author Surya Manian
 *   @author Madhuvanthi Hemanathan
 *   @author Prathika Suvarna
 *   @version 1.0.0
 */
public abstract class AbstractOrder {
    private int d_id;
    private OrderType d_type;
    private AbstractCountry d_from;
    private AbstractCountry d_to;
    private int d_reinforcements;

    /**
     * A function to get the ID of the player
     * @return  the ID of the player
     */
    public int getId() {
        return d_id;
    }

    /**
     * A function to set the ID of the player
     * @param p_id ID of the player to be set
     */
    public void setId(int p_id) {
        this.d_id = p_id;
    }

    /**
     * A function to fetch the type of the order
     * @return The Order Type
     */
    public OrderType getType() {
        return d_type;
    }

    /**
     * A function to set the order object d_type with p_type
     * @param p_type Object of the class OrderType
     */
    public void setD_type(OrderType p_type) {
        this.d_type = p_type;
    }

    /**
     * A function to get where the army is coming from
     * @return the country where the army is coming from
     */
    public AbstractCountry getFrom() {
        return d_from;
    }

    /**
     * A function to set the the country where the army is coming from
     * @param p_from Object of Class AbstractCountry
     */
    public void setFrom(AbstractCountry p_from) {
        this.d_from = p_from;
    }

    /**
     * A function to get the country where the army is being sent to
     * @return the country where army is being sent
     */
    public AbstractCountry getTo() {
        return d_to;
    }

    /**
     * A function to set the country where the army is being sent to
     * @param p_to Object of Class AbstractCountry
     */
    public void setTo(AbstractCountry p_to) {
        this.d_to = p_to;
    }

    /**
     * A function to get the number of reinforcements
     * @return number of reinforcements
     */
    public int getReinforcements() {
        return d_reinforcements;
    }

    /**
     * A function to set the number of reinforcements
     * @param p_reinforcements the number of reinforcements
     */
    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements = p_reinforcements;
    }
}
