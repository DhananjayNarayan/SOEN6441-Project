package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.Player;

import java.util.Scanner;

public class IssueOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase = GamePhase.IssueOrder;
    GameMap d_GameMap;

    public IssueOrder(){
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        d_GamePhase = p_GamePhase;
        int l_Counter = 0;
        while(l_Counter != d_GameMap.getPlayers().size()){
            for(Player l_Player : d_GameMap.getPlayers().values()){
                if(l_Player.getReinforcementArmies() < 0){
                    l_Counter++;
                    continue;
                }
                String l_Commands = readFromPlayer();
                l_Player.issueOrder(l_Commands);
            }
        }
        return p_GamePhase.nextState(d_NextGamePhase);
    }

    private String readFromPlayer(){
        // come back here to test
        String l_Command;
        System.out.println("\nPlease enter the command: \n");
        Scanner l_scanner = new Scanner(System.in);
        l_Command = l_scanner.nextLine();
        System.out.println(l_Command);
        return l_Command;
    }
    public static void main(String args[]){

    }
}
