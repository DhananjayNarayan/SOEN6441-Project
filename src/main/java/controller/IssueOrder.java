package controller;
import model.*;
import java.util.*;

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
    private final static Scanner Scanner = new Scanner(System.in);
    private static Map<Player, Boolean> PlayerMap = new HashMap<>();
    public static String Commands = null;
    
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
            while (!(PlayerMap.size() == d_GameMap.getPlayers().size())) {
                for (Player l_Player : d_GameMap.getPlayers().values()) {
                    if(!PlayerMap.isEmpty() && PlayerMap.containsKey(l_Player)){
                        continue;
                    }
                    System.out.println("Player:" + l_Player.getName() + "; Armies assigned are: " + l_Player.getReinforcementArmies());
                    System.out.println("The countries to be assigned to the player are: ");
                    //show the cards available
                    for(Country l_Country : l_Player.getCapturedCountries() ){
                        System.out.println(l_Country.getName() + " ");
                    }
                    System.out.println("=================================================================================");
                    boolean l_IssueCommand = false;
                    while (!l_IssueCommand) {
                        System.out.println("List of game loop commands");
                        System.out.println("To deploy the armies : deploy countryID armies");
                        System.out.println("To skip: pass");
                        System.out.println("Please enter the correct command");
                        System.out.println("=============================================================================");
                        Commands = ReadFromPlayer();
                        l_IssueCommand = ValidateCommand(Commands, l_Player);
                        if(Commands.equals("pass")) {
                            break;
                        }
                    }
                    if(!Commands.equals("pass")) {
                        l_Player.issueOrder();
                        System.out.println("The order has been had to the list of orders.");
                        System.out.println("=============================================================================");
                    }
                }
            }
            return p_GamePhase.nextState(d_NextGamePhase);
        }

    /**
     *  A function to read all the commands from player
     *
     * @return command entered by the player
     */
    public static String ReadFromPlayer() {
            return Scanner.nextLine();
    }

    /**
     * A static function to validate the deploy command
     *
     * @param p_CommandArr The string entered by the user
     * @return true if the command is correct else false
     */
    public static  boolean ValidateCommand(String p_CommandArr, Player p_Player) {
        List<String> l_Commands = Arrays.asList("deploy", "advance", "bomb", "blockade", "airlift", "negotiate");
        String[] l_CommandArr = p_CommandArr.split(" ");
        if(p_CommandArr.toLowerCase().contains("pass")){
            AddToSetOfPlayers(p_Player);
            return false;
        }
        if(l_CommandArr.length <=2){
            System.out.println("The command syntax is invalid.");
            return false;
        }
        if(!l_Commands.contains(l_CommandArr[0].toLowerCase())){
            System.out.println("The command syntax is invalid.");
            return false;
        }
        if(!CheckLengthOfCommand(l_CommandArr[0], l_CommandArr.length)){
            System.out.println("The command syntax is invalid.");
            return false;
        }
        switch (l_CommandArr[0].toLowerCase()){
            case "deploy":
                try{
                    Integer.parseInt(l_CommandArr[2]);
                }
                catch (NumberFormatException l_Exception){
                    System.out.println("The number format is invalid");
                    return false;
                }
                break;
            default: break;

        }
        return true;
    }

    /**
     * A function to map the players and their status for the issuing of the order
     *
     * @param p_Player The player who has skipped his iteration for the issuing
     */
    private static void AddToSetOfPlayers(Player p_Player){
        PlayerMap.put(p_Player, true);
    }
    
    /**
     * A function to check the length of each command
     *
     * @param p_Command the command to be validated
     * @return true if length is correct else false
     */
    private static boolean CheckLengthOfCommand(String p_Command, int p_Length){
        if(p_Command.contains("deploy") || p_Command.contains("advance")){
            return p_Length == 3;
        }
        else if(p_Command.contains("bomb") || p_Command.contains("blockade") || p_Command.contains("negotiate")){
            return (p_Length == 2);
        }
        else if(p_Command.contains("airlift")){
            return (p_Length == 4);
        }
        return false;
    }

}



