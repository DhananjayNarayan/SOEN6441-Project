package model.order;

import controller.IssueOrder;
import model.Country;
import model.GameMap;
import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Deploy Order
 */
public class DeployOrderTest {
    GameMap d_GameMap;
    List<Country> l_CountriesPlayer1;
    List<Country> l_CountriesPlayer2;
    Player l_Player;

    /**
     * Setup for the test cases
     * @throws Exception exception during setup
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "4");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        d_GameMap.assignCountries();
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            l_Player.calculateReinforcementArmies(d_GameMap);
        }
        l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        l_Player = d_GameMap.getPlayer("Player1");
    }

    /**
     * Teardown after the test cases
     *
     * @throws Exception exception during teardown
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    /**
     * test case to execute the command
     */
    @Test
    public void execute() {
        IssueOrder.Commands = "deploy " + l_CountriesPlayer1.get(0).getName() + " " + l_Player.getReinforcementArmies();
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Check if the command is valid
     */
    @Test
    public void checkIfTheCommandIsValid() {
        IssueOrder.Commands = "deploy " + l_CountriesPlayer1.get(0).getName() + " " + l_Player.getReinforcementArmies();
        Order l_Order = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order);
        assertTrue(l_Player.nextOrder().validateCommand());
    }

    /**
     * Check if countries in command are valid
     */
    @Test
    public void checkIfTheCountriesAreValid() {
        l_CountriesPlayer2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
        IssueOrder.Commands = "deploy " + l_CountriesPlayer2.get(0).getName() + " " + l_Player.getReinforcementArmies();
        Order l_Order = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order);
        assertFalse(l_Player.nextOrder().validateCommand());
    }

    /**
     * Check if armies are valid in command
     */
    @Test
    public void checkIfTheArmiesAreValid() {
        IssueOrder.Commands = "deploy " + l_CountriesPlayer1.get(0).getName() + " 10";
        Order l_Order = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
}