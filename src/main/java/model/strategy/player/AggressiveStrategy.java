package model.strategy.player;

import model.*;
import model.order.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AggressiveStrategy extends PlayerStrategy implements  Serializable  {

    private static final GameMap d_GameMap = GameMap.getInstance();

    AggressiveStrategy(Player p_Player) {
        super(p_Player);
    }

    public Country determineStrongestCountry(Player p_player) {
        List<Country> countryList = p_player.getCapturedCountries();
        Country l_strongestCountry = countryList.get(0);
        for (Country country : countryList) {
            if (country.getArmies() > l_strongestCountry.getArmies())
                l_strongestCountry = country;
        }

        return l_strongestCountry;
    }

    public String createCommand(){







             return null;
    }

}
