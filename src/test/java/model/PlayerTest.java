package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the functionalities for Player class
 *
 * @author Prathika Suvarna
 */
public class PlayerTest extends Player {

    int d_ReinforcementArmies;
    int d_ArmyCountValid, d_ArmyCountInvalid;
    Player d_P = new Player();
    String d_CountryValid, d_CountryInvalid;
    List<Country> d_CapturedCountries = new ArrayList<>();
    Country d_C1 = new Country();
    Country d_C2 = new Country();
    Country d_C3 = new Country();
    GameMap d_GameMap = GameMap.getInstance();

    /**
     * This method initializes each value before execution of every test case
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception {
        d_ReinforcementArmies = 10;
        d_ArmyCountValid = 5;
        d_ArmyCountInvalid = 13;
        d_P.setReinforcementArmies(d_ReinforcementArmies);

        d_C1.setName("India");
        d_C2.setName("China");
        d_C3.setName("Japan");
        d_CountryValid = "India";
        d_CountryInvalid = "Canada";
        d_CapturedCountries.add(d_C1);            //add to list
        d_CapturedCountries.add(d_C2);
        d_CapturedCountries.add(d_C3);
        d_P.setCapturedCountries(d_CapturedCountries);
    }

    /**
     * This method will be executed at the end of the test
     *
     * @throws Exception when execution fails
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.getContinents().clear();
        d_GameMap.getCountries().clear();
        d_GameMap.getPlayers().clear();
    }

    @Test
    public void testValidDeployReinforcementArmiesFromPlayer() {
        assertTrue(d_P.deployReinforcementArmiesFromPlayer(d_ArmyCountValid));
    }

    /**
     * This is the test method to check the invalid deployment of armies
     */
    @Test
    public void testInvalidDeployReinforcementArmiesFromPlayer() {
        assertFalse(d_P.deployReinforcementArmiesFromPlayer(d_ArmyCountInvalid));
    }
}

