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
 */
public class AggressiveStrategy extends PlayerStrategy implements Serializable {
    /**
     * An instance of gamemap object
     */
    private static final GameMap d_GameMap = GameMap.getInstance();
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

//        l_Commands = new ArrayList<>();
//
//        //Bomb an opponent Country if it has more than 1 army
//        if (d_Player.getPlayerCards().size() > 0) {
//            for (Card l_Card : d_Player.getPlayerCards()) {
//                if (l_Card.getCardType() == CardType.BOMB) {
//                    for (Country l_C : d_Player.getCapturedCountries()) {
//                        for (Country l_B : l_C.getNeighbors()) {
//                            if (!l_B.getPlayer().equals(d_Player) && l_B.getArmies() > 0) {
//                                l_Commands.add(0, "bomb");
//                                l_Commands.add(1, l_B.getName());
//                                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
//                                l_Order = new BombOrder();
//                                l_Order.setOrderInfo(OrderCreater.GenerateBombOrderInfo(l_CommandsArr, d_Player));
//                                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
//                                d_Player.issueOrder();
//                                return "pass";
//                            }
//                        }
//                    }
//                }
//            }
//        }

    // Attack
//        for (Country l_C : l_StrongCountry.getNeighbors()) {
//            if (!(l_C.getPlayer().equals(d_Player))) {
//                l_Commands.add(0, "advance");
//                l_Commands.add(1, l_StrongCountry.getName());
//                l_Commands.add(2, l_C.getName());
//                l_Commands.add(3, String.valueOf(l_StrongCountry.getArmies()));
//                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
//                l_Order = new AdvanceOrder();
//                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
////                l_StrongCountry = l_C;
//                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
//                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
//                d_Player.issueOrder();
//                return "pass";
////                break; // if no break need to check game is running smooth or add min condition for armies to attack
//            }
//        }

// Moving the armies to other next countries of players. Alternate is to move the armies to a random self owned country if we have to change.
//        for (Country l_C : l_StrongCountry.getNeighbors()) {
//            if (l_C.getPlayer().equals(d_Player)) {
//                l_Commands.add(0, "advance");
//                l_Commands.add(1, l_StrongCountry.getName());
//                l_Commands.add(2, l_C.getName());
//                l_Commands.add(3, String.valueOf(l_StrongCountry.getArmies()));
//                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
//                l_Order = new AdvanceOrder();
//                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
//                l_StrongCountry = l_C;
//                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
//                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
//                d_Player.issueOrder();
//                return "pass";
//            }
//        }
//        return "pass";
//    }


    private void createAndOrderCountryList() {
        orderedList = d_Player.getCapturedCountries()
                .stream()
                .sorted(Comparator.comparingInt(Country::getArmies).reversed())
                .collect(Collectors.toList());
    }

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

    public boolean bombOrAttack() {
        boolean flag = false;
        for (Card playerCard : d_Player.getPlayerCards()) {
            if (playerCard.getCardType().equals(CardType.BOMB)) {
                flag = true;
                break;
            }
        }

        Country fromCountry = orderedList.get(0);
        Country toCountry = fromCountry.getNeighbors()
                .stream()
                .filter(country -> !d_Player.getName().equals(country.getPlayer().getName()))
                .findFirst().orElse(null);

//        AtomicReference<Country> fromCountry = new AtomicReference<>();
//        Country toCountry = orderedList.stream()
//                .filter(country -> Objects.nonNull(country) && country.getArmies() > 1)
//                .peek(fromCountry::set)
//                .flatMap(country -> country.getNeighbors().stream())
//                .filter(country -> !d_Player.getName().equals(country.getPlayer().getName()))
//                .findFirst().orElse(null);

        if (Objects.nonNull(fromCountry) && Objects.nonNull(toCountry)) {
            List<String> l_Commands = new ArrayList<>();
            if (flag) {
                l_Commands.add(0, "bomb");
                l_Commands.add(1, toCountry.getName());
                String[] l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                Order l_Order = new BombOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateBombOrderInfo(l_CommandsArr, d_Player));
                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
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


    private boolean moveToSelf() {
        Country fromCountry = orderedList.get(0);
        if (fromCountry.getArmies() <= 0) {
            return false;
        }
        Country toCountry = null;
        toCountry = fromCountry.getNeighbors().stream().max(Comparator.comparingInt(Country::getArmies)).orElse(null);
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

//        //Bomb an opponent Country if it has more than 1 army
//        if (d_Player.getPlayerCards().size() > 0) {
//            for (Card l_Card : d_Player.getPlayerCards()) {
//                if (l_Card.getCardType() == CardType.BOMB) {
//                    for (Country l_C : d_Player.getCapturedCountries()) {
//                        for (Country l_B : l_C.getNeighbors()) {
//                            if (!l_B.getPlayer().equals(d_Player) && l_B.getArmies() > 0) {
//                                l_Commands.add(0, "bomb");
//                                l_Commands.add(1, l_B.getName());
//                                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
//                                l_Order = new BombOrder();
//                                l_Order.setOrderInfo(OrderCreater.GenerateBombOrderInfo(l_CommandsArr, d_Player));
//                                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
//                                d_Player.issueOrder();
//                                return "pass";
//                            }
//                        }
//                    }
//                }
//            }
//        }

}
