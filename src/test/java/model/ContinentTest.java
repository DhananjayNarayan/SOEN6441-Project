package model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests the functionalities for Continent class
 *
 * @author Prathika Suvarna
 */
public class ContinentTest extends Continent{

    String d_Id;
    String d_Name;
    Continent d_Continent = new Continent();

    /**
     * This method initializes each value before execution of every test case
     *
     * @throws Exception if initialisation fails
     */
    @Before
    public void setUp() throws Exception {

        d_Id = "1";
        d_Continent.setId(d_Id);
        d_Name = "Canada";
        d_Continent.setName(d_Name);

    }

    /**
     * This is the test method to check the Continent ID
     *
     */
    @Test
    public void testContinentId(){
        String l_Id = d_Continent.getId();
        assertEquals(d_Id,l_Id);
    }

    /**
     * This is the test method to check the Continent Name
     *
     */
    @Test
    public void testContinentName(){
        String l_Name = d_Continent.getName();
        assertEquals(d_Name,l_Name);
    }

}
