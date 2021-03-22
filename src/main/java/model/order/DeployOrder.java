package model.order;

import model.Country;
import model.Player;
import utils.LogEntryBuffer;

import java.util.List;

/**
 * Class DeployOrder which is a child of Order, used to execute the orders
 *
 * @author Prathika Suvarna
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class DeployOrder extends Order {
    LogEntryBuffer d_leb = new LogEntryBuffer();
    /**
     * Constructor for class DeployOrder
     */
    public DeployOrder() {
        super();
        setType("deploy");
    }

    /**
     * Overriding the execute function for the order type deploy
     *
     * @return true if the execution was successful else return false
     */
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        String l_Destination = getOrderInfo().getDestination();
        int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("The order: " + getType() + " " + getOrderInfo().getDestination() + " " + getOrderInfo().getNumberOfArmy());
        if(validateCommand()){
            for(Country l_Country : l_Player.getCapturedCountries()){
                if(l_Country.getName().equals(l_Destination)){
                    l_Country.deployArmies(l_ArmiesToDeploy);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * A function to validate the commands
     *
     * @return true if command can be executed else false
     */
    public boolean validateCommand(){
        Player l_Player = getOrderInfo().getPlayer();
        String l_Destination = getOrderInfo().getDestination();
        int l_Reinforcements = getOrderInfo().getNumberOfArmy();
        if (l_Player == null || l_Destination == null) {
            System.out.println("Invalid order information.");
            return false;
        }
        if (checkIfCountryExists(l_Destination,l_Player)) {
            System.out.println("The country does not belong to you");
            return false;
        }
        if (!l_Player.deployReinforcementArmiesFromPlayer(l_Reinforcements)) {
            System.out.println("You do not have enough Reinforcement Armies to deploy.");
            return false;
        }
        return  true;
    }
    /**
     * A function to check if the country exists in the list of player assigned countries
     *
     * @param p_Country The country to be checked if present
     * @param p_Player  The Player for whom the function is checked for
     * @return true if country exists in the assigned country list else false
     */
    private boolean checkIfCountryExists(String p_Country, Player p_Player) {
        List<Country> l_ListOfCountries = p_Player.getCapturedCountries();
        for (Country l_Country : l_ListOfCountries) {
            if (l_Country.getName().equals(p_Country)) {
                return true;
            }
        }
        return false;
    }

    /**
     * A function to print the order on completion
     */
    public void printOrderCommand(){
        System.out.println("Deployed " + getOrderInfo().getNumberOfArmy() + " armies to " + getOrderInfo().getDestination() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_leb.logInfo("Deployed " + getOrderInfo().getNumberOfArmy() + " armies to " + getOrderInfo().getDestination() + ".");
    }

}
