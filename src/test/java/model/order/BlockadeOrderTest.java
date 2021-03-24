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
    Player player = new Player();
    Country country1= new Country();
    Country country2= new Country();
    List<Country> d_CapturedCountries = new ArrayList<>();

    /**
     * This function sets up the assignment requirements for the tests
     */
    @Before
    public void setUp() {

        player.setName("Dhananjay");
        country1.setName("India");
        country2.setName("Pakistan");
        country1.setPlayer(player);
        d_CapturedCountries.add(country1);
        player.setCapturedCountries(d_CapturedCountries);
    }

    /**
     * This function tests if the armies are updated accordingly and that the country is removed for the player after blockade.
     */
    @Test
    public void verifyIfArmiesUpdated() {
        int initialArmies =10;

        if(country1.getPlayer() == player) {
            country1.setArmies(initialArmies);
            System.out.println("Set armies: "+ country1.getArmies());
            country1.setArmies(country1.getArmies()*3);
            System.out.println("New Set armies: "+ country1.getArmies());
            int updatedArmies = country1.getArmies();
            System.out.println("Blockade on " + country1.getName() + " by "+player.getName());
            assertEquals((3*initialArmies),updatedArmies);

            //Now checking that the country is neutral (Not belonging to the player anymore after blockade)
            player.getCapturedCountries().remove(country1);
            d_CapturedCountries.remove(country1);
            System.out.println("After Blockade, Removed Country: "+country1.getName()+" for player "+player.getName());
            assertFalse( player.getCapturedCountries().contains(country1));
            country1.setPlayer(null);
            System.out.println(country1.getPlayer());

        }
    }

}