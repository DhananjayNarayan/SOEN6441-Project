package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import utils.SaveMap;
import utils.ValidationException;
import utils.MapValidation;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class is used to create map using game console commands.
 * <p></p>
 */
public class MapEditor implements GameController {
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> CLI_COMMANDS = Arrays.asList("editcontinent", "editcountry", "editneighbor","showmap","savemap","editmap","validatemap");
    GameMap d_GameMap;
    SaveMap d_SaveMap;
    GamePhase d_NextState = GamePhase.LoadGame;

    public MapEditor() {
        this.d_GameMap = GameMap.getInstance();
    }

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException {
        while (true) {
            System.out.println("Enter your map operation:" + "\n" + "1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end map creation");
            String l_Input = scanner.nextLine();
            List<String> l_InputList = Arrays.stream(l_Input.split("-"))
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .collect(Collectors.toList());
            if (!inputValidator(l_InputList)) {
                if (l_Input.startsWith("exit")) {
                    l_InputList.add(0, "exit");
                } else {
                    // if not available in command list forcing to call help
                    l_InputList.add(0, "help");
                }
            }
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
                    case "showmap" : {
//                        d_GameMap.showMap();
                        break;
                    }
                    case "validatemap" : {
//                        d_GameMap.validateMap();
                        break;
                    }
                    case "savemap" : {
                        if(l_CommandArray.length == 1) {
                            d_GameMap.saveMap();
                        }
                        break;
                    }
                    case "editmap" : {
                        if(l_CommandArray.length == 1) {
//                            d_GameMap.editMap(l_CommandArray[0]);
                        }
                        break;
                    }
                    case "exit": {
                            return p_GamePhase.nextState(d_NextState);
                    }
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

    public boolean inputValidator(List<String> p_InputList) {
        if (p_InputList.size() > 0) {
            String l_MainCommand = p_InputList.get(0);
            return CLI_COMMANDS.contains(l_MainCommand.toLowerCase());
        }
        return false;
    }
    public void saveMap() {
        //Ask p_size for minimum number of countries based on player
        if (MapValidation.validateMap(d_GameMap, 0)){
            System.out.println("Done.");
            boolean bool = true;
            while (bool) {
                String mapName = null;
                System.out.println("Please enter the map name to save:");
                if (mapName != null) {
                    if (mapName.isEmpty()) {
                        System.out.println("Please enter a name..");
                    } else {
                        d_GameMap.setName(mapName);
                        if (d_SaveMap.saveMapIntoFile(d_GameMap, mapName)) {
                            System.out.println("Map saved.");
                        } else {
                            System.out.println("Map name already exists, enter different name.");
                        }
                        bool = false;
                    }
                } else {
                    bool = false;
                }
            }
        } else{
            System.out.println("Invalid Map, can not be saved.");
            System.out.println(d_GameMap.getErrorMessage());
        }
    }

}
