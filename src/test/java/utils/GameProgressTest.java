package utils;

import model.GameMap;
import model.GamePhase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *  A class to test all the functionalities in GameProgress saving and loading game
 *
 */
public class GameProgressTest {
    /**
     * gamemap instance
     */
    GameMap d_GameMap;

    /**
     * Initial Setup to be used before all test cases
     *
     * @throws Exception if execution fails
     */
    @Before
    public void setUp() throws Exception {
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
     * savegame progress test case
     */
    @Test
    public void saveGameProgress() {

    }

    /**
     * incorrect file name while loading
     */
    @Test
    public void incorrectFileName() {
        assertEquals(GamePhase.StartUp, GameProgress.LoadGameProgress("random.bin"));
    }

    /**
     * corrupt file while loading
     */
    @Test
    public void corruptFileLoad(){
        assertEquals(GamePhase.StartUp, GameProgress.LoadGameProgress("corrupt.bin"));
    }

    /**
     * successful load game
     */
    @Test
    public void loadGameSuccess(){
        assertEquals(GamePhase.StartUp, GameProgress.LoadGameProgress("test.bin"));
    }
}