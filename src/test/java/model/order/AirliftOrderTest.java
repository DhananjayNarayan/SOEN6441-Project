package model.order;
import model.Country;
import model.Player;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the Airlift Order
 * @author Surya Manian
 */
public class AirliftOrderTest {
    Player d_Player = new Player();
    Player d_player1 = new Player();
    List<Country> d_CapturedCountries = new ArrayList<>();
    Country d_CountryA= new Country();
    Country d_CountryB= new Country();

    /**
     * This function sets up the assignment requirements for the tests
     */
    @Before
    public void setUp() {

        d_Player.setName("P1");
        d_CountryA.setName("C1");
        d_CountryB.setName("C2");
        d_CountryA.setPlayer(d_Player);
        d_CapturedCountries.add(d_CountryA);
        d_Player.setCapturedCountries(d_CapturedCountries);

    }

    /**
     * This function tests if the player is valid.
     */
    @Test
    public void CheckPlayerValidity() {
        // If player is null, return false
        boolean l_A=true;
        boolean l_B=false;
        {
            if(d_player1 != null) {
                System.out.println("The player is  valid!");
                l_B=true;
                assertTrue(l_B);
            }
            else {

                l_A=false;
                System.out.println("The Player is not valid.");
                assertFalse(l_A);
            }
        }
    }

    /**
     * This function tests if the source country belongs to the player.
     */
    @Test
    public void CheckSourceCountryValidity() {
        // if country not in d_CapturedCountries, return false
        boolean l_A=true;
        boolean l_B=false;
        if(d_CountryA.getPlayer() != d_Player) {
            System.out.println("The source country does not belong to the player. AirliftOrder validation failed.");
            l_A=false;
            assertFalse(l_A);
        }


        if(d_CountryA.getPlayer() == d_Player) {
            System.out.println("The source country belongs to the player.Success!");
            l_B=true;
            assertTrue(l_B);
        }

    }

    /**
     * This function tests if the target country belongs to the player.
     */
    @Test
    public void CheckTargetCountryValidity() {
        // if country not in d_CapturedCountries, return false
        boolean l_A=true;
        boolean l_B=false;
        if(d_CountryB.getPlayer() != d_Player) {
            System.out.println("The target country does not belong to the player. AirliftOrder validation failed.");
            l_A=false;
            assertFalse(l_A);
        }


        if(d_CountryB.getPlayer() == d_Player) {
            System.out.println("The target country belongs to Player.Success!");
            l_B=true;
            assertTrue(l_B);
        }

    }
}
