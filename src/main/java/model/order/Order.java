package model.order;

import model.Country;
import model.GameMap;
import model.Player;

import java.util.Deque;
import java.util.List;
import java.util.Objects;

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
    private static Order d_Order;
    private String d_Type;
    private OrderInfo d_OrderInfo;
    private List<Order> d_OrderList;

    public List<Order> getOrderList() {
        return d_OrderList;
    }

    public void setOrderList(List<Order> d_OrderList) {
        this.d_OrderList = d_OrderList;
    }
    public void AddToOrderList(Order p_Order){
        d_OrderList.add(p_Order);
    }



    public static Order getInstance() {
        if (Objects.isNull(d_Order)) {
            d_Order = new Order();
        }
        return d_Order;
    }

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
