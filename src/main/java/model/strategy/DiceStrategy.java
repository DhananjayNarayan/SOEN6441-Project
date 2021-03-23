package model.strategy;

import model.Country;
import model.GameSettings;
import model.Player;

import java.util.stream.IntStream;

public class DiceStrategy implements GameStrategy {

    GameSettings SETTINGS = GameSettings.getInstance();

    @Override
    public boolean attack(Player p_player, Country p_from, Country p_to, int p_armies) {
        p_from.depleteArmies(p_armies);
        int l_attackerKills = (int) IntStream.range(0, p_armies).boxed().filter((p_integer) -> Math.random() <= SETTINGS.ATTACKER_PROBABILITY).count();
        int l_defenderKills = (int) IntStream.range(0, p_to.getArmies()).boxed().filter(p_integer -> Math.random() <= SETTINGS.DEFENDER_PROBABILITY).count();

        int l_armiesLeftAttacker = p_armies - l_defenderKills;
        int l_armiesLeftDefender = p_to.getArmies() - l_attackerKills;
        if (l_armiesLeftAttacker > 0 && l_armiesLeftDefender <= 0) {
            p_to.setArmies(l_armiesLeftAttacker);
            makeMeKing(p_player, p_to);
            System.out.println("Attacker : " + p_player.getName() + " won.");
            System.out.println("Remaining attacker's armies " + p_to.getArmies() + " moved from " + p_from.getName() + " to " + p_to.getName() + ".");
            return true;
        } else {
            p_from.deployArmies(l_armiesLeftAttacker);
            p_to.setArmies(l_armiesLeftDefender);
            System.out.println("Attacker : " + p_player.getName() + " lost.");
            System.out.println("Remaining attacker's armies: " + p_from.getArmies());
            System.out.println("Remaining defender's armies: " + p_to.getArmies());
        }
        return false;
    }

}
