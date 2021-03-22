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

    @Before
    public void setUp() throws Exception {

        d_Id = "1";
        d_Continent.setId(d_Id);
        d_Name = "Canada";
        d_Continent.setName(d_Name);

    }

    @Test
    public void testContinentId(){
        String l_Id = d_Continent.getId();
        assertEquals(d_Id,l_Id);
    }

    @Test
    public void testContinentName(){
        String l_Name = d_Continent.getName();
        assertEquals(d_Name,l_Name);
    }

}
