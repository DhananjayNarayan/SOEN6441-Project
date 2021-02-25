package utils;

import model.GameMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  A class to test all the functionalities in Map Validation
 *
 */
public class MapValidationTest {
    GameMap d_GameMap;

    /**
     * Initial Setup to be used before all test cases
     *
     * @throws Exception if execution fails
     */
    @Before
    public void setup() throws Exception {
        d_GameMap = GameMap.getInstance();
        d_GameMap.addContinent("Asia", "5");
        d_GameMap.addContinent("US", "5");
        d_GameMap.addContinent("Africa", "5");
        d_GameMap.addContinent("Antartica", "5");
        d_GameMap.addContinent("Aus", "5");

        d_GameMap.addCountry("Pak", "Africa");
        d_GameMap.addCountry("India", "Asia");
        d_GameMap.addCountry("Newyork", "US");
        d_GameMap.addCountry("Penguin", "Antartica");
        d_GameMap.addCountry("Melbourne", "Aus");

        d_GameMap.addNeighbor("Pak", "India");
        d_GameMap.addNeighbor("India", "Pak");
        d_GameMap.addNeighbor("Pak", "Newyork");
        d_GameMap.addNeighbor("Newyork", "Pak");
        d_GameMap.addNeighbor("Penguin", "India");
        d_GameMap.addNeighbor("India", "Penguin");
        d_GameMap.addNeighbor("Penguin", "Melbourne");
        d_GameMap.addNeighbor("Melbourne", "Penguin");
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
     * This method tests if Continent is Empty
     *
     * @throws ValidationException if validation fails
     */
    @Test
    public void checkIfContinentIsEmpty() throws ValidationException {
        d_GameMap.addContinent("Europe", "5");
        assertEquals(true, MapValidation.checkIfContinentIsEmpty(d_GameMap));
    }

    /**
     * This method tests if duplicate Neighbors exist
     *
     * @throws ValidationException if validation fails
     */
    @Test
    public void checkDuplicateNeighbours() throws ValidationException {
        d_GameMap.addNeighbor("Pak", "Pak");
        assertEquals(true, MapValidation.checkDuplicateNeighbours(d_GameMap));
    }

    /**
     * This method tests if continent subgraph is connected
     *
     * @throws ValidationException if validation fails
     */
    @Test
    public void checkIfContinentIsConnected() throws ValidationException {
        assertEquals(true, MapValidation.checkIfContinentIsConnected(d_GameMap));
    }

    /**
     * This method tests if the whole graph is connected
     */
    @Test
    public void checkIfMapIsConnected() {
        assertEquals(true, MapValidation.checkIfMapIsConnected(d_GameMap));
    }
}