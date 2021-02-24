package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import utils.ValidationException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class GamePlay implements GameController {
    GameMap d_GameMap;
    GamePhase d_GamePhase = GamePhase.Reinforcement;

    private final Scanner scanner = new Scanner(System.in);

    public GamePlay() {
        d_GameMap = GameMap.getInstance();
    }

    public GamePhase start(GamePhase p_GamePhase) throws ValidationException {
        while (true) {
            System.out.println("Create your game players:" + "\n" + "1. Enter help to view the set of commands" + "\n" + "2. Enter exit to end");
            String l_Input = scanner.nextLine();
            List<String> l_InputList = Arrays.stream(l_Input.split("-"))
                    .filter(s -> !s.isEmpty())
                    .map(String::trim)
                    .collect(Collectors.toList());
            if (l_InputList.contains("gameplayer")) {
                l_InputList.remove(l_InputList.get(0));
                for (String l_Command : l_InputList) {
                    String[] l_CommandArray = l_Command.split(" ");
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
                        default: {
                            System.out.println("List of player creation commands");
                            System.out.println("To add or remove a player : gameplayer -add playername -remove playername");
                        }
                    }
                }
            } else if (l_InputList.contains("exit")) {
                return p_GamePhase.nextState(d_GamePhase);
            } else {
                System.out.println("List of player creation commands");
                System.out.println("To add or remove a player : gameplayer -add playername -remove playername");
            }
        }
    }
}
