package utils;

import model.GameMap;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *  A class to test all the functionalities in Map Validation
 */
public class MapValidationTest {
    GameMap d_GameMap;

    /**
     *  Intial Setup to be used by all test cases
     * @throws Exception
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
     * Check if Continent is Empty
     * @throws ValidationException
     */
    @Test
    public void checkIfContinentIsEmpty() throws ValidationException {
        d_GameMap.addContinent("Europe", "5");
        assertEquals(true, MapValidation.checkIfContinentIsEmpty(d_GameMap));
    }

    /**
     * Check if duplicate Neighbors exist
     * @throws ValidationException
     */
    @Test
    public void checkDuplicateNeighbours() throws ValidationException {
        d_GameMap.addNeighbor("Pak", "Pak");
        assertEquals(true, MapValidation.checkDuplicateNeighbours(d_GameMap));
    }

    /**
     * Check if continent a subgraph is connected
     * @throws ValidationException
     */
    @Test
    public void checkIfContinentIsConnected() throws ValidationException {
        assertEquals(true, MapValidation.checkIfContinentIsConnected(d_GameMap));
    }

    /**
     * Check if the whole graph is connected
     */
    @Test
    public void checkIfMapIsConnected() {
        assertEquals(true, MapValidation.checkIfMapIsConnected(d_GameMap));
    }
}