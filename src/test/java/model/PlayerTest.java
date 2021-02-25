package model;
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

    int d_ReinforcementArmies;
    int d_ArmyCountValid, d_ArmyCountInvalid;
    Player p = new Player();
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
        d_ReinforcementArmies = 10;
        d_ArmyCountValid = 5;
        d_ArmyCountInvalid = 13;
        p.setReinforcementArmies(d_ReinforcementArmies);

        c1.setName("India");
        c2.setName("China");
        c3.setName("Japan");
        d_CountryValid = "India";
        d_CountryInvalid = "Canada";
        d_CapturedCountries.add(c1);            //add to list
        d_CapturedCountries.add(c2);
        d_CapturedCountries.add(c3);
        p.setCapturedCountries(d_CapturedCountries);
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
     * This is the test method to check if Country exists
     *
     */
    @Test
    public void testValidCheckIfCountryExists() {
        assertTrue(p.checkIfCountryExists(d_CountryValid,p));
    }
    /**
     * This is the test method to check if Country does not exist
     *
     */
    @Test
    public void testInvalidCheckIfCountryExists() {
        assertFalse(p.checkIfCountryExists(d_CountryInvalid,p));
    }
    /**
     * This is the test method to check the valid deployment of armies
     *
     */
    @Test
    public void testValidDeployReinforcementArmiesFromPlayer() {
        assertTrue(p.deployReinforcementArmiesFromPlayer(d_ArmyCountValid));
    }
    /**
     * This is the test method to check the invalid deployment of armies
     *
     */
    @Test
    public void testInvalidDeployReinforcementArmiesFromPlayer() {
        assertFalse(p.deployReinforcementArmiesFromPlayer(d_ArmyCountInvalid));
    }
}

