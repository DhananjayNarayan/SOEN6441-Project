import model.GameController;
import model.GamePhase;
import model.GameSettings;
import utils.InvalidExecutionException;
import utils.ValidationException;
import utils.logger.ConsoleWriter;
import utils.logger.LogEntryBuffer;
import utils.logger.LogEntryWriter;

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
     * Game Settings for warzone game
     */
    private static GameSettings d_GameSettings;

    /**
     * Creating Logger Observable
     * Single Instance needs to be maintained (Singleton)
     */
    private static LogEntryBuffer d_Logger;

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
        d_GameSettings = GameSettings.getInstance();
        d_GameSettings.setStrategy("default");
        d_Logger = LogEntryBuffer.getInstance();
        d_Logger.addObserver(new LogEntryWriter());
        d_Logger.addObserver(new ConsoleWriter());
        d_Logger.clear();
        new GameEngine().start();
    }

    /**
     * The function which runs the whole game in phases
     */
    public void start() {
        try {
            if (!d_GamePhase.equals(GamePhase.ExitGame)) {
                GameController l_GameController = d_GamePhase.getController();
                if (Objects.isNull(l_GameController)) {
                    throw new Exception("No Controller found");
                }
                d_GamePhase = l_GameController.start(d_GamePhase);
                d_Logger.log("You have entered the " + d_GamePhase + " Phase.");
                d_Logger.log("-----------------------------------------------------------------------------------------");
                start();
            }
        } catch (ValidationException | InvalidExecutionException p_Exception) {
            System.err.println(p_Exception.getMessage());
            start();
        } catch (Throwable p_Exception) {
            System.err.println(p_Exception.getMessage());
            System.err.println("Please try again with valid data");
            if(d_GamePhase.equals(GamePhase.MapEditor)){
                start();
            }
        }
    }

}
