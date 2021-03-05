package controller;

import model.*;

import java.util.Arrays;
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

    private final static Scanner SCANNER = new Scanner(System.in);

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
            Boolean[] l_PlayerStatus = new Boolean[d_GameMap.getPlayers().size()];
            int l_Counter = 0;
            Arrays.fill(l_PlayerStatus, Boolean.FALSE);
            while (!ifVisited(l_PlayerStatus)) {
                for (Player l_Player : d_GameMap.getPlayers().values()) {
                    if (l_Player.getReinforcementArmies() <= 0) {
                        l_PlayerStatus[l_Counter] = true;
                        l_Counter++;
                        continue;
                    }
                    System.out.println("Player:" + l_Player.getName() + "; Armies assigned are: " + l_Player.getReinforcementArmies());
                    System.out.println("The countries to be assigned armies are: ");
                    for(Country l_Country : l_Player.getCapturedCountries() ){
                        System.out.println(l_Country.getName() + " ");
                    }
                    System.out.println("=========================================================================================");
                    l_Player.issueOrder();
                    l_Counter++;
                }
                l_Counter = 0;
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
    public static String ReadFromPlayer() {
            return SCANNER.nextLine();
        }

    /**
     * A static function to validate the deploy command
     *
     * @param p_CommandArr The string entered by the user
     * @param p_player The player instance
     * @return true if the command is correct else false
     */
    public static  boolean ValidateCommand(String p_CommandArr, Player p_player){
            int l_ReinforcementArmies;
            String[] l_CommandArr = p_CommandArr.split(" ");
            if(l_CommandArr.length !=3){
                return false;
            }
            else if(!l_CommandArr[0].equalsIgnoreCase("deploy")){
                return false;
            }
            else if (!p_player.checkIfCountryExists(l_CommandArr[1], p_player)) {
                System.out.println("The country does not belong to you");
                return false;
            }
            try {
                l_ReinforcementArmies = Integer.parseInt(l_CommandArr[2]);
            }
            catch (NumberFormatException l_Exception){
                System.out.println("The number format is wrong.");
                return false;
            }
            if (!p_player.deployReinforcementArmiesFromPlayer(l_ReinforcementArmies)) {
                System.out.println("You do not have enough Reinforcement Armies to deploy..");
                return false;
            }
            else{
                return  true;
            }
    }

    /**
     * A function to check if Players exhausted the armies
     *
     * @param p_PlayerStatus The array of the status for each player if
     *                       they have exhausted the army or not
     * @return true if all players have exhausted the army else false
     */
    private boolean ifVisited(Boolean[] p_PlayerStatus){
        for (Boolean l_Status : p_PlayerStatus){
            if(!l_Status){
                return false;
            }
        }
        return true;
    }

}



