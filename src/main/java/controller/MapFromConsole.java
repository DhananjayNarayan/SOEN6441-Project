package controller;

import model.GameMap;
import utils.MapValidation;
import utils.ValidationException;
import utils.SaveMap;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MapFromConsole {
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> CLI_COMMANDS = Arrays.asList("editcontinent", "editcountry", "editneighbor");
    GameMap d_GameMap;
    SaveMap d_SaveMap;
    public MapFromConsole(GameMap p_GameMap) {
        this.d_GameMap = p_GameMap;
    }

    public void start() throws ValidationException {
        while (true) {
            System.out.println("Enter your map operation:" + "\n" + "Type help to view the set of commands");
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
                    case "exit": {
                        return;
                    }
                    default: {
                        System.out.println("List of map creation commands");
                        System.out.println("To add a continent : editcontinent -add continentID continentvalue -remove continentID");
                        System.out.println("To add a country : editcountry -add countryID continentID -remove countryID");
                        System.out.println("To add a neighbor to a country : editneighbor -add countryID neighborcountryID -remove countryID neighborcountryID");
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

    public void saveMap(GameMap d_GameMap) {
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
                            System.out.println("Map has been saved.");
                        } else {
                            System.out.println("Not able to save map as text file, enter different name.");
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
