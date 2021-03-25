package model.order;

import controller.IssueOrder;
import model.Card;
import model.CardType;
import model.Country;
import model.GameMap;
import model.Player;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class AirliftOrderTest {
    GameMap d_GameMap;
    List<Country> l_CountryList1 = new ArrayList<Country>();
    List<Country> l_CountryList2 = new ArrayList<Country>();

    @Before
    public void setUp() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addPlayer("Player1");
        d_GameMap.addPlayer("Player2");
        d_GameMap.addContinent("Asia", "5");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("Pakistan", "Asia");
        d_GameMap.addCountry("SriLanka", "Asia");
        d_GameMap.addCountry("Afganisthan", "Asia");
        d_GameMap.addCountry("Bangladesh", "Asia");
        d_GameMap.addCountry("Myanmar", "Asia");
        d_GameMap.addCountry("China", "Asia");
        d_GameMap.assignCountries();
        l_CountryList1 = d_GameMap.getPlayer("Player1").getCapturedCountries();
        l_CountryList2 = d_GameMap.getPlayer("Player2").getCapturedCountries();
    }

    @After
    public void tearDown() throws Exception {
        d_GameMap.flushGameMap();
    }

    @Test
    public void execute() {
        Player l_Player = d_GameMap.getPlayer("Player1");
        l_Player.addPlayerCard(new Card(CardType.AIRLIFT));
        l_CountryList1.get(0).setArmies(100);
        IssueOrder.Commands = "airlift " + l_CountryList1.get(0).getName() + " " + l_CountryList1.get(1).getName()+ " "+ 10;
        Order l_Order1 = OrderCreater.CreateOrder(IssueOrder.Commands.split(" "), l_Player);
        l_Player.addOrder(l_Order1);
        assertTrue(l_Player.nextOrder().execute());
    }

}
