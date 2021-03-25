package model.order;

import controller.IssueOrder;
import model.Country;
import model.GameMap;
import model.GameSettings;
import model.Player;
import model.strategy.DiceStrategy;
import model.strategy.GameStrategy;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AdvanceOrderTest {
    GameMap d_GameMap;
    GameStrategy d_GameStrategy;

    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        GameSettings l_GameSettings = GameSettings.getInstance();
        l_GameSettings.setStrategy(new DiceStrategy());

        //Add Continent
        d_GameMap.addContinent("Asia", "4");
        //Add Countries
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.addCountry("Vietnam", "Asia");
        //Add Neighbors
        Country l_Country1 = d_GameMap.getCountry("India");
        Country l_Country2 = d_GameMap.getCountry("China");
        Country l_Country3 = d_GameMap.getCountry("Vietnam");
        l_Country1.setNeighbors(l_Country2);
        l_Country2.setNeighbors(l_Country3);
        l_Country3.setNeighbors(l_Country1);
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        //Add Countries to players
        d_GameMap.getPlayer("Player1").getCapturedCountries().add(d_GameMap.getCountry("India"));
        d_GameMap.getPlayer("Player1").getCapturedCountries().add(d_GameMap.getCountry("Vietnam"));
        d_GameMap.getPlayer("Player2").getCapturedCountries().add(d_GameMap.getCountry("China"));
        //Add Reinforcements to players
        d_GameMap.getPlayer("Player1").setReinforcementArmies(10);
        d_GameMap.getPlayer("Player2").setReinforcementArmies(10);
    }

    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    @Test
    public void checkExecutionFailOnNoTroopsDeployed() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("Player2").getCapturedCountries();

        Player l_Player1 = d_GameMap.getPlayer("Player1");
        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(0).getName() + " " + l_CountriesPlayer2.get(0).getName() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    @Test
    public void checkExecutionFailOnNotANeighbor() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        l_CountriesPlayer1.get(0).setArmies(6);
        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(0).getName() + " " + l_CountriesPlayer1.get(1).getName() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    @Test
    public void checkExecutionSuccessOnNeighborAndTroopsDeployedInOwnCountry() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        l_CountriesPlayer1.get(1).setArmies(6);
        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(1).getName() + " " + l_CountriesPlayer1.get(0).getName() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    @Test
    public void checkAdvanceSuccessOnNeighborIfNoKingExists() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);

        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(0).getName() + " " + l_CountriesPlayer2.get(0).getName() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
        assertEquals("Armies Depleted from source and deployed to target",5,d_GameMap.getCountry("China").getArmies());
        assertTrue(l_CountriesPlayer1.get(0).getArmies() > 0);
    }

    @Test
    public void checkAttackSuccessOnNeighborWithKing() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        Player l_Player2 = d_GameMap.getPlayer("Player2");
        // Set Players to countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);
        l_CountriesPlayer2.get(0).setArmies(6);

        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(0).getName() + " " + l_CountriesPlayer2.get(0).getName() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    @Test
    public void checkOwnershipChangeOnAdvanceSuccess() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        Player l_Player2 = d_GameMap.getPlayer("Player2");

        //Set Players to Countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);

        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(0).getName() + " " + l_CountriesPlayer2.get(0).getName() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
        assertEquals("Ownership changed",l_Player1,d_GameMap.getCountry("China").getPlayer());
    }

    @Test
    public void checkExecutionSkipsOnNeutralPlayer() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        List<Country> l_CountriesPlayer2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        Player l_Player2 = d_GameMap.getPlayer("Player2");
        // Set Players to countries
        l_CountriesPlayer1.get(0).setPlayer(l_Player1);
        l_CountriesPlayer2.get(0).setPlayer(l_Player2);
        // Set Armies to each country
        l_CountriesPlayer1.get(0).setArmies(6);
        l_CountriesPlayer2.get(0).setArmies(6);
        //Set Neutral Players
        l_Player1.getNeutralPlayers().add(l_Player2);
        l_Player2.getNeutralPlayers().add(l_Player1);

        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(0).getName() + " " + l_CountriesPlayer2.get(0).getName() + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertTrue(l_Player1.nextOrder().execute());
    }

    @Test
    public void checkExecutionFailOnInvalidCommand() {
        List<Country> l_CountriesPlayer1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        Player l_Player1 = d_GameMap.getPlayer("Player1");
        l_CountriesPlayer1.get(1).setArmies(6);
        IssueOrder.Commands = "advance " + l_CountriesPlayer1.get(1).getName() + " " + "Thailand" + " " + (l_Player1.getReinforcementArmies() - 5);
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player1);
        l_Player1.addOrder(l_Order1);
        assertFalse(l_Player1.nextOrder().execute());
    }

    @Test
    public void printOrderCommand() {

    }
}