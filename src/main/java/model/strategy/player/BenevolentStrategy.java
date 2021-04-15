package model.strategy.player;

import controller.IssueOrder;
import model.*;
import model.order.*;
import utils.logger.LogEntryBuffer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Class that implements the Benevolent Player Strategy
 *
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class BenevolentStrategy extends PlayerStrategy implements Serializable {

    /**
     * a random number
     */
    private static final Random d_Random = new Random();
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    /**
     * An instance of gamemap object
     */
    private static GameMap d_GameMap;


    /**
     * A function to determine the weakest country from the list of captured countries
     *
     * @param p_Player player object
     * @return The weakest country
     */
    public Country getWeakestConqueredCountry(Player p_Player) {
        List<Country> l_CountryList = p_Player.getCapturedCountries();
        if(l_CountryList.size()>0){
            Country l_WeakestCountry = l_CountryList.get(0);
            for (Country l_Country : l_CountryList) {
                if (l_Country.getArmies() < l_WeakestCountry.getArmies())
                    l_WeakestCountry = l_Country;
            }
            return l_WeakestCountry;
        }
        return null;
    }

    /**
     * A function to create the commands for deploying, negotiating and advancing for a Benevolent player
     *
     * @return null if empty
     */
    public String createCommand() {
        d_GameMap = GameMap.getInstance();
        d_Player = d_GameMap.getCurrentPlayer();
        d_Logger.log("Issuing Orders for the Benevolent Player - " + d_Player.getName());
        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;
        Country l_WeakestCountry = getWeakestConqueredCountry(d_Player);
        if(Objects.isNull(l_WeakestCountry)) {
            return "pass";
        }
        int l_ArmiesReinforce = d_Player.getReinforcementArmies();

        // Deploy armies to weakest Country
        l_Commands.add(0, "deploy");
        l_Commands.add(1, l_WeakestCountry.getName());
        l_Commands.add(2, String.valueOf((l_ArmiesReinforce)));
        l_CommandsArr = l_Commands.toArray(new String[0]);
        l_Order = new DeployOrder();
        l_Order.setOrderInfo(OrderCreater.GenerateDeployOrderInfo(l_CommandsArr, d_Player));
        IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
        d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
        d_Player.issueOrder();

        //if Player has a diplomacy card,then use it
        if (d_Player.getPlayerCards().size() > 0) {
            for (Card l_Card : d_Player.getPlayerCards()) {
                if (l_Card.getCardType() == CardType.DIPLOMACY) {
                    Player l_RandomPlayer = getRandomPlayer(d_Player);
                    if (Objects.nonNull(l_RandomPlayer)) {
                        l_Commands = new ArrayList<>();
                        l_Commands.add(0, "negotiate");
                        l_Commands.add(1, l_RandomPlayer.getName());
                        l_CommandsArr = l_Commands.toArray(new String[0]);
                        l_Order = new NegotiateOrder();
                        l_Order.setOrderInfo(OrderCreater.GenerateNegotiateOrderInfo(l_CommandsArr, d_Player));
                        IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
                        d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
                        d_Player.issueOrder();
                        return "pass";
                    }
                }
            }
        }

// move armies to the weakest country from the other neighbouring countries of the same player
        for (Country l_Country : l_WeakestCountry.getNeighbors()) {
            if (l_Country.getPlayer().getName().equals(d_Player.getName())) {
                l_Commands = new ArrayList<>();
                l_Commands.add(0, "advance");
                l_Commands.add(1, l_Country.getName());
                l_Commands.add(2, l_WeakestCountry.getName());
                l_Commands.add(3, String.valueOf(l_Country.getArmies()));
                l_CommandsArr = l_Commands.toArray(new String[0]);
                l_Order = new AdvanceOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, d_Player));
                IssueOrder.Commands = l_Order.getOrderInfo().getCommand();
                d_Player.issueOrder();
                d_Logger.log(String.format("%s issuing new command: %s", d_Player.getName(), IssueOrder.Commands));
                l_WeakestCountry = l_Country;
            }
        }
        return "pass";
    }

    /**
     * Get a random player other than itself
     *
     * @param p_Player current player
     * @return Random Player
     */
    protected Player getRandomPlayer(Player p_Player) {
        List<Country> l_Enemies = d_Player.getCapturedCountries().stream()
                .flatMap(country -> country.getNeighbors().stream())
                .filter(country -> !country.getPlayer().getName().equals(d_Player.getName()))
                .collect(Collectors.toList());
        if (l_Enemies.size() > 0) {
            int l_Random = d_Random.nextInt(l_Enemies.size());
            return l_Enemies.get(l_Random).getPlayer();
        }
        return null;
    }
}
