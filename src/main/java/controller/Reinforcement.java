package controller;

import model.*;
import utils.InvalidExecutionException;
import utils.ValidationException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Reinforcement implements GameController {
    GamePhase d_NextGamePhase = GamePhase.IssueOrder;
    GamePhase d_GamePhase;
    GameMap d_GameMap;
    Player d_CurrentPlayer;

    public Reinforcement() {
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws ValidationException, InvalidExecutionException {
        d_GamePhase = p_GamePhase;
        calculateReinforcements();
        return null;
    }

    private void calculateReinforcements() throws InvalidExecutionException {
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            d_CurrentPlayer = l_Player;
            setReinforcementTroops();
        }
    }

    public void setReinforcementTroops() throws InvalidExecutionException {
        if (d_GamePhase.equals(GamePhase.Reinforcement)) {
            int reinforcements = d_CurrentPlayer.getCapturedCountries().size();
            Map<String, List<Country>> l_CountryMap = d_CurrentPlayer.getCapturedCountries()
                    .stream()
                    .collect(Collectors.groupingBy(Country::getContinent));
            for (String continent : l_CountryMap.keySet()) {
                if (d_GameMap.getContinent(continent).getCountries().size() == l_CountryMap.get(continent).size()) {
                    reinforcements += d_GameMap.getContinent(continent).getAwardArmies();
                }
            }
            d_CurrentPlayer.setReinforcementArmies(reinforcements > 2 ? reinforcements : 3);
        } else throw new InvalidExecutionException();
    }
}
