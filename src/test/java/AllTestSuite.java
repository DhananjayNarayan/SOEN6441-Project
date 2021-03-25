import controller.ExecuteOrderTest;
import controller.IssueOrderTest;
import controller.ReinforcementTest;
import model.ContinentTest;
import model.CountryTest;
import model.GameMapTest;
import model.PlayerTest;
import model.order.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import utils.MapValidationTest;

@RunWith(Suite.class)

/**
 * Run all test cases
 */
@Suite.SuiteClasses({
        IssueOrderTest.class, ReinforcementTest.class, ExecuteOrderTest.class,
        AirliftOrderTest.class, BlockadeOrderTest.class, DeployOrderTest.class, NegotiateOrderTest.class, AdvanceOrderTest.class, BombOrderTest.class,
        ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class,
        MapValidationTest.class})

/**
 * class for test suite
 */
public class AllTestSuite {

}