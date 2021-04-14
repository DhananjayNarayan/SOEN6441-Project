package model.strategy.player;

import model.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheaterStrategy extends PlayerStrategy implements  Serializable {

    private static final GameMap d_GameMap = GameMap.getInstance();

    List<Country> l_cheatCountryList;

    CheaterStrategy(Player p_player){
        super(p_player);
        l_cheatCountryList = new ArrayList<>();

    }

    public String createCommand() {






        return null;
    }
}
