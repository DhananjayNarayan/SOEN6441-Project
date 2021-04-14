package controller;

import model.*;
import model.order.Order;
import utils.logger.LogEntryBuffer;

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
    /**
     * scanner to read from user
     */
    private final static Scanner SCANNER = new Scanner(System.in);
    /**
     * variable to keep track of players who skipped
     */
    private static Set<Player> SkippedPlayers = new HashSet<>();
    /**
     * Static variable to hold commands
     */
    public static String Commands = null;
    /**
     * GamePhase Instance with next phase
     */
    GamePhase d_NextGamePhase = GamePhase.ExecuteOrder;
    /**
     * GamePhase instance
     */
    GamePhase d_GamePhase;
    /**
     * GameMap instance
     */
    GameMap d_GameMap;

    /**
     * Log Entry
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

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
//        d_Logger.log("\nISSUE ORDER PHASE \n");
        while (!(SkippedPlayers.size() == d_GameMap.getPlayers().size())) {
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                if (!SkippedPlayers.isEmpty() && SkippedPlayers.contains(l_Player)) {
                    continue;
                }
                boolean l_IssueCommand = false;
                while (!l_IssueCommand) {
                    d_Logger.log("List of game loop commands");
                    d_Logger.log("To deploy the armies : deploy countryID numarmies");
                    d_Logger.log("To advance/attack the armies : advance countrynamefrom countynameto numarmies");
                    d_Logger.log("To airlift the armies : airlift sourcecountryID targetcountryID numarmies");
                    d_Logger.log("To blockade the armies : blockade countryID");
                    d_Logger.log("To negotiate with player : negotiate playerID");
                    d_Logger.log("To bomb the country : bomb countryID");
                    d_Logger.log("To skip: pass");
                    d_Logger.log("=============================================================================");
                    showStatus(l_Player);
                    Commands = l_Player.readFromPlayer();
                    l_IssueCommand = validateCommand(Commands, l_Player);
                    if (Commands.equals("pass")) {
                        break;
                    }
                }
                if (!Commands.equals("pass")) {
                    d_Logger.log(l_Player.getName() + " has issued this order :- " + Commands);
                    l_Player.issueOrder();
                    d_Logger.log("The order has been added to the list of orders.");
                    d_Logger.log("=============================================================================");
                }
            }
        }
        SkippedPlayers.clear();
        return p_GamePhase.nextState(d_NextGamePhase);
    }

    /**
     * A static function to validate the deploy command
     *
     * @param p_CommandArr The string entered by the user
     * @param p_Player     the player object
     * @return true if the command is correct else false
     */
    public boolean validateCommand(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");
        String[] l_CommandArr = p_CommandArr.split(" ");
        if (p_CommandArr.toLowerCase().contains("pass")) {
            AddToSetOfPlayers(p_Player);
            return false;
        }
        if (!l_Commands.contains(l_CommandArr[0].toLowerCase())) {
            d_Logger.log("The command syntax is invalid.");
            return false;
        }
        if (!CheckLengthOfCommand(l_CommandArr[0], l_CommandArr.length)) {
            d_Logger.log("The command syntax is invalid.");
            return false;
        }
        switch (l_CommandArr[0].toLowerCase()) {
            case "deploy":
                try {
                    Integer.parseInt(l_CommandArr[2]);
                } catch (NumberFormatException l_Exception) {
                    d_Logger.log("The number format is invalid");
                    return false;
                }
                break;
            case "advance":
                try {
                    Integer.parseInt(l_CommandArr[3]);
                } catch (NumberFormatException l_Exception) {
                    d_Logger.log("The number format is invalid");
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

    /**
     * A function to show the player the status while issuing the order
     *
     * @param p_Player The current player object
     */
    public void showStatus(Player p_Player) {
        Player l_Player = p_Player;
        String l_Table = "|%-15s|%-19s|%-22s|%n";
        System.out.println("Current Player Details Are:\n");
        System.out.format("+--------------+-----------------------+------------------+%n");
        System.out.format("| Player Name   | Initial Assigned  | Left Armies          | %n");
        System.out.format("+---------------+------------------  +---------------------+%n");
        System.out.format(l_Table, l_Player.getName(), l_Player.getReinforcementArmies(), l_Player.getIssuedArmies());
        System.out.format("+--------------+-----------------------+------------------+%n");

        d_Logger.log("The countries assigned to the player are: ");
        System.out.format("+--------------+-----------------------+------------------+---------+%n");

        System.out.format(
                "|Country name  |Country Armies  | Neighbors                         |%n");
        System.out.format(
                "+--------------+-----------------------+------------------+---------+%n");
        for (Country l_Country : l_Player.getCapturedCountries()) {

            String tableCountry = "|%-15s|%-15s|%-35s|%n";
            String l_NeighborList = "";
            for (Country l_Neighbor : l_Country.getNeighbors()) {
                l_NeighborList += l_Neighbor.getName() + "-";
            }
            System.out.format(tableCountry, l_Country.getName(), l_Country.getArmies(), l_Country.createANeighborList(l_Country.getNeighbors()));
        }
        System.out.format("+--------------+-----------------------+------------------+---------+\n");

        if (!l_Player.getPlayerCards().isEmpty()) {
            d_Logger.log("The Cards assigned to the Player are: ");
            for (Card l_Card : l_Player.getPlayerCards()) {
                d_Logger.log(l_Card.getCardType().toString());
            }
        }
        if (!l_Player.getOrders().isEmpty()) {
            System.out.println("The Orders issued by Player " + l_Player.getName() + " are:");
            for (Order l_Order : l_Player.getOrders()) {
                System.out.println(l_Order.getOrderInfo().getCommand());
            }
        }
        d_Logger.log("=================================================================================");
    }
}



