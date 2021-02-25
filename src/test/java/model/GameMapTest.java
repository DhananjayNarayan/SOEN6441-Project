package model;
/**
 * A class to test the functionalities of GameMap Class
 * @author Dhananajay Narayan
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.ValidationException;
import java.util.Random;

import java.util.*;

import static org.junit.Assert.*;

public class GameMapTest {


    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetContinents() {
    }

    @Test
    public void testSetContinents() {
    }

    @Test
    public void testGetCountries() {
    }

    @Test
    public void testSetCountries() {
    }

    @Test
    public void testGetPlayers() {
    }

    @Test
    public void testSetPlayers() {
    }

    /**
     * A test to assure that all countries are shuffled/randomized before being assigned. This ensures random assignment of countries.
     * @throws ValidationException
     */
    @Test
    public void assignCountries() throws ValidationException {

        List<String> l_countryList = new ArrayList<String>();
        l_countryList.add("India");
        l_countryList.add("Pakistan");
        l_countryList.add("Bangladesh");
        l_countryList.add("England");
        l_countryList.add("Australia");
        l_countryList.add("Sri Lanka");
        Collections.shuffle(l_countryList);
        List<String> l_captured = new ArrayList<String>();
        for(int i = 0; i < l_countryList.size(); i++) {
            Random rand = new Random();
            int rand_int1 = rand.nextInt(l_countryList.size());
            String c = l_countryList.get(rand_int1);
            l_captured.add(c);
            l_countryList.remove(c);
        }
       boolean x= l_countryList.equals(l_captured);
        assertFalse(x);

        }

    @Test
    public void showMap() {
    }
}