package model.order;

import controller.IssueOrder;
import model.Card;
import model.CardType;
import model.GameMap;
import model.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Negotiate Order
 */
public class NegotiateOrderTest {
    GameMap d_GameMap;

    /**
     * Setup for the test case
     * @throws Exception in case of any exception
     */
    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
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
     * Test to check if the command executes correctly
     *
     */
    @Test
    public void execute() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrder.Commands = "negotiate Player2";
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    /**
     * Test the validation of Negotiate command
     */
    @Test
    public void checkIfCommandIsTrue() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrder.Commands = "negotiate Player2";
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());
    }

    /**
     * Test the validation of Negotiate command
     */
    @Test
    public void checkIfCommandIsfalse() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.DIPLOMACY));
        IssueOrder.Commands = "negotiate Player3";
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
}