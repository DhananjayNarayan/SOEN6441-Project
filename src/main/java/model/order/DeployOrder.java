package model.order;

import model.Country;
import model.Player;

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
        if (getOrderInfo().getPlayer() == null || getOrderInfo().getDestination() == null) {
            System.out.println("Fail to execute Deploy order: Invalid order information.");
            return false;
        }
        Player l_Player = getOrderInfo().getPlayer();
        String l_Destination = getOrderInfo().getDestination();
        int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
        for(Country l_Country : l_Player.getCapturedCountries()){
            if(l_Country.getName().equals(l_Destination)){
                l_Country.deployArmies(l_ArmiesToDeploy);
                System.out.println("The country " + l_Country.getName() + " has been deployed with " + l_Country.getArmies() + " armies.");
            }
        }
        System.out.println("\nExecution is completed: deployed " + l_ArmiesToDeploy + " armies to " + l_Destination + ".");
        System.out.println("=========================================================================================");
        return true;
    }

}
