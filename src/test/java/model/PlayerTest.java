package model;

import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

/**
 * This class tests the functionalities for Player class
 *
 * @author Prathika Suvarna
 */
public class PlayerTest extends Player {

    int d_id;
    String d_Name;
    int d_ReinforcementArmies;
    int d_ArmyCountValid, d_ArmyCountInvalid;
    Player d_player = new Player();
    String d_CountryValid, d_CountryInvalid;
    List<Country> d_CapturedCountries = new ArrayList<>();
    Country c1 = new Country();
    Country c2 = new Country();
    Country c3 = new Country();
    GameMap d_GameMap = GameMap.getInstance();

    /**
     * This method initializes each value before execution of every test case
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception {

        d_player.setId(1);
        d_Name = "Maria";

        d_ReinforcementArmies = 10;
        d_ArmyCountValid = 5;
        d_ArmyCountInvalid = 13;
        d_player.setReinforcementArmies(d_ReinforcementArmies);

        c1.setName("India");
        c2.setName("China");
        c3.setName("Japan");
        d_CountryValid = "India";
        d_CountryInvalid = "Canada";
        d_CapturedCountries.add(c1);            //add to list
        d_CapturedCountries.add(c2);
        d_CapturedCountries.add(c3);
        d_player.setCapturedCountries(d_CapturedCountries);

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
    public void testPlayerId(){
        int id = d_player.getId();
        assertEquals(d_id,id);
    }

    /**
     * This is the test method to check if Country exists
     *
     */
    @Test
    public void testValidCheckIfCountryExists() {
        assertTrue(d_player.checkIfCountryExists(d_CountryValid,d_player));
    }
    /**
     * This is the test method to check if Country does not exist
     *
     */
    @Test
    public void testInvalidCheckIfCountryExists() {
        assertFalse(d_player.checkIfCountryExists(d_CountryInvalid,d_player));
    }
    /**
     * This is the test method to check the valid deployment of armies
     *
     */
    @Test
    public void testValidDeployReinforcementArmiesFromPlayer() {
        assertTrue(d_player.deployReinforcementArmiesFromPlayer(d_ArmyCountValid));
    }
    /**
     * This is the test method to check the invalid deployment of armies
     *
     */
    @Test
    public void testInvalidDeployReinforcementArmiesFromPlayer() {
        assertFalse(d_player.deployReinforcementArmiesFromPlayer(d_ArmyCountInvalid));
    }
}

