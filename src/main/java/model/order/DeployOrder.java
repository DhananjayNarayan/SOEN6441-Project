package model.order;

import model.Country;
import model.Player;
import utils.LogEntryBuffer;

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
        Country l_Destination = getOrderInfo().getDestination();
        int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("The order: " + getType() + " " + getOrderInfo().getDestination().getName() + " " + getOrderInfo().getNumberOfArmy());
        if (validateCommand()) {
            l_Destination.deployArmies(l_ArmiesToDeploy);
            return true;
        }
        return false;
    }

    /**
     * A function to validate the commands
     *
     * @return true if command can be executed else false
     */
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Destination = getOrderInfo().getDestination();
        int l_Reinforcements = getOrderInfo().getNumberOfArmy();
        if (l_Player == null || l_Destination == null) {
            System.out.println("Invalid order information.");
            return false;
        }
        if (!l_Player.isCaptured(l_Destination)) {
            System.out.println("The country does not belong to you");
            return false;
        }
        if (!l_Player.deployReinforcementArmiesFromPlayer(l_Reinforcements)) {
            System.out.println("You do not have enough Reinforcement Armies to deploy.");
            return false;
        }
        return true;
    }

    /**
     * A function to print the order on completion
     */
    public void printOrderCommand() {
        System.out.println("Deployed " + getOrderInfo().getNumberOfArmy() + " armies to " + getOrderInfo().getDestination().getName() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_leb.logInfo("Deployed " + getOrderInfo().getNumberOfArmy() + " armies to " + getOrderInfo().getDestination().getName() + ".");
    }

}
