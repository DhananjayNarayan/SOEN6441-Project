package controller;

import model.GameMap;
import model.GamePhase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A class to check to test the Issue Order
 */
public class IssueOrderTest {
    /**
     * gameMap instance
     */
    GameMap d_GameMap;
    /**
     * Issue Order instance
     */
    private IssueOrder d_IssueOrder;

    /**
     * Setup for test case
     *
     * @throws Exception exception
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
        d_IssueOrder = new IssueOrder();
        d_IssueOrder.d_GamePhase = GamePhase.IssueOrder;
    }

    /**
     * Clear the setup after the test case
     *
     * @throws Exception exception
     */
    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    /**
     * Validate the command syntax
     */
    @Test
    public void validateCommand() {
        assert d_IssueOrder.validateCommand("deploy india 10", d_GameMap.getPlayer("Player1"));
        assert !d_IssueOrder.validateCommand("deploye india 10", d_GameMap.getPlayer("Player2"));
    }
}