package model;

import java.util.List;

public abstract class AbstractMap {
    private List<AbstractContinent> d_continents;
    private List<AbstractCountry> d_countries;
    private List<AbstractPlayer> d_players;

    public List<AbstractContinent> getContinents() {
        return d_continents;
    }

    public void setContinents(List<AbstractContinent> p_continents) {
        this.d_continents = p_continents;
    }

    public List<AbstractCountry> getCountries() {
        return d_countries;
    }

    public void setCountries(List<AbstractCountry> p_countries) {
        this.d_countries = p_countries;
    }

    public List<AbstractPlayer> getPlayers() {
        return d_players;
    }

    public void setPlayers(List<AbstractPlayer> p_players) {
        this.d_players = p_players;
    }
}
