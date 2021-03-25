package model.strategy;

import model.Card;
import model.Country;
import model.GameSettings;
import model.Player;
import utils.logger.LogEntryBuffer;

import java.util.stream.IntStream;

/**
 * Class holding the Dice strategy for advance logic
 *
 * @author Madhuvanthi Hemanathan
 */
public class DiceStrategy implements GameStrategy {
    /**
     * Game settings object
     */
    GameSettings SETTINGS = GameSettings.getInstance();

    /**
     * Logger for game a actions
     */
    LogEntryBuffer d_LogEntryBuffer = new LogEntryBuffer();

    /**
     * Method holding the default attack logic
     *
     * @param p_Player The player who initiated attack
     * @param p_From   The country from which the attack is initiated
     * @param p_To     The country on which the attack is going to happen
     * @param p_Armies The number of armies to be moved
     * @return true on successful execution else false
     */
    @Override
    public boolean attack(Player p_Player, Country p_From, Country p_To, int p_Armies) {
        try{
            p_From.depleteArmies(p_Armies);
            int l_AttackerKills = (int) IntStream.range(0, p_Armies).boxed().filter((p_integer) -> Math.random() <= SETTINGS.ATTACKER_PROBABILITY).count();
            int l_DefenderKills = (int) IntStream.range(0, p_To.getArmies()).boxed().filter(p_integer -> Math.random() <= SETTINGS.DEFENDER_PROBABILITY).count();

            int l_ArmiesLeftAttacker = p_Armies - l_DefenderKills;
            int l_ArmiesLeftDefender = p_To.getArmies() - l_AttackerKills;
            if (l_ArmiesLeftAttacker > 0 && l_ArmiesLeftDefender <= 0) {
                p_To.setArmies(l_ArmiesLeftAttacker);
                makeMeKing(p_Player, p_To);
                Card l_AssignedCard = new Card();
                p_Player.addPlayerCard(l_AssignedCard);
                System.out.println("Attacker: " + p_Player.getName() + " received a card: " + l_AssignedCard);
                d_LogEntryBuffer.logInfo("Attacker: " + p_Player.getName() + " received a card: "+ l_AssignedCard);
                System.out.println("Attacker : " + p_Player.getName() + " won.");
                System.out.println("Remaining attacker's armies " + p_To.getArmies() + " moved from " + p_From.getName() + " to " + p_To.getName() + ".");

            } else {
                p_From.deployArmies(l_ArmiesLeftAttacker);
                p_To.setArmies(l_ArmiesLeftDefender);
                System.out.println("Attacker : " + p_Player.getName() + " lost.");
                System.out.println("Remaining attacker's armies: " + p_From.getArmies());
                System.out.println("Remaining defender's armies: " + p_To.getArmies());
            }
            return true;
        } catch (Exception p_Exception) {
            return false;
        }

    }

}
