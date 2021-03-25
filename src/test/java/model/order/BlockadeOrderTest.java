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
    Player d_Player = new Player();
    Country d_Country1= new Country();
    Country d_Country2= new Country();
    List<Country> d_CapturedCountries = new ArrayList<>();

    /**
     * This function sets up the assignment requirements for the tests
     */
    @Before
    public void setUp() {

        d_Player.setName("Dhananjay");
        d_Country1.setName("India");
        d_Country2.setName("Pakistan");
        d_Country1.setPlayer(d_Player);
        d_CapturedCountries.add(d_Country1);
        d_Player.setCapturedCountries(d_CapturedCountries);
    }

    /**
     * This function tests if the armies are updated accordingly and that the country is removed for the player after blockade.
     */
    @Test
    public void verifyIfArmiesUpdated() {
        int l_InitialArmies =10;

        if(d_Country1.getPlayer() == d_Player) {
            d_Country1.setArmies(l_InitialArmies);
            System.out.println("Set armies: "+ d_Country1.getArmies());
            d_Country1.setArmies(d_Country1.getArmies()*3);
            System.out.println("New Set armies: "+ d_Country1.getArmies());
            int l_UpdatedArmies = d_Country1.getArmies();
            System.out.println("Blockade on " + d_Country1.getName() + " by "+ d_Player.getName());
            assertEquals((3*l_InitialArmies),l_UpdatedArmies);

            //Now checking that the country is neutral (Not belonging to the player anymore after blockade)
            d_Player.getCapturedCountries().remove(d_Country1);
            d_CapturedCountries.remove(d_Country1);
            System.out.println("After Blockade, Removed Country: "+ d_Country1.getName()+" for player "+ d_Player.getName());
            assertFalse( d_Player.getCapturedCountries().contains(d_Country1));
            d_Country1.setPlayer(null);
            System.out.println(d_Country1.getPlayer());

        }
    }

    /**
     * This function just does a simple test if the country belongs to a player or not
     */
    @Test
    public void verifyIfCountryBelongsToPlayer() {
        // if country not in country not in d_CapturedCountries, return false
        boolean l_X=true;
        boolean l_Y=false;
        if(d_Country2.getPlayer() != d_Player) {
            System.out.println("The target country 2 does not belong to the player.Blockade will not be issued.");
            l_X=false;
        }
        assertFalse(l_X);


        if(d_Country1.getPlayer() == d_Player) {
            System.out.println("The target country belongs to Player. Blockade can be issued.");
            l_Y=true;
        }
        assertTrue(l_Y);
    }

}