package controller;

import model.*;
import utils.InvalidExecutionException;
import utils.ValidationException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for {@code Reinforcement} phase of game.
 *
 * @author Prathika Suvarna
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class Reinforcement implements GameController {
    /**
     * Data Member holding next phase of game
     */
    GamePhase d_NextGamePhase = GamePhase.IssueOrder;
    /**
     * Data Member holding current phase of game
     */
    GamePhase d_GamePhase;
    /**
     * Data Member for Game map
     */
    GameMap d_GameMap;

    /**
     * Data Member for Current Player
     */
    Player d_CurrentPlayer;

    /**
     * Default constructor initialising the Game map data member with
     * {@code GameMap} singleton object
     */
    public Reinforcement() {
        d_GameMap = GameMap.getInstance();
    }

    /**
     * Beginner method of the Reinforcement phase
     *
     * @param p_GamePhase holding the current game phase
     * @return Next game phase upon successful execution
     * @throws ValidationException       upon invalid input or output
     * @throws InvalidExecutionException upon invalid game phase command
     */
    @Override
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException, InvalidExecutionException {
        d_GamePhase = p_GamePhase;
        calculateReinforcements();
        return d_NextGamePhase;
    }

    /**
     * Method to calculate and set reinforcement armies for each player.
     *
     * @throws InvalidExecutionException upon invalid game phase command
     */
    public void calculateReinforcements() throws InvalidExecutionException {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            d_CurrentPlayer = l_Player;
            setReinforcementTroops();
        }
    }

    /**
     * Game Logic to calculate reinforcement armies for each player based on
     * number of countries captured.
     *
     * @throws InvalidExecutionException upon invalid game phase command
     */
    public void setReinforcementTroops() throws InvalidExecutionException {
        if (d_GamePhase.equals(GamePhase.Reinforcement)) {
            if (d_CurrentPlayer.getCapturedCountries().size() > 0) {
                int reinforcements = (int) Math.floor(d_CurrentPlayer.getCapturedCountries().size() / 3f);
                Map<String, List<Country>> l_CountryMap = d_CurrentPlayer.getCapturedCountries()
                        .stream()
                        .collect(Collectors.groupingBy(Country::getContinent));
                for (String continent : l_CountryMap.keySet()) {
                    if (d_GameMap.getContinent(continent).getCountries().size() == l_CountryMap.get(continent).size()) {
                        reinforcements += d_GameMap.getContinent(continent).getAwardArmies();
                    }
                }
                d_CurrentPlayer.setReinforcementArmies(reinforcements > 2 ? reinforcements : 3);
                System.out.println("The Player:" + d_CurrentPlayer.getName() + " is assigned with " + d_CurrentPlayer.getReinforcementArmies() + " armies.");
            } else {
                d_CurrentPlayer.setReinforcementArmies(3);
                System.out.println("The Player:" + d_CurrentPlayer.getName() + " is assigned with " + d_CurrentPlayer.getReinforcementArmies() + " armies.");
            }

        } else throw new InvalidExecutionException();
    }
}
