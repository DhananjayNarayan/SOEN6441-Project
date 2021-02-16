package model;

public abstract class AbstractContinent {
    private int d_id;
    private String d_name;
    private int d_award_armies;
    private boolean d_credited;

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

    public int getAwardArmies() {
        return d_award_armies;
    }

    public void setAwardArmies(int p_award_armies) {
        this.d_award_armies = p_award_armies;
    }

    public boolean isCredited() {
        return d_credited;
    }

    public void setCredited(boolean p_credited) {
        this.d_credited = p_credited;
    }
}
