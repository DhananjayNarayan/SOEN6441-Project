package model;

import model.strategy.player.PlayerStrategy;
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

    int d_Id;
    String d_Name;

    int d_ReinforcementArmies;
    int d_ArmyCountValid, d_ArmyCountInvalid;
    Player d_Player;
    String d_CountryValid, d_CountryInvalid;
    List<Country> d_CapturedCountries = new ArrayList<>();
    Country d_Country1 = new Country();
    Country d_Country2 = new Country();
    Country d_Country3 = new Country();
    GameMap d_GameMap = GameMap.getInstance();

    public PlayerTest() {
        super(PlayerStrategy.getStrategy("human"));
        d_Player = this;
    }

    /**
     * This method initializes each value before execution of every test case
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception {
        d_Id = 4;
        d_Player.setId(d_Id);
        d_Name = "Shiro";
        d_Player.setName(d_Name);

        d_ReinforcementArmies = 10;
        d_ArmyCountValid = 5;
        d_ArmyCountInvalid = 13;
        d_Player.setReinforcementArmies(d_ReinforcementArmies);

        d_Country1.setName("India");
        d_Country2.setName("China");
        d_Country3.setName("Japan");
        d_CountryValid = "India";
        d_CountryInvalid = "Canada";
        d_CapturedCountries.add(d_Country1);            //add to list
        d_CapturedCountries.add(d_Country2);
        d_CapturedCountries.add(d_Country3);
        d_Player.setCapturedCountries(d_CapturedCountries);
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

    /**
     * This is the test method to check the Player ID
     */

    @Test
    public void testCountryId() {
        int l_Id = d_Player.getId();
        assertEquals(d_Id, l_Id);
    }

    /**
     * This is the test method to check the Player Name
     */

    @Test
    public void testCountryName() {
        String l_Name = d_Player.getName();
        assertEquals(d_Name, l_Name);
    }

    /**
     * Checks the reinforcement armies
     */
    @Test
    public void testValidDeployReinforcementArmiesFromPlayer() {
        assertTrue(d_Player.deployReinforcementArmiesFromPlayer(d_ArmyCountValid));
    }

    /**
     * This is the test method to check the invalid deployment of armies
     */
    @Test
    public void testInvalidDeployReinforcementArmiesFromPlayer() {
        assertFalse(d_Player.deployReinforcementArmiesFromPlayer(d_ArmyCountInvalid));
    }
}

