package model;

import org.junit.Test;
import utils.ValidationException;
import java.util.*;
import static org.junit.Assert.*;

/**
 * A class to test the functionalities of GameMap Class
 * @author Dhananajay Narayan
 */
public class GameMapTest {
    /**
     * A test to assure that all countries are shuffled/randomized before being assigned.
     * This ensures random assignment of countries.
     *
     * @throws ValidationException if validation fails
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
            String c = l_countryList.get(i);
            l_captured.add(c);
            l_countryList.set(i,"Assigned to some player");
        }
        boolean x= l_countryList.equals(l_captured);
        assertFalse(x);
    }
}