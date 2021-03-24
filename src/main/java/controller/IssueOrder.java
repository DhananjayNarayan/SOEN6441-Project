package controller;

import model.*;
import utils.LogEntryBuffer;

import java.util.*;

/**
 * Class which is the controller for the Issue Order phase
 *
 * @author Prathika Suvarna
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class IssueOrder implements GameController {
    private final static Scanner SCANNER = new Scanner(System.in);
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    GamePhase d_GamePhase;
    GameMap d_GameMap;
    private static Set<Player> SkippedPlayers = new HashSet<>();
    public static String Commands = null;
    LogEntryBuffer d_leb = new LogEntryBuffer();

    /**
     * Constructor to get the GameMap instance
     */
    public IssueOrder() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * A function to start the issue order phase
     *
     * @param p_GamePhase The current phase which is executing
     * @return the next phase to be executed
     * @throws Exception when execution fails
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        d_GamePhase = p_GamePhase;
        d_leb.logInfo("\n ISSUE ORDER PHASE \n");
        while (!(SkippedPlayers.size() == d_GameMap.getPlayers().size())) {
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                if (!SkippedPlayers.isEmpty() && SkippedPlayers.contains(l_Player)) {
                    continue;
                }
                System.out.println("Player:" + l_Player.getName() + "; Armies assigned are: " + l_Player.getReinforcementArmies());
                System.out.println("The countries to be assigned to the player are: ");
                for (Country l_Country : l_Player.getCapturedCountries()) {
                    System.out.println(l_Country.getName() + " ");
                }
                if(!l_Player.getPlayerCards().isEmpty()){
                    System.out.println("The Cards assigned to the Players are:");
                    for(Card l_Card : l_Player.getPlayerCards()){
                        System.out.println();
                    }
                }
                System.out.println("=================================================================================");
                boolean l_IssueCommand = false;
                while (!l_IssueCommand) {
                    System.out.println("List of game loop commands");
                    System.out.println("To deploy the armies : deploy countryID numarmies");
                    System.out.println("To advance/attack the armies : advance countrynamefrom countynameto numarmies");
                    System.out.println("To airlift the armies : airlift sourcecountryID targetcountryID numarmies");
                    System.out.println("To blockade the armies : blockade countryID");
                    System.out.println("To negotiate with player : negotiate playerID");
                    System.out.println("To bomb the country : bomb countryID");
                    System.out.println("To skip: pass");
                    System.out.println("Please enter the correct command");
                    System.out.println("=============================================================================");
                    Commands = ReadFromPlayer();
                    l_IssueCommand = ValidateCommand(Commands, l_Player);
                    if (Commands.equals("pass")) {
                        break;
                    }
                }
                if (!Commands.equals("pass")) {
                    l_Player.issueOrder();
                    System.out.println("The order has been had to the list of orders.");
                    System.out.println("=============================================================================");
                }
            }
        }
        SkippedPlayers.clear();
        return p_GamePhase.nextState(d_NextGamePhase);
    }

    /**
     * A function to read all the commands from player
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
     * @return true if the command is correct else false
     */
    public static boolean ValidateCommand(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");
        String[] l_CommandArr = p_CommandArr.split(" ");
        if (p_CommandArr.toLowerCase().contains("pass")) {
            AddToSetOfPlayers(p_Player);
            return false;
        }
        if (!l_Commands.contains(l_CommandArr[0].toLowerCase())) {
            System.out.println("The command syntax is invalid.");
            return false;
        }
        if (!CheckLengthOfCommand(l_CommandArr[0], l_CommandArr.length)) {
            System.out.println("The command syntax is invalid.");
            return false;
        }
        switch (l_CommandArr[0].toLowerCase()) {
            case "deploy":
                try {
                    Integer.parseInt(l_CommandArr[2]);
                } catch (NumberFormatException l_Exception) {
                    System.out.println("The number format is invalid");
                    return false;
                }
                break;
            case "advance":
                if (l_CommandArr.length < 4) {
                    System.out.println("The command syntax is invalid.");
                    return false;
                }
                try {
                    Integer.parseInt(l_CommandArr[3]);
                } catch (NumberFormatException l_Exception) {
                    System.out.println("The number format is invalid");
                    return false;
                }

            default:
                break;

        }
        return true;
    }

    /**
     * A function to map the players and their status for the issuing of the order
     *
     * @param p_Player The player who has skipped his iteration for the issuing
     */
    private static void AddToSetOfPlayers(Player p_Player) {
        SkippedPlayers.add(p_Player);
    }

    /**
     * A function to check the length of each command
     *
     * @param p_Command the command to be validated
     * @return true if length is correct else false
     */
    private static boolean CheckLengthOfCommand(String p_Command, int p_Length) {
        if (p_Command.contains("deploy")) {
            return p_Length == 3;
        } else if (p_Command.contains("bomb") || p_Command.contains("blockade") || p_Command.contains("negotiate")) {
            return (p_Length == 2);
        } else if (p_Command.contains("airlift") || p_Command.contains("advance")) {
            return (p_Length == 4);
        }
        return false;
    }

}



