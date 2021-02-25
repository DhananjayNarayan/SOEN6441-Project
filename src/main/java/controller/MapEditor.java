package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import utils.MapReader;
import utils.SaveMap;
import utils.ValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is used to create map using game console commands.
 * <p></p>
 */
public class MapEditor implements GameController {
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> CLI_COMMANDS = Arrays.asList("editcontinent", "editcountry", "editneighbor", "showmap", "savemap", "editmap", "validatemap");
    GameMap d_GameMap;
    GamePhase d_NextState = GamePhase.LoadGame;
    /**
     *Map editor
     *
     */
    public MapEditor() {
        this.d_GameMap = GameMap.getInstance();
    }
    /**
     * Start The enum GamePhase that maintains the flow of the game play
     * @param p_GamePhase Parameter of the enum GamePhase is passed
     *
     * @throws ValidationException if the commands are invaid
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException {
        while (true) {
            System.out.println("Enter your map operation:" + "\n" + "1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end map creation");
            String l_Input = scanner.nextLine();
            List<String> l_InputList = null;
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
                    // if not available in command list forcing to call help
                    l_InputList.add(0, "help");
                }
            }
           /**
             * Handle editcontinent command from console
            */
            String l_MainCommand = l_InputList.get(0);
            l_InputList.remove(l_MainCommand);
            for (String l_Command : l_InputList) {
                String[] l_CommandArray = l_Command.split(" ");
                switch (l_MainCommand.toLowerCase()) {
                    case "editcontinent": {
                        if (l_CommandArray.length > 0) {
                            switch (l_CommandArray[0]) {
                                case "add": {
                                    if (l_CommandArray.length == 3) {
                                        d_GameMap.addContinent(l_CommandArray[1], l_CommandArray[2]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                                case "remove": {
                                    if (l_CommandArray.length == 2) {
                                        d_GameMap.removeContinent(l_CommandArray[1]);
                                    } else {
                                        throw new ValidationException();
                                    }
                                    break;
                                }
                            }
                        }
                        break;
                    }
                    /*
                    Handle editcountry command from console
                     */
                    case "editcountry": {
                        switch (l_CommandArray[0]) {
                            case "add": {
                                if (l_CommandArray.length == 3) {
                                    d_GameMap.addCountry(l_CommandArray[1], l_CommandArray[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                            case "remove": {
                                if (l_CommandArray.length == 2) {
                                    d_GameMap.removeCountry(l_CommandArray[1]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                        }
                        break;
                    }
                    /**
                     * Handle editneighbor command from console
                     */
                    case "editneighbor": {
                        switch (l_CommandArray[0]) {
                            case "add": {
                                if (l_CommandArray.length == 3) {
                                    d_GameMap.addNeighbor(l_CommandArray[1], l_CommandArray[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                            case "remove": {
                                if (l_CommandArray.length == 3) {
                                    d_GameMap.removeNeighbor(l_CommandArray[1], l_CommandArray[2]);
                                } else {
                                    throw new ValidationException();
                                }
                                break;
                            }
                        }
                        break;
                    }
                    /**
                     * Handle showmap command from console
                     */
                    case "showmap": {
//                        d_GameMap.showMap();
                        break;
                    }
                    /**
                     * Handle validatemap command from console
                     */
                    case "validatemap": {
//                        d_GameMap.validateMap();
                        break;
                    }
                    /**
                     * Handle savemap command from console
                     */
                    case "savemap": {
                        if (l_CommandArray.length == 1) {
                            d_GameMap.setName(l_CommandArray[0]);
                            d_GameMap.saveMap();
                        }
                        break;
                    }
                    /**
                     * Handle editmap command from console
                     */
                    case "editmap": {
                        if (l_CommandArray.length == 1) {
                            MapReader.readMap(d_GameMap, l_CommandArray[0]);
                        }
                        break;
                    }
                    case "exit": {
                        return p_GamePhase.nextState(d_NextState);
                    }
                    /**
                     * Print the commands for help
                     */
                    default: {
                        System.out.println("List of map creation commands");
                        System.out.println("To add or remove a continent : editcontinent -add continentID continentvalue -remove continentID");
                        System.out.println("To add or remove a country : editcountry -add countryID continentID -remove countryID");
                        System.out.println("To add or remove a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
                    }
                }
            }
        }
    }
    /**
     * Input Validator the to check if the input is from the list of console commands
     * @param p_InputList
     * @return false, if not from the console commands
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
