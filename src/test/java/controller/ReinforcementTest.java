package controller;

import model.GameMap;
import model.GamePhase;
import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.InvalidExecutionException;
import utils.ValidationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReinforcementTest extends Player {
    GamePhase d_NextGamePhase = GamePhase.IssueOrder;
    GameMap d_GameMap;
    Reinforcement l_Reinforcement;

    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "4");
        d_GameMap.addContinent("Europe", "3");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addCountry("France", "Europe");
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        d_GameMap.assignCountries();
        l_Reinforcement = new Reinforcement();
        l_Reinforcement.d_GamePhase = GamePhase.Reinforcement;
    }

    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }


    @Test

    public void startShouldReturnNextPhase() throws ValidationException, InvalidExecutionException {
        GamePhase l_NextGamePhase = l_Reinforcement.start(GamePhase.Reinforcement);
        assertEquals(d_NextGamePhase, l_NextGamePhase);
    }

    @Test
    public void checkReinforcementsSetOrNot() throws ValidationException, InvalidExecutionException {
        l_Reinforcement.d_CurrentPlayer = d_GameMap.getPlayer("Player2");
        l_Reinforcement.setReinforcementTroops();
        assertTrue(l_Reinforcement.d_CurrentPlayer.getReinforcementArmies() >= 3);
    }
}