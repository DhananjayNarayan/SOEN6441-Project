import model.GamePhase;
import utils.logger.ConsoleWriter;
import utils.logger.LogEntryBuffer;
import utils.logger.LogEntryWriter;

import java.util.Scanner;

/**
 * Class to implement the game
 */
public class Game {
    /**
     * game engine
     */
    private Engine d_Engine;
    /**
     * logger observable
     */
    private final LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    /**
     * game phase
     */
    private GamePhase d_GamePhase;

    /**
     * Default Constructor
     */
    public Game() {
        d_Logger.addObserver(new LogEntryWriter());
        d_Logger.addObserver(new ConsoleWriter());
    }
    /**
     * method to implement main class to start game
     *
     * @param args the arguments
     * @throws Exception if it occurs
     */
    public static void main(String[] args) throws Exception {
        new Game().start();
    }

    /**
     * method which starts each phase in the game
     *
     * @throws Exception when it occurs
     */
    public void start() throws Exception {
        Scanner l_Scanner = new Scanner(System.in);
        d_Logger.log("");
        d_Logger.log("==================================");
        d_Logger.log("\t\t\t Warzone");
        d_Logger.log("==================================");
        d_Logger.log("\t\t\t Main Menu");
        d_Logger.log("\t=======================");
        d_Logger.log("\t\t 1. New Game");
        d_Logger.log("\t\t 2. Load Game");
        d_Logger.log("\t\t 3. Single Game Mode");
        d_Logger.log("\t\t 4. Simulation Mode");
        d_Logger.log("\t\t 5. Exit");
        d_Logger.log("\t=======================");
        d_Logger.log("\t\tSelect the option");
        d_Logger.log("==================================");
        try {
            int option = l_Scanner.nextInt();
            d_Engine = new GameEngine();
            switch (option) {
                case 1: {
                    d_GamePhase = GamePhase.MapEditor;
                    break;
                }
                case 2: {
                    d_GamePhase = GamePhase.LoadGame;
                    break;
                }
                case 3: {
                    d_Engine = new SingleGameEngine();
                    break;
                }
                case 4: {
                    d_Engine = new TournamentEngine();
                    break;
                }
                case 5: {
                    d_GamePhase = GamePhase.ExitGame;
                    break;
                }
                default: {
                    throw new Exception();
                }
            }
        } catch (Exception p_Exception) {
            d_Logger.log("\nPlease choose the correct option number");
            start();
        }
        d_Engine.setGamePhase(d_GamePhase);
        d_Engine.start();
    }
}
