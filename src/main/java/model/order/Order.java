package model.order;

import model.Country;
import model.Player;

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
    private String d_Type;
    private OrderInfo d_OrderInfo;

    public OrderInfo getOrderInfo() {
        return d_OrderInfo;
    }

    public void setOrderInfo(OrderInfo d_OrderInfo) {
        this.d_OrderInfo = d_OrderInfo;
    }

    public String getType() {
        return d_Type;
    }

    public void setType(String d_Type) {
        this.d_Type = d_Type;
    }

    public boolean execute() {
        System.out.println("Void order is not able to execute");
        return false;
    }

}
