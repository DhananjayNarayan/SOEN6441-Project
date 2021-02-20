package model;

/**
 * Concrete Class to manage the orders of the players
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class Order {
    private int d_Id;
    private OrderType d_Type;
    private Country d_From;
    private Country d_To;
    private int d_Reinforcements;

    /**
     * A function to get the ID of the player
     *
     * @return the ID of the player
     */
    public int getId() {
        return d_Id;
    }

    /**
     * A function to set the ID of the player
     *
     * @param p_Id ID of the player to be set
     */
    public void setId(int p_Id) {
        this.d_Id = p_Id;
    }

    /**
     * A function to fetch the type of the order
     *
     * @return The Order Type
     */
    public OrderType getType() {
        return d_Type;
    }

    /**
     * A function to set the order object d_type with p_type
     *
     * @param p_Type Object of the class OrderType
     */
    public void setType(OrderType p_Type) {
        this.d_Type = p_Type;
    }

    /**
     * A function to get where the army is coming from
     *
     * @return the country where the army is coming from
     */
    public Country getFrom() {
        return d_From;
    }

    /**
     * A function to set the the country where the army is coming from
     *
     * @param p_From Object of Class Country
     */
    public void setFrom(Country p_From) {
        this.d_From = p_From;
    }

    /**
     * A function to get the country where the army is being sent to
     *
     * @return the country where army is being sent
     */
    public Country getTo() {
        return d_To;
    }

    /**
     * A function to set the country where the army is being sent to
     *
     * @param p_To Object of Class Country
     */
    public void setTo(Country p_To) {
        this.d_To = p_To;
    }

    /**
     * A function to get the number of reinforcements
     *
     * @return number of reinforcements
     */
    public int getReinforcements() {
        return d_Reinforcements;
    }

    /**
     * A function to set the number of reinforcements
     *
     * @param p_Reinforcements the number of reinforcements
     */
    public void setReinforcements(int p_Reinforcements) {
        this.d_Reinforcements = p_Reinforcements;
    }
}
