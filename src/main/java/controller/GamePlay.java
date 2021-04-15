package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import utils.GameProgress;
import utils.MapReader;
import utils.MapValidation;
import utils.ValidationException;
import utils.logger.LogEntryBuffer;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class implements the Game Controller and it executes the current phases
 *
 * @author Prathika Suvarna
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class GamePlay implements GameController {
    /**
     * A data member for scanner
     */
    private final Scanner SCANNER = new Scanner(System.in);
    /**
     * A data member that stores the list of commands for gameplay as list
     */
    private final List<String> CLI_COMMANDS = Arrays.asList("showmap", "loadmap", "gameplayer", "assigncountries", "savegame", "loadgame", "tournament");
    GameMap d_GameMap;
    /**
     * Reinforcement phase
     */
    GamePhase d_ReinforcementPhase = GamePhase.Reinforcement;
    /**
     * Map editor
     */
    GamePhase d_MapEditorPhase = GamePhase.MapEditor;
    /**
     * LogEntry Buffer Instance
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * This is the default constructor
     */
    public GamePlay() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * This function starts the game phase and passes through the tasks in the game phase
     * depending on the command given
     *
     * @param p_GamePhase current Game Phase
     * @return the next Game Phase
     * @throws ValidationException when validation fails
     */
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException {
        while (true) {
            d_Logger.log("1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end");
            String l_Input = SCANNER.nextLine();
            List<String> l_InputList;
            if (l_Input.contains("-")) {
                l_InputList = Arrays.stream(l_Input.split("-"))
                        .filter(s -> !s.isEmpty())
                        .map(String::trim)
                        .collect(Collectors.toList());
            } else {
                l_InputList = Arrays.stream(l_Input.split(" ")).collect(Collectors.toList());
            }

            if (!inputValidator(l_InputList)) {
                if (l_Input.startsWith("exit")) {
                    l_InputList.add(0, "exit");
                } else {
                    l_InputList.clear();
                    // if not available in command list forcing to call help
                    l_InputList.add("help");
                    l_InputList.add("dummy");
                }
            }
            //Handle loadmap command from console

            String l_MainCommand = l_InputList.get(0);
            l_InputList.remove(l_MainCommand);
            for (String l_Command : l_InputList) {
                String[] l_CommandArray = l_Command.split(" ");
                switch (l_MainCommand.toLowerCase()) {
                    case "loadmap": {
                        if (l_CommandArray.length == 1) {
                            loadMap(l_CommandArray[0]);
                        }
                        break;
                    }

                    case "gameplayer": {
                        if (l_CommandArray.length > 0) {
                            switch (l_CommandArray[0]) {
                                case "add": {
                                    if (l_CommandArray.length == 2) {
                                        d_GameMap.addPlayer(l_CommandArray[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                                case "remove": {
                                    if (l_CommandArray.length == 2) {
                                        d_GameMap.removePlayer(l_CommandArray[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    //Handle assigncountries command from console

                    case "assigncountries": {
                        if (d_GameMap.getPlayers().size() > 1) {
                            d_GameMap.assignCountries();
                        } else {
                            d_Logger.log("Game ended as the minimum players are not there.");
                            throw new ValidationException("Create atleast two players");
                        }
                        break;
                    }
                    //Handle showmap command from console
                    case "showmap": {
                        d_GameMap.showMap();
                        break;
                    }
                    case "savegame" : {
                        if (l_CommandArray.length == 1) {
                            GameProgress.SaveGameProgress(d_GameMap, l_CommandArray[0]);
                            d_GameMap.setGamePhase(d_MapEditorPhase);
                            return d_MapEditorPhase;
                        }
                        break;
                    }
                    case "loadgame" : {
                        if (l_CommandArray.length == 1) {
                            if(!GameProgress.LoadGameProgress(l_CommandArray[0]).equals(GamePhase.StartUp)){
                                return GameProgress.LoadGameProgress(l_CommandArray[0]);
                            }
                        }
                        break;
                    }
                    case "exit": {
                        d_Logger.log("================================ End of StartUp Phase ==================================");
                        d_GameMap.setGamePhase(d_ReinforcementPhase);
                        return p_GamePhase.nextState(d_ReinforcementPhase);
                    }
                    //Print the commands for help
                    default: {
                        d_Logger.log("Order of game play commands:");
                        d_Logger.log("-----------------------------------------------------------------------------------------");
                        d_Logger.log("To load the map : loadmap filename");
                        d_Logger.log("To show the loaded map : showmap");
                        d_Logger.log("To add or remove a player : gameplayer -add playername -remove playername");
                        d_Logger.log("To assign countries : assigncountries");
                        d_Logger.log("To save the game : savegame filename");
                        d_Logger.log("To load the game : loadgame filename");
                        d_Logger.log("-----------------------------------------------------------------------------------------");

                    }
                }
            }
        }
    }

    /**
     * This method loads the game map from the map file
     *
     * @param p_Filename the map file name
     * @throws ValidationException when validation fails
     */
    private void loadMap(String p_Filename) throws ValidationException {
        MapReader.readMap(d_GameMap, p_Filename);
        if (!MapValidation.validateMap(d_GameMap, 0)) {
            throw new ValidationException("Invalid Map");
        }
    }

    /**
     * This method validates to check if the current cli command is executable
     * in the current phase
     *
     * @param p_InputList the command list from console
     * @return true if command is executable else false
     */
    public boolean inputValidator(List<String> p_InputList) {
        if (p_InputList.size() > 0) {
            String l_MainCommand = p_InputList.get(0);
            if (p_InputList.size() == 1) {
                p_InputList.add("dummy");
            }
            return CLI_COMMANDS.contains(l_MainCommand.toLowerCase());
        }
        return false;
    }
}
