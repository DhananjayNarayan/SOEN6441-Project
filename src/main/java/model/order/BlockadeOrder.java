package model.order;
import model.GameMap;
import utils.LogEntryBuffer;
import model.Country;
import model.Player;



/**
 * This class helps in executing the Blokcade Card
 * @author Dhananjay
 *
 */
public class BlockadeOrder extends Order {
    private GameMap d_GameMap;
    LogEntryBuffer d_leb = new LogEntryBuffer();

    /**
     * Constructor for class Blockade Order
     */
    public BlockadeOrder() {
        super();
        setType("blockade");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Execute the Blockade Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();
        if (validateCommand()) {
            l_Country.setArmies(l_Country.getArmies() * 3);
            l_Player.getCapturedCountries().remove(l_Country);
        }
        System.out.println("The order: " + getType() + " " + l_Country);
        d_leb.logInfo("Blockade on" + getOrderInfo().getTargetCountry() + " by " + getOrderInfo().getPlayer());

        return false;
    }

    /**
     * Validate the command
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_Country = getOrderInfo().getTargetCountry();

        if (l_Player == null) {
            System.out.println("The Player is not valid.");
            return false;
        }

        if (l_Country.getPlayer() != l_Player) {
            System.out.println("The target country does not belong to the player");
            return false;
        }
        if (!l_Player.getPlayerCards().contains(getType())) {
            System.out.println("Player doesn't have Blockade Card.");
            return false;
        }
        return true;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Blockade on " + getOrderInfo().getTargetCountry().getName() + " by " + getOrderInfo().getPlayer());
        System.out.println("---------------------------------------------------------------------------------------------");

    }
}