package controller;

import model.*;
import utils.MapValidation;
import utils.ValidationException;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class IssueOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase = GamePhase.IssueOrder;
    GameMap d_GameMap;

        public IssueOrder() {
            d_GameMap = GameMap.getInstance();
        }

        @Override
        public GamePhase start(GamePhase p_GamePhase) throws Exception {
            d_GamePhase = p_GamePhase;
            int l_Counter = 0;
            while (l_Counter != d_GameMap.getPlayers().size()) {
                for (Player l_Player : d_GameMap.getPlayers().values()) {
                    System.out.println("Player name is: " + l_Player.getName());
                    if (l_Player.getReinforcementArmies() < 0) {
                        l_Counter++;
                        continue;
                    }
                    System.out.println("Player:" + l_Player.getName() + "-Counter:" + l_Counter);
                    for(Country c : l_Player.getCapturedCountries() ){
                        System.out.println("Countries are : " + c.getName());
                    }
                    String l_Commands = readFromPlayer();
                    l_Player.issueOrder(l_Commands);
                }
            }
            return p_GamePhase.nextState(d_NextGamePhase);
        }

        private String readFromPlayer() {
            // come back here to test
            String l_Command;
            System.out.println("\nPlease enter the command: \n");
            Scanner l_scanner = new Scanner(System.in);
            l_Command = l_scanner.nextLine();
            System.out.println(l_Command);
            return l_Command;
        }
//        public static void main(String[] args) throws ValidationException {
//            MapValidation map = new MapValidation();
//            GameMap gameMap = new GameMap();
//            gameMap.addContinent("Asia", "5");
//
//            gameMap.addCountry("Pak", "Asia");
//            gameMap.addCountry("India", "Asia");
//            gameMap.addCountry("Nepal", "Asia");
//
//
//            gameMap.addNeighbor("Pak", "India");
//            gameMap.addNeighbor("India", "Nepal");
//            gameMap.addNeighbor("Nepal", "Pak");
//
//            gameMap.addPlayer("Neona");
//            gameMap.addPlayer("Pratika");
//
//            gameMap.assignCountries();
//
//        }

}



