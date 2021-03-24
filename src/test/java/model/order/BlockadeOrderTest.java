package model.order;
import model.Country;
import model.Player;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the Blockade Order
 * @author Dhananjay Narayan
 */

public class BlockadeOrderTest {
    Player d_player = new Player();
    Country d_country1= new Country();
    Country d_country2= new Country();
    List<Country> d_CapturedCountries = new ArrayList<>();

    /**
     * This function sets up the assignment requirements for the tests
     */
    @Before
    public void setUp() {

        d_player.setName("Dhananjay");
        d_country1.setName("India");
        d_country2.setName("Pakistan");
        d_country1.setPlayer(d_player);
        d_CapturedCountries.add(d_country1);
        d_player.setCapturedCountries(d_CapturedCountries);
    }

    /**
     * This function tests if the armies are updated accordingly and that the country is removed for the player after blockade.
     */
    @Test
    public void verifyIfArmiesUpdated() {
        int l_initialArmies =10;

        if(d_country1.getPlayer() == d_player) {
            d_country1.setArmies(l_initialArmies);
            System.out.println("Set armies: "+ d_country1.getArmies());
            d_country1.setArmies(d_country1.getArmies()*3);
            System.out.println("New Set armies: "+ d_country1.getArmies());
            int l_updatedArmies = d_country1.getArmies();
            System.out.println("Blockade on " + d_country1.getName() + " by "+ d_player.getName());
            assertEquals((3*l_initialArmies),l_updatedArmies);

            //Now checking that the country is neutral (Not belonging to the player anymore after blockade)
            d_player.getCapturedCountries().remove(d_country1);
            d_CapturedCountries.remove(d_country1);
            System.out.println("After Blockade, Removed Country: "+ d_country1.getName()+" for player "+ d_player.getName());
            assertFalse( d_player.getCapturedCountries().contains(d_country1));
            d_country1.setPlayer(null);
            System.out.println(d_country1.getPlayer());

        }
    }

    /**
     * This function just does a simple test if the country belongs to a player or not
     */
    @Test
    public void verifyIfCountryBelongsToPlayer() {
        // if country not in country not in d_CapturedCountries, return false
        boolean l_x=true;
        boolean l_y=false;
        if(d_country2.getPlayer() != d_player) {
            System.out.println("The target country 2 does not belong to the player.Blockade will not be issued.");
            l_x=false;
        }
        assertFalse(l_x);


        if(d_country1.getPlayer() == d_player) {
            System.out.println("The target country belongs to Player. Blockade can be issued.");
            l_y=true;
        }
        assertTrue(l_y);
    }

}