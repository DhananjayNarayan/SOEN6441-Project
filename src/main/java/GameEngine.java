import model.GameController;
import model.GamePhase;
import model.GameSettings;
import model.strategy.DiceStrategy;
import utils.InvalidExecutionException;
import utils.ValidationException;

import java.util.Objects;

/**
 * A class to start the game with the Map Editor Phase
 *
 * @author Prathika Suvarna
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class GameEngine {
    /**
     * gamephase instance for the state
     */
    GamePhase d_GamePhase = GamePhase.MapEditor;

    /**
     * Main method to run the game
     *
     * @param args passed to main if used in command line
     */
    public static void main(String[] args) {
        GameSettings l_gameSettings = GameSettings.getInstance();
        l_gameSettings.setStrategy(new DiceStrategy());
        new GameEngine().start(l_gameSettings);
    }

    /**
     * The function which runs the whole game in phases
     * @param p_GameSettings controls the game phase
     */
    public void start(GameSettings p_GameSettings) {
        try {
            if (!d_GamePhase.equals(GamePhase.ExitGame)) {
                GameController l_GameController = d_GamePhase.getController();
                if (Objects.isNull(l_GameController)) {
                    throw new Exception("No Controller found");
                }
                d_GamePhase = l_GameController.start(d_GamePhase);
                System.out.println("You have entered the " + d_GamePhase + " Phase.");
                System.out.println("-----------------------------------------------------------------------------------------");
                start(p_GameSettings);
            }
        } catch (ValidationException | InvalidExecutionException p_Exception) {
            System.err.println(p_Exception.getMessage());
            start(p_GameSettings);
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }
    }

}
