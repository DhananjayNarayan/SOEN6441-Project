package model.order;

import model.Country;
import model.Player;

public class DeployOrder extends Order {
    public DeployOrder() {
        super();
        setType("deploy");
    }
    public boolean execute() {
        if (getOrderInfo().getPlayer() == null || getOrderInfo().getDestination() == null) {
            System.out.println("Fail to execute Deploy order: Invalid order information.");
            return false;
        }

        Player l_Player = getOrderInfo().getPlayer();
        String l_Destination = getOrderInfo().getDestination();

        // If the player decides to deploy armies to the country he/she doesn't control, the player will lost the armies.
        if (!l_Player.getCapturedCountries().contains(l_Destination)) {
            System.out.println("\nFail to execute Deploy order: the country " + l_Destination + " is not in the control of player " + l_Player.getName() + ".");
            return false;
        }

        // check if the subtraction manages to execute
        int l_ArmiesToDeploy = getOrderInfo().getNumberOfArmy();
        if (!l_Player.deployReinforcementArmiesFromPlayer(getOrderInfo().getNumberOfArmy())) {
            System.out.println("\nFail to execute Deploy order: the player " + l_Player.getName() + " doesn't have adequate armies to deploy!\n");
            return false;
        }


        for(Country l_Country : l_Player.getCapturedCountries()){
            if(l_Country.getName().equals(l_Destination)){
                l_Country.deployArmies(l_ArmiesToDeploy);
            }
        }

        System.out.println("\nExecution is completed: deploy " + l_ArmiesToDeploy + " armies to Country " + l_Destination + ".");
        System.out.println("\nArmies left: " + l_Player.getReinforcementArmies());

        return true;
    }

}
