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

public class DeployOrderTest {
    GameMap d_GameMap;

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
    }

    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    @Test
    public void execute() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        Player l_Player = d_GameMap.getPlayer("Player1");
        IssueOrder.Commands = "deploy " + l_CountriesPlayer1.get(0).getName() + " " + l_Player.getReinforcementArmies();
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

    @Test
    public void validateCommand() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
        Player l_Player = d_GameMap.getPlayer("Player1");
        IssueOrder.Commands = "deploy " + l_CountriesPlayer1.get(0).getName() + " " + l_Player.getReinforcementArmies();
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().validateCommand());

        IssueOrder.Commands = "deploy " + l_CountriesPlayer2.get(0).getName() + " " + l_Player.getReinforcementArmies();
        l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());

        IssueOrder.Commands = "deploy " + l_CountriesPlayer1.get(0).getName() + " 10";
        l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertFalse(l_Player.nextOrder().validateCommand());
    }
}