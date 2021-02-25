package model.order;

import java.util.ArrayList;
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
    private List<Order> d_OrderList = new ArrayList<Order>();

    /**
     * A function to get the order list
     *
     * @return the list of type Order class
     */
    public List<Order> getOrderList() {

        return d_OrderList;
    }

    /**
     * A function to set the order list
     *
     * @param p_OrderList Order List of type Order class
     */
    public void setOrderList(List<Order> p_OrderList) {

        this.d_OrderList = p_OrderList;
    }

    /**
     * A function to add the order to the order list
     *
     * @param p_Order The order to be added to the list
     */
    public void AddToOrderList(Order p_Order){

        d_OrderList.add(p_Order);
    }

    /**
     * A function to get the instance of the class Order
     *
     * @return the instance of class Order
     */
    public static Order getInstance() {
        if (Objects.isNull(d_Order)) {
            d_Order = new Order();
        }
        return d_Order;
    }
    /**
     * A function to get order information
     *
     * @return the order information in an object
     */
    public OrderInfo getOrderInfo() {

        return d_OrderInfo;
    }

    /**
     * A function to the set Order information based on the order
     *
     * @param p_OrderInfo Order Information contained in an object of type OrderInfo
     */
    public void setOrderInfo(OrderInfo p_OrderInfo) {

        this.d_OrderInfo = p_OrderInfo;
    }

    /**
     * A function to return the type of order
     *
     * @return String which indicates the type of order
     */
    public String getType() {

        return d_Type;
    }

    /**
     * A function to set the type of order
     *
     * @param p_Type String which indicates the type of order
     */
    public void setType(String p_Type) {

        this.d_Type = p_Type;
    }

    /**
     * A function to be overridden  by the Child class
     *
     * @return false as there is not order to be executed
     */
    public boolean execute() {
        System.out.println("Void order is not able to execute");
        return false;
    }

}
