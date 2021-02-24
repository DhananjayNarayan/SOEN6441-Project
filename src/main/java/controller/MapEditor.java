package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
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
                    case "showmap": {
//                        d_GameMap.showMap();
                        break;
                    }
                    case "validatemap": {
//                        d_GameMap.validateMap();
                        break;
                    }
                    case "savemap": {
                        if (l_CommandArray.length == 1) {
                            d_GameMap.setName(l_CommandArray[0]);
                            d_GameMap.saveMap();
                        }
                        break;
                    }
                    case "editmap": {
                        if (l_CommandArray.length == 1) {
                            readMap(l_CommandArray[0]);
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

    /**
     * This function reads the file and places the contents of the file
     * in a Hash Map
     *
     * @param p_FileName the map file name
     */
    public void readMap(String p_FileName) throws ValidationException {
        try {
            File l_File = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader l_Buffer = new BufferedReader(l_FileReader);
            while (l_Buffer.ready()) {
                String l_Read = l_Buffer.readLine();
                if (!l_Read.isEmpty()) {
                    if (l_Read.contains("[") && l_Read.contains("]")) {
                        l_CurrentKey = l_Read.replace("[", "").replace("]", "");
                        l_MapFileContents.put(l_CurrentKey, new ArrayList<>());
                    } else {
                        l_MapFileContents.get(l_CurrentKey).add(l_Read);
                    }
                }
            }
            readContinentsFromFile(l_MapFileContents.get("Continents"));
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(l_MapFileContents.get("Territories"));
            addNeighborsFromFile(l_CountryNeighbors);
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * This function reads the Continents from the file
     *
     * @param p_ContinentArray the value list for Continents
     * @throws ValidationException
     */
    public void readContinentsFromFile(List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split(" ");
            if (l_InputArray.length == 2) {
                d_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    /**
     * This function reads the Countries from the file
     *
     * @param p_CountryArray the value list for Countries
     * @throws ValidationException
     */

    public Map<String, List<String>> readCountriesFromFile(List<String> p_CountryArray) throws ValidationException {
        Map<String, List<String>> l_CountryNeighbors = new HashMap<>();
        for (String l_InputString : p_CountryArray) {
            List<String> l_InputArray = Arrays.stream(l_InputString.split(" ")).collect(Collectors.toList());
            if (l_InputArray.size() >= 2) {
                d_GameMap.addCountry(l_InputArray.get(0), l_InputArray.get(1));
                l_CountryNeighbors.put(l_InputArray.get(0), l_InputArray.subList(2, l_InputArray.size()));
            }
        }
        return l_CountryNeighbors;
    }

    /**
     * This function adds the neighbouring Countries
     *
     * @param p_NeighborList the neighbouring country list
     * @throws ValidationException
     */

    public void addNeighborsFromFile(Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                d_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }

}
