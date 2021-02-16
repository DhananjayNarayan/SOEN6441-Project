package model;

public abstract class AbstractCountry {
    private int d_id;
    private String d_name;
    private int d_continent;
    private AbstractPlayer d_player;
    private int d_armies;

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

    public int getContinent() {
        return d_continent;
    }

    public void setContinent(int p_continent) {
        this.d_continent = p_continent;
    }

    public AbstractPlayer getPlayer() {
        return d_player;
    }

    public void setPlayer(AbstractPlayer p_player) {
        this.d_player = p_player;
    }

    public int getArmies() {
        return d_armies;
    }

    public void setArmies(int p_armies) {
        this.d_armies = p_armies;
    }
}
