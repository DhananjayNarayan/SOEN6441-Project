package model.order;

import model.Country;
import model.GameSettings;
import model.Player;
import model.strategy.GameStrategy;
import utils.logger.LogEntryBuffer;

import java.util.Objects;

/**
 * Class holding the properties of advance order
 *
 * @author Madhuvanthi Hemanathan
 */
public class AdvanceOrder extends Order {

    LogEntryBuffer d_Leb = new LogEntryBuffer();
    GameSettings d_Settings = GameSettings.getInstance();
    GameStrategy d_GameStrategy;

    /**
     * Constructor for class AdvanceOrder
     */
    public AdvanceOrder() {
        super();
        setType("advance");
        d_GameStrategy = d_Settings.getStrategy();
    }

    /**
     * Advance the number of armies from a source country to destination country
     * Advance if self conquered
     * Advance if destination does not belong to any player.
     * Attack if a country belongs to enemy player.
     * Skips the attack command if player exists in NeutralPlayers list.
     * Remove the neutral player from the list once the attack is negotiated.
     * The attack happens in the next turn of the same phase on same player if the
     * negotiation happened already once in the phase.
     *
     * @return true if command is successfully executed or skipped else false
     */
    @Override
    public boolean execute() {
        if (validateCommand()) {
            Player l_Player = getOrderInfo().getPlayer();
            Country l_From = getOrderInfo().getDeparture();
            Country l_To = getOrderInfo().getDestination();
            int l_Armies = getOrderInfo().getNumberOfArmy();
            if (l_Player.getNeutralPlayers().contains(l_To.getPlayer())) {
                System.out.printf("Truce between %s and %s\n", l_Player.getName(), l_To.getPlayer().getName());
                l_Player.getNeutralPlayers().remove(l_To.getPlayer());
                l_To.getPlayer().getNeutralPlayers().remove(l_Player);
                return true;
            }
            if (l_Player.isCaptured(l_To) || Objects.isNull(l_To.getPlayer())) {
                l_From.depleteArmies(l_Armies);
                l_To.deployArmies(l_Armies);
                if (!l_Player.getCapturedCountries().contains(l_To)) {
                    l_Player.getCapturedCountries().add(l_To);
                }
                l_To.setPlayer(l_Player);
                System.out.println("Advanced/Moved " + l_Armies + " from " + l_From.getName() + " to " + l_To.getName());
                d_Leb.logInfo("Advanced/Moved " + l_Armies + " from " + l_From.getName() + " to " + l_To.getName());
                return true;
            } else if (d_GameStrategy.attack(l_Player, l_From, l_To, l_Armies)) {
                return true;
            }
        }
        System.out.println("---------------------------------------------------");
        return false;
    }


    /**
     * Validate command
     *
     * @return Returns true if valid command else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_From = getOrderInfo().getDeparture();
        Country l_To = getOrderInfo().getDestination();
        int l_Armies = getOrderInfo().getNumberOfArmy();
        boolean success = true;
        String log = "Failed due to some errors\n";
        if (l_Player == null || l_To == null || l_From == null) {
            System.err.println("Invalid order information.");
            return false;
        }
        if (!l_Player.isCaptured(l_From)) {
            log += String.format("\t-> Country %s does not belong to player %s\n", l_From.getName(), l_Player.getName());
            success = false;
        }
        if (!l_From.isNeighbor(l_To)) {
            log += String.format("\t-> Destination country %s is not a neighbor of %s\n", l_To.getName(), l_From.getName());
            success = false;
        }
        if (l_Armies > l_From.getArmies()) {
            log += String.format("\t-> Attacking troop count %d is greater than available troops %d", l_Armies, l_From.getArmies());
            success = false;
        }
        if (!success) {
            System.err.println(log);
            d_Leb.logInfo(log);
        }
        return success;
    }

    /**
     * Print the command
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Advanced " + getOrderInfo().getNumberOfArmy() + " armies " + " from " + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_Leb.logInfo("Advanced " + getOrderInfo().getNumberOfArmy() + " armies " + " from " + getOrderInfo().getDeparture().getName() + " to " + getOrderInfo().getDestination().getName() + ".");
    }
}
