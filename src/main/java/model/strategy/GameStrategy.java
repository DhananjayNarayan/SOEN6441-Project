package model.strategy;

import model.Country;
import model.Player;

public interface GameStrategy {

    boolean attack(Player p_player, Country p_from, Country p_to, int p_armies);

    default void makeMeKing(Player p_player, Country p_country) {
        p_country.getPlayer().getCapturedCountries().remove(p_country);
        p_country.setPlayer(p_player);
        p_player.getCapturedCountries().add(p_country);
    }
}
