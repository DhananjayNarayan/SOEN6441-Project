package model.strategy.game;

import model.Country;
import model.Player;

/**
 * Interface class for Game Strategy
 *
 * @author Madhuvanthi Hemanathan
 */
public interface GameStrategy {

    /**
     * Method holding the default attack logic
     *
     * @param p_Player The player who initiated attack
     * @param p_From   The country from which the attack is initiated
     * @param p_To     The country on which the attack is going to happen
     * @param p_Armies The number of armies to be moved
     * @return true on successful execution else false
     */
    boolean attack(Player p_Player, Country p_From, Country p_To, int p_Armies);

    /**
     * Method to swap the ownership of territories once conquered
     *
     * @param p_Player The player to whom the ownership should go
     * @param p_Country The country which is conquered
     */
    default void makeMeKing(Player p_Player, Country p_Country) {
        p_Country.getPlayer().getCapturedCountries().remove(p_Country);
        p_Country.setPlayer(p_Player);
        p_Player.getCapturedCountries().add(p_Country);
    }
}
