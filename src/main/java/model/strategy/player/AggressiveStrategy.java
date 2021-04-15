package model.strategy.player;

import controller.IssueOrder;
import model.Card;
import model.CardType;
import model.Country;
import model.GameMap;
import model.order.*;
import utils.logger.LogEntryBuffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A class to implement the Aggressive strategy for a player
 *
 * @author Dhananjay Narayan
 * @author Madhuvanthi Hemanathan
 */
public class AggressiveStrategy extends PlayerStrategy implements Serializable {
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Ordered list based on number of armies
     */
    private List<Country> orderedList;


    /**
     * A function to create the commands for deploying, advancing and bombing for an Aggressive player
     *
     * @return null if empty
     */
    public String createCommand() {
        d_Player = GameMap.getInstance().getCurrentPlayer();
        d_Logger.log("Issuing Orders for the Aggressive Player - " + d_Player.getName());
        if (d_Player.getCapturedCountries().size() > 0) {
            createAndOrderCountryList();
            deployCommand();
            if (bombOrAttack()) {
                return "pass";
            }
            moveToSelf();
        }
        return "pass";
    }

    /**
     * Create a list of sorted countries with respect to their army strength
     */
    private void createAndOrderCountryList() {
        orderedList = d_Player.getCapturedCountries()
                .stream()
                .sorted(Comparator.comparingInt(Country::getArmies).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Generates deploy command
     *
     * @return true/false
     */
    public boolean deployCommand() {
        List<String> l_Commands = new ArrayList<>();
        Country l_StrongCountry = orderedList.get(0);
        int l_armiesReinforce = d_Player.getReinforcementArmies();
        // Deploy armies to strongestCountry
        l_Commands.add(0, "deploy");
        l_Commands.add(1, l_StrongCountry.getName());
        l_Commands.add(2, String.valueOf((l_armiesReinforce)));
        String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
        Order l_Order = new DeployOrder();
        l_Order.setOrderInfo(OrderCreater.GenerateDeployOrderInfo(l_CommandsArr, d_Player));
        IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
        d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
        d_Player.issueOrder();
        return true;
    }

    /**
     * Bomb an enemy if bomb card exists else attack the enemy with strongest country
     *
     * @return true/false
     */
    public boolean bombOrAttack() {
        boolean flag = false;
        for (Card playerCard : d_Player.getPlayerCards()) {
            if (playerCard.getCardType().equals(CardType.BOMB)) {
                flag = true;
                break;
            }
        }
        
        Country fromCountry = null;
        Country toCountry = null;
        for (Country l_Country : orderedList) {
            fromCountry = l_Country;
            toCountry = fromCountry.getNeighbors()
                    .stream()
                    .filter(country -> !d_Player.getName().equals(country.getPlayer().getName()))
                    .findFirst().orElse(null);
        }

        if (Objects.nonNull(fromCountry) && Objects.nonNull(toCountry)) {
            List<String> l_Commands = new ArrayList<>();
            if (flag) {
                l_Commands.add(0, "bomb");
                l_Commands.add(1, toCountry.getName());
                String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                Order l_Order = new BombOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateBombOrderInfo(l_CommandsArr, d_Player));
                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
                d_Player.issueOrder();
                return true;
            } else if (fromCountry.getArmies() > 0) {
                l_Commands.add(0, "advance");
                l_Commands.add(1, fromCountry.getName());
                l_Commands.add(2, toCountry.getName());
                l_Commands.add(3, String.valueOf(fromCountry.getArmies()));
                String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                Order l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
                d_Player.issueOrder();
                return true;
            }
        }
        return false;
    }


    /**
     * If enemy doesnt exist to the strongest country
     * Move armies to the next strongest country that has an enemy
     *
     * @return true/false
     */
    private boolean moveToSelf() {
        Country fromCountry = orderedList.get(0);
        if (fromCountry.getArmies() <= 0) {
            return false;
        }
        List<Country> l_NeighborsWithEnemies = getNeighborsWithEnemies(fromCountry);
        Country toCountry = l_NeighborsWithEnemies.stream().max(Comparator.comparingInt(Country::getArmies)).orElse(null);
        if (Objects.nonNull(fromCountry) && Objects.nonNull(toCountry)) {
            List<String> l_Commands = new ArrayList<>();
            l_Commands.add(0, "advance");
            l_Commands.add(1, fromCountry.getName());
            l_Commands.add(2, toCountry.getName());
            l_Commands.add(3, String.valueOf(fromCountry.getArmies()));
            String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
            Order l_Order = new AdvanceOrder();
            l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
            IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
            d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
            d_Player.issueOrder();
            return true;
        }
        return false;
    }

    private List<Country> getNeighborsWithEnemies(Country p_FromCountry) {
        return p_FromCountry.getNeighbors().stream()
                .takeWhile(country -> {
                    Long count = country.getNeighbors().stream()
                            .filter(country1 -> !country.getPlayer().getName().equals(country1.getPlayer().getName()))
                            .count();
                    if (count > 0) {
                        return true;
                    }
                    return false;
                }).collect(Collectors.toList());
    }

}
