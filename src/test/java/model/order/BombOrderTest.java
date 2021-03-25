package model.order;

import controller.IssueOrder;
import model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for Bomb Order
 */
public class BombOrderTest {
    GameMap d_GameMap;
    List<Country> d_Player1Countries;
    List<Country> d_Player2Countries;

    /**
     * Setup for the test case
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();

        d_GameMap.addContinent("Asia","10");
        d_GameMap.addContinent("Africa","20");
        d_GameMap.addCountry("India","Asia");
        d_GameMap.addCountry("Zambia","Africa");
        d_GameMap.addNeighbor("India","Zambia");
        d_GameMap.addNeighbor("Zambia","India");
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        d_GameMap.assignCountries();
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            l_Player.calculateReinforcementArmies(d_GameMap);
        }
        d_Player1Countries = d_GameMap.getPlayer("Player1").getCapturedCountries();
        d_Player2Countries = d_GameMap.getPlayer("Player2").getCapturedCountries();
    }

    /**
     * Clear the instance
     *
     * @throws Exception in case of any exception
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    /**
     * Test case to execute the command
     *
     */
    @Test
    public void executeTest() {
        Player l_Player = d_GameMap.getPlayer("Player2");
        l_Player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrder.Commands = "bomb " + d_Player1Countries.get(0).getName();
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "),l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Test the validation of Execute command
     */
    @Test
    public void checkIfCommandIsTrue() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrder.Commands = "bomb " + d_Player2Countries.get(0).getName();
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }

    /**
     * Test to check if player has bomb card
     */
    @Test
    public void noBombCardTest() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.BLOCKADE));
        IssueOrder.Commands = "bomb " + d_Player2Countries.get(0).getName();
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
    /**
     * Test to check if Target Country belongs to the player
     */
    @Test
    public void invalidTargetCountry() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.BOMB));
        IssueOrder.Commands = "bomb " + d_Player1Countries.get(0).getName();
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
}
