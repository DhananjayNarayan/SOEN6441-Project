import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.GameSettings;
import utils.InvalidExecutionException;
import utils.ValidationException;
import utils.logger.LogEntryBuffer;

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
public class GameEngine implements Engine {

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


    public GameEngine() {
        d_GameSettings = GameSettings.getInstance();
        d_GameSettings.setStrategy("default");
        d_Logger = LogEntryBuffer.getInstance();
        d_Logger.clear();
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
                GameMap.getInstance().setGamePhase(d_GamePhase);
                d_Logger.log("\n\n\n/*************************** You have entered the " + d_GamePhase + " Phase *************************/");
                start();
            }
        } catch (ValidationException | InvalidExecutionException p_Exception) {
            System.err.println(p_Exception.getMessage());
            p_Exception.printStackTrace();
            start();
        } catch (Throwable p_Exception) {
            System.err.println(p_Exception.getMessage());
            p_Exception.printStackTrace();
            System.err.println("Please try again with valid data");
            if (d_GamePhase.equals(GamePhase.MapEditor)) {
                start();
            }
        }
    }

    public void setGamePhase(GamePhase p_gamePhase) {
        d_GamePhase = p_gamePhase;
    }

}
