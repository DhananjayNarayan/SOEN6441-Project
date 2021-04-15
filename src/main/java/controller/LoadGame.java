package controller;

import model.GameController;
import model.GamePhase;
import utils.GameProgress;
import utils.logger.LogEntryBuffer;

import java.util.Scanner;

/**
 * Loadgame controller
 */
public class LoadGame implements GameController {

    /**
     * logger to print
     */
    private final LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * a method to execute the phase
     *
     * @param p_GamePhase holding the current game phase
     * @return gamephase
     * @throws Exception exception
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        GameProgress.showFiles();
        Scanner l_Scanner = new Scanner(System.in);
        String l_Command = l_Scanner.nextLine();
        String l_FileName = parseCommand(l_Command);
        GamePhase l_GameLoaded = GameProgress.LoadGameProgress(l_FileName);
        if (l_GameLoaded.equals(GamePhase.StartUp)) {
            d_Logger.log("Loading Old game failed, Check the file name");
            return GamePhase.StartUp;
        }
        return l_GameLoaded;
    }

    /**
     * function to parse the command
     *
     * @param command string to be parsed
     * @return command
     */
    private String parseCommand(String command) {
        String[] l_Commands = command.split(" ");
        if (l_Commands.length == 2 && l_Commands[0].equals("loadgame")) {
            return l_Commands[1];
        }
        return "";
    }


}
