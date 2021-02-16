package model;

public abstract class AbstractOrder {
    private int d_id;
    private OrderType d_type;
    private AbstractCountry d_from;
    private AbstractCountry d_to;
    private int d_reinforcements;

    public int getId() {
        return d_id;
    }

    public void setId(int p_id) {
        this.d_id = p_id;
    }

    public OrderType getType() {
        return d_type;
    }

    public void setD_type(OrderType p_type) {
        this.d_type = p_type;
    }

    public AbstractCountry getFrom() {
        return d_from;
    }

    public void setFrom(AbstractCountry p_from) {
        this.d_from = p_from;
    }

    public AbstractCountry getTo() {
        return d_to;
    }

    public void setTo(AbstractCountry p_to) {
        this.d_to = p_to;
    }

    public int getReinforcements() {
        return d_reinforcements;
    }

    public void setReinforcements(int p_reinforcements) {
        this.d_reinforcements = p_reinforcements;
    }
}
