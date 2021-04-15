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
import utils.GameProgress;
import utils.GameProgressTest;
import utils.MapValidationTest;


/**
 * A class for test suites
 */
@RunWith(Suite.class)

/**
 * Run all test cases
 */
@Suite.SuiteClasses({
        IssueOrderTest.class, ReinforcementTest.class, ExecuteOrderTest.class,
        AirliftOrderTest.class, BlockadeOrderTest.class, DeployOrderTest.class, NegotiateOrderTest.class, AdvanceOrderTest.class, BombOrderTest.class,
        ContinentTest.class, CountryTest.class, GameMapTest.class, PlayerTest.class,
        MapValidationTest.class, GameProgressTest.class})

/**
 * A class for test suites
 */
public class AllTestSuite {

}