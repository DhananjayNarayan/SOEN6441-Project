package model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test case class to check the right possible next phase from each phase
 */
public class GamePhaseTest {
    /**
     * Game phase object
     */
    GamePhase d_GamePhase;

    /**
     * Method to set up the basics for each test case
     *
     * @throws Exception if any exception occurs
     */
    @Before
    public void setUp() throws Exception {

    }

    /**
     * Method to tear down the basics after each test case ran
     *
     * @throws Exception if any exception occurs
     */
    @After
    public void tearDown() throws Exception {

    }

    /**
     * Test to check next right state after MapEditor state
     */
    @Test
    public void checkNextStateAfterMapEditor() {
        d_GamePhase = GamePhase.MapEditor;
        assertEquals(GamePhase.StartUp,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to check next right state after StartUp state
     */
    @Test
    public void checkNextStateAfterStartUp() {
        d_GamePhase = GamePhase.StartUp;
        assertEquals(GamePhase.Reinforcement,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to check next right state after Reinforcement state
     */
    @Test
    public void checkNextStateAfterReinforcement() {
        d_GamePhase = GamePhase.Reinforcement;
        assertEquals(GamePhase.IssueOrder,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to check next right state after IssueOrder state
     */
    @Test
    public void checkNextStateAfterIssueOrder() {
        d_GamePhase = GamePhase.IssueOrder;
        assertEquals(GamePhase.ExecuteOrder,d_GamePhase.possibleStates().get(0));
    }

    /**
     * Test to check next right states after ExecuteOrder state
     */
    @Test
    public void checkNextStateAfterExecuteOrder() {
        d_GamePhase = GamePhase.ExecuteOrder;
        assertTrue(d_GamePhase.possibleStates().contains(GamePhase.Reinforcement));
        assertTrue(d_GamePhase.possibleStates().contains(GamePhase.ExitGame));
    }

}