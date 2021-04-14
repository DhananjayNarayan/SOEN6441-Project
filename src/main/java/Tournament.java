import model.GameMap;
import model.GamePhase;
import model.Player;
import model.strategy.player.PlayerStrategy;
import model.tournament.TournamentOptions;
import model.tournament.TournamentResult;
import utils.MapReader;
import utils.MapValidation;
import utils.ValidationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Tournament {

    TournamentOptions d_Options;
    List<TournamentResult> d_Results = new ArrayList<>();
    GameMap d_CurrentMap;

    public Tournament(TournamentOptions p_Options) {
        d_Options = p_Options;
    }

    public void start() throws IllegalAccessException, InvocationTargetException, InstantiationException, ValidationException {
        for (String l_File : d_Options.getMap()) {
            for (int l_game = 0; l_game < d_Options.getGames(); l_game++) {
                d_CurrentMap = GameMap.getInstance();
                TournamentResult l_Result = new TournamentResult();
                d_Results.add(l_Result);
                l_Result.setGame(l_game);
                l_Result.setMap(l_File);
                MapReader.readMap(d_CurrentMap, l_File);
                if (!MapValidation.validateMap(d_CurrentMap, 0)) {
                    throw new ValidationException("Invalid Map");
                }
                for (PlayerStrategy l_PlayerStrategy : d_Options.getPlayerStrategies()) {
                    Player l_Player = new Player(l_PlayerStrategy);
                    d_CurrentMap.getPlayers().put(l_PlayerStrategy.getClass().getSimpleName(), l_Player);
                }
                d_CurrentMap.assignCountries();
                GameEngine l_GameEngine = new GameEngine();
                l_GameEngine.setGamePhase(GamePhase.Reinforcement);
                l_GameEngine.start();
                Player l_Winner = d_CurrentMap.getWinner();
                if (Objects.nonNull(l_Winner)) {
                    l_Result.setWinner(l_Winner.getName());
                } else {
                    l_Result.setWinner("Draw");
                }
                d_CurrentMap.flushGameMap();
            }
        }

        for (TournamentResult l_Result : d_Results) {
            System.out.printf("%15s%15s", l_Result.getMap(), l_Result.getWinner());
        }

    }
}
