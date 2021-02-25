import model.GameController;
import model.GamePhase;
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
    GamePhase d_GamePhase = GamePhase.MapEditor;

    /**
     * Main method to run the game
     * 
     * @param args passed to main if used in command line
     */
    public static void main(String[] args) {
        new GameEngine().start();
    }

    /**
     * The function which runs the whole game in phases
     *
     */
    public void start() {
        try {
            if (!d_GamePhase.equals(GamePhase.ExitGame)) {
                GameController l_GameController = d_GamePhase.getController();
                if(Objects.isNull(l_GameController)) {
                    throw new Exception("No Controller found");
                }
                d_GamePhase = l_GameController.start(d_GamePhase);
                System.out.println("You have entered the " + d_GamePhase + " Phase.");
                System.out.println("-----------------------------------------------------------------------------------------");
                start();
            }
        } catch (ValidationException | InvalidExecutionException p_Exception) {
            System.err.println(p_Exception.getMessage());
            start();
        } catch (Exception p_Exception) {
            p_Exception.printStackTrace();
        }
    }

}
