package model;

import java.util.List;

public abstract class AbstractPlayer {
    private int d_id;
    private String d_name;
    private int d_order_count;
    private List<AbstractCountry> d_captured_countries;
    private List<AbstractOrder> d_orders;

    public int getId() {
        return d_id;
    }

    public void setId(int p_id) {
        this.d_id = p_id;
    }

    public String getName() {
        return d_name;
    }

    public void setName(String p_name) {
        this.d_name = p_name;
    }

    public int getOrderCount() {
        return d_order_count;
    }

    public void setOrderCount(int p_order_count) {
        this.d_order_count = p_order_count;
    }

    public List<AbstractCountry> getCapturedCountries() {
        return d_captured_countries;
    }

    public void setCapturedCountries(List<AbstractCountry> p_captured_countries) {
        this.d_captured_countries = p_captured_countries;
    }

    public List<AbstractOrder> getOrders() {
        return d_orders;
    }

    public void setOrders(List<AbstractOrder> p_orders) {
        this.d_orders = p_orders;
    }
}
