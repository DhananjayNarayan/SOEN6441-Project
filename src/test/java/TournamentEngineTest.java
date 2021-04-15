import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.ValidationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test case class to check the correct starting phase of game
 *
 * @author Madhuvanthi Hemanathan
 */
public class TournamentEngineTest extends TournamentEngine {
    /**
     * the game engine
     */
    public static Engine d_Engine;

    /**
     * to start the tournament engine
     */
    public TournamentEngineTest() {
        init();
    }

    /**
     * Method to set up the basics for each test case
     *
     * @throws Exception if any exception occurs
     */
    @Before
    public void setUp() throws Exception {
        d_Engine = new TournamentEngineTest();
    }

    /**
     * override of init method
     */
    @Override
    public void init() {

    }

    /**
     * Method to tear down the basics after each test case ran
     *
     * @throws Exception if any exception occurs
     */
    @After
    public void tearDown() throws Exception {
        d_Engine = null;
    }

    /**
     * Check if tournament command options are valid
     */
    @Test
    public void checkValidParseOfTournamentCommand() {
        String l_TournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D 3";
        this.parseCommand(l_TournamentCommand);
        assertEquals(2, d_Options.getGames());
        assertEquals(3, d_Options.getMaxTries());
        assertEquals(1, d_Options.getMap().size());
        assertEquals("Australia.map", d_Options.getMap().get(0));

    }

    /**
     * Check possibilities of invalid options in a tournament command
     */
    @Test
    public void checkInvalidCommandOptions() {
        String l_TournamentCommand = "tournament -M Australia.map -P aggressive,random -G 6 -D 3";
        d_Options = this.parseCommand(l_TournamentCommand);
        assertNull(d_Options);
        l_TournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D -9";
        d_Options = this.parseCommand(l_TournamentCommand);
        assertNull(d_Options);
        l_TournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D 60";
        d_Options = this.parseCommand(l_TournamentCommand);
        assertNull(d_Options);
        l_TournamentCommand = "tournament -M Australia.map -P aggressive -G 2 -D 60";
        d_Options = this.parseCommand(l_TournamentCommand);
        assertNull(d_Options);
    }

    /**
     * Check if Result of tournament is not null
     *
     * @throws ValidationException
     */
    @Test
    public void checkValidResultOfTournament() throws ValidationException {
        String l_TournamentCommand = "tournament -M Australia.map -P aggressive,random -G 2 -D 3";
        this.parseCommand(l_TournamentCommand);
        this.start();
        assertEquals(2,this.d_Results.size());
    }
}