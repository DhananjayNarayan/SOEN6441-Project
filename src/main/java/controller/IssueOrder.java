package controller;

import model.*;
import model.order.Order;
import utils.GameProgress;
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
    GamePhase d_ExecutePhase = GamePhase.ExecuteOrder;
    /**
     * GamePhase Instance with next phase
     */
    GamePhase d_MapEditorPhase = GamePhase.MapEditor;
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
        if (d_GameMap.getCurrentPlayer() == null) {
            d_GameMap.setCurrentPlayer(d_GameMap.getPlayers().entrySet().iterator().next().getValue());
        }
        d_GamePhase = p_GamePhase;
        while (!(SkippedPlayers.size() == d_GameMap.getPlayers().size())) {
            for (Player l_Player : d_GameMap.getPlayers().values()) {
                if ((d_GameMap.getGameLoaded() && !(l_Player.getName().equalsIgnoreCase(d_GameMap.getCurrentPlayer().getName())))) {
                    continue;
                }
                if (!SkippedPlayers.isEmpty() && SkippedPlayers.contains(l_Player)) {
                    continue;
                }
                d_GameMap.setGameLoaded(false);
                d_GameMap.setCurrentPlayer(l_Player);
                boolean l_IssueCommand = false;
                while (!l_IssueCommand) {
                    showStatus(l_Player);
                    Commands = l_Player.readFromPlayer();
                    if (Objects.isNull(Commands)) {
                        Commands = "";
                    }
                    if (!Commands.isEmpty()) {
                        l_IssueCommand = validateCommand(Commands, l_Player);
                    }
                    if (Commands.equals("pass")) {
                        break;
                    }
                    if (Commands.split(" ")[0].equals("savegame") && l_IssueCommand) {
                        d_GameMap.setGamePhase(d_MapEditorPhase);
                        return d_MapEditorPhase;
                    }
                }
                if (!Commands.equals("pass")) {
                    d_Logger.log(l_Player.getName() + " has issued this order :- " + Commands);
                    l_Player.issueOrder();
                    d_Logger.log("The order has been added to the list of orders.");
                    d_Logger.log("=============================================================================");
                }
            }
            d_GameMap.setGameLoaded(false);
        }
        SkippedPlayers.clear();
        d_GameMap.setGamePhase(d_ExecutePhase);
        return d_ExecutePhase;
    }

    /**
     * A static function to validate the deploy command
     *
     * @param p_CommandArr The string entered by the user
     * @param p_Player     the player object
     * @return true if the command is correct else false
     */
    public boolean validateCommand(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate", "savegame");
        String[] l_CommandArr = p_CommandArr.split(" ");
        if (p_CommandArr.toLowerCase().contains("pass")) {
            AddToSetOfPlayers(p_Player);
            return false;
        }
        if (!l_Commands.contains(l_CommandArr[0].toLowerCase())) {
            d_Logger.log("The command syntax is invalid." + p_CommandArr);
            return false;
        }
        if (!CheckLengthOfCommand(l_CommandArr[0], l_CommandArr.length)) {
            d_Logger.log("The command syntax is invalid." + p_CommandArr);
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
                if(Integer.parseInt(l_CommandArr[2]) < 0){
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
                break;
            case "savegame":
                System.out.println("Are you sure you want to save the file? Enter Yes/No.");
                String l_Input = new Scanner(System.in).nextLine();
                if (l_Input.equalsIgnoreCase("Yes")) {
                    GameProgress.SaveGameProgress(d_GameMap, l_CommandArr[1]);
                    return true;
                } else {
                    d_Logger.log("The game has not been saved, continue to play.");
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
        } else if (p_Command.contains("bomb") || p_Command.contains("blockade") || p_Command.contains("negotiate") || p_Command.contains("savegame")) {
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
        d_Logger.log("List of game loop commands");
        d_Logger.log("To deploy the armies : deploy countryID numarmies");
        d_Logger.log("To advance/attack the armies : advance countrynamefrom countynameto numarmies");
        d_Logger.log("To airlift the armies : airlift sourcecountryID targetcountryID numarmies");
        d_Logger.log("To blockade the armies : blockade countryID");
        d_Logger.log("To negotiate with player : negotiate playerID");
        d_Logger.log("To bomb the country : bomb countryID");
        d_Logger.log("To skip: pass");
        d_Logger.log("=============================================================================");
        String l_Table = "|%-15s|%-19s|%-22s|%n";
        d_Logger.log("Current Player Details Are:\n");
        System.out.format("+--------------+-----------------------+------------------+%n");
        System.out.format("| Player Name   | Initial Assigned  | Left Armies          | %n");
        System.out.format("+---------------+------------------  +---------------------+%n");
        System.out.format(l_Table, p_Player.getName(), p_Player.getReinforcementArmies(), p_Player.getIssuedArmies());
        System.out.format("+--------------+-----------------------+------------------+%n");

        d_Logger.log("The countries assigned to the player are: ");
        System.out.format("+--------------+-----------------------+------------------+---------+%n");

        System.out.format(
                "|Country name  |Country Armies  | Neighbors                         |%n");
        System.out.format(
                "+--------------+-----------------------+------------------+---------+%n");
        for (Country l_Country : p_Player.getCapturedCountries()) {
            String l_TableCountry = "|%-15s|%-15s|%-35s|%n";
            String l_NeighborList = "";
            for (Country l_Neighbor : l_Country.getNeighbors()) {
                l_NeighborList += l_Neighbor.getName() + "-";
            }
            System.out.format(l_TableCountry, l_Country.getName(), l_Country.getArmies(), l_Country.createANeighborList(l_Country.getNeighbors()));
        }
        System.out.format("+--------------+-----------------------+------------------+---------+\n");

        if (!p_Player.getPlayerCards().isEmpty()) {
            d_Logger.log("The Cards assigned to the Player are: ");
            for (Card l_Card : p_Player.getPlayerCards()) {
                d_Logger.log(l_Card.getCardType().toString());
            }
        }
        if (!p_Player.getOrders().isEmpty()) {
            d_Logger.log("The Orders issued by Player " + p_Player.getName() + " are:");
            for (Order l_Order : p_Player.getOrders()) {
                d_Logger.log(l_Order.getOrderInfo().getCommand());
            }
        }
        d_Logger.log("=================================================================================");
    }
}



