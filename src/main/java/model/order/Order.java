package model.order;

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
    private OrderInfo d_OrderInfo;
    private String d_Type;


    public Order() {
        d_OrderInfo = null;
    }

    public OrderInfo getOrderInfo() {
        return d_OrderInfo;
    }

    public void setOrderInfo(OrderInfo d_OrderInfo) {
        this.d_OrderInfo = d_OrderInfo;
    }

    /**
     * A function to fetch the type of the order
     *
     * @return The Order Type
     */
    public String getType() {
        return d_Type;
    }

    /**
     * A function to set the order object d_type with p_type
     *
     * @param p_Type Object of the class OrderType
     */
    public void setType(String p_Type) {
        this.d_Type = p_Type;
    }




    public boolean execute(){

        return true;
    }
}
