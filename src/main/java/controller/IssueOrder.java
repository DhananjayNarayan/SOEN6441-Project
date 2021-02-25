package controller;

import model.*;
import java.util.Scanner;
import static model.Player.d_OrderList;

public class IssueOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase = GamePhase.IssueOrder;
    GameMap d_GameMap;

    private final Scanner d_scanner = new Scanner(System.in);

        public IssueOrder() {
            d_GameMap = GameMap.getInstance();
        }

        @Override
        public GamePhase start(GamePhase p_GamePhase) throws Exception {
            d_GamePhase = p_GamePhase;
            int l_Counter = 0;
            while (l_Counter < d_GameMap.getPlayers().size()) {
                for (Player l_Player : d_GameMap.getPlayers().values()) {
                    if (l_Player.getReinforcementArmies() <= 0) {
                        l_Counter++;
                        continue;
                    }
                    System.out.println("Player:" + l_Player.getName() + "-Counter:" + l_Counter);
                    for(Country c : l_Player.getCapturedCountries() ){
                        System.out.println("Countries are : " + c.getName() + "- Armies:" + l_Player.getReinforcementArmies());
                    }
                    String l_Commands = readFromPlayer();
                    l_Player.issueOrder(l_Commands);
                }
            }
            System.out.println(d_OrderList);
            return p_GamePhase.nextState(d_NextGamePhase);
        }

        private String readFromPlayer() {
            String l_Command;
            System.out.println("To issue your orders:\" + \"\\n\" + \"1. Enter help to view the set of command");
            l_Command = d_scanner.nextLine();
            return l_Command;
        }

}



