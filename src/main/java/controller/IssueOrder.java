package controller;

import model.*;
import java.util.Scanner;

/**
 *  Class which is the controller for the Issue Order phase
 *
 * @author Prathika Suvarna
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class IssueOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase = GamePhase.IssueOrder;
    GameMap d_GameMap;

    private final Scanner SCANNER = new Scanner(System.in);

    /**
     * Constructor to get the GameMap instance
     */
    public IssueOrder() {
            d_GameMap = GameMap.getInstance();
        }

    /**
     * A function to start the issue order phase
     *
     * @param p_GamePhase  The current phase which is executing
     * @return the next phase to be executed
     * @throws Exception  when execution fails
     */

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
                    System.out.println("Player:" + l_Player.getName() + "; Armies assigned are: " + l_Player.getReinforcementArmies());
                    System.out.println("The countries to be assigned armies are: ");
                    for(Country l_Country : l_Player.getCapturedCountries() ){
                        System.out.println(l_Country.getName() + " ");
                    }
                    System.out.println("=========================================================================================");
                    String l_Commands = readFromPlayer();
                    l_Player.issueOrder(l_Commands);
                }
            }
            System.out.println("You have exhausted all your armies. Moving to the next phase.");
            System.out.println("=========================================================================================");
            return p_GamePhase.nextState(d_NextGamePhase);
        }

    /**
     *  A function to read all the commands from player
     *
     * @return command entered by the player
     */
    private String readFromPlayer() {
            String l_Command;
            System.out.println("To issue your orders: ");
            System.out.println("1. Enter help to view the set of command");
            while(true){
                l_Command = SCANNER.nextLine();
                if ("deploy".equalsIgnoreCase(l_Command.split(" ")[0])) {
                    if (checkIfCommandIsDeploy(l_Command.toLowerCase())) {
                        return l_Command;
                    }
                } else {
                    System.out.println("List of game loop commands");
                    System.out.println("To deploy the armies : deploy countryID armies");
                    System.out.println("Please enter the correct command");
                }
            }
        }

    /**
     * A function to validate if the command is correct
     *
     * @param p_Command The command entered by player
     * @return true if the format is valid else false
     */
    private boolean checkIfCommandIsDeploy(String p_Command){
            String[] l_Commands = p_Command.split(" ");
            if(l_Commands.length ==  3){
                return l_Commands[0].equals("deploy");
            }
            else
                return false;
        }

}



