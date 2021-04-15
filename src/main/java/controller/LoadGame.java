package controller;

import model.GameController;
import model.GamePhase;
import utils.GameProgress;
import utils.logger.LogEntryBuffer;

import java.util.Scanner;

public class LoadGame implements GameController {

    private final LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        GameProgress.showFiles();
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        String fileName = parseCommand(command);
        GamePhase l_GameLoaded = GameProgress.loadGameProgress(fileName);
        if (l_GameLoaded.equals(GamePhase.StartUp)) {
            d_Logger.log("Loading Old game failed, Check the file name");
            return GamePhase.StartUp;
        }
        return l_GameLoaded;
    }

    private String parseCommand(String command) {
        String[] commands = command.split(" ");
        if (commands.length == 2 && commands[0].equals("loadgame")) {
            return commands[1];
        }
        return "";
    }


}
