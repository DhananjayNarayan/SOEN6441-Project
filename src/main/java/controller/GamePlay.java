package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import utils.MapReader;
import utils.MapValidation;
import utils.SaveMap;
import utils.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GamePlay implements GameController {
    GameMap d_GameMap;
    GamePhase d_NextState = GamePhase.Reinforcement;

    private final Scanner scanner = new Scanner(System.in);
    private final List<String> CLI_COMMANDS = Arrays.asList("showmap", "loadmap", "gameplayer", "assigncountries");

    public GamePlay() {
        d_GameMap = GameMap.getInstance();
    }

    public GamePhase start(GamePhase p_GamePhase) throws ValidationException {
        while (true) {
            System.out.println("Create your game players:" + "\n" + "1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end");
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
                    case "assigncountries": {
                        if (d_GameMap.getPlayers().size() > 1) {
//                         d_GameMap.assignCountries();
                        } else {
                            throw new ValidationException("Create atleast two players");
                        }
                        break;
                    }
                    case "showmap": {
//                        d_GameMap.showMap();
                        break;
                    }
                    case "exit": {
                        return p_GamePhase.nextState(d_NextState);
                    }
                    default: {
                        System.out.println("Order of game play commands");
                        System.out.println("To load the map : loadmap filename");
                        System.out.println("To show the loaded map : showmap");
                        System.out.println("To add or remove a player : gameplayer -add playername -remove playername");
                        System.out.println("To assign countries : assigncountries");
                    }
                }
            }
        }
    }

    private void loadMap(String p_Filename) throws ValidationException {
        if(MapValidation.validateMap(d_GameMap,0)) {
            MapReader.readMap(d_GameMap, p_Filename);
        } else {
            throw new ValidationException("Invalid Map");
        }
    }

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
