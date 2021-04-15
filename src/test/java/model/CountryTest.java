package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests the functionalities for Country class
 *
 * @author Prathika Suvarna
 */
public class CountryTest extends Country{
    /**
     * ID of country
     */
    String d_Id;
    /**
     * Name of the country
     */
    String d_Name;
    /**
     * Country Object
     */
    Country d_Country = new Country();

    /**
     * This method initializes each value before execution of every test case
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception {

        d_Id = "2";
        d_Country.setId(d_Id);
        d_Name = "Canada";
        d_Country.setName(d_Name);

    }
    /**
     * This is the test method to check the Country ID
     *
     */

    @Test
    public void testCountryId(){
        String l_Id = d_Country.getId();
        assertEquals(d_Id,l_Id);
    }

    /**
     * This is the test method to check the Country Name
     *
     */

    @Test
    public void testCountryName(){
        String l_Name = d_Country.getName();
        assertEquals(d_Name,l_Name);
    }

}
