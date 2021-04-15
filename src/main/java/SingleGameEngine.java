import model.GameMap;
import model.GamePhase;
import model.GameSettings;
import model.Player;
import model.strategy.player.PlayerStrategy;
import model.tournament.TournamentOptions;
import model.tournament.TournamentResult;
import utils.*;
import utils.logger.LogEntryBuffer;

import java.util.*;

/**
 * class to implement single game engine
 */
public class SingleGameEngine implements Engine {

    /**
     * the logger observable
     */
    private LogEntryBuffer d_Logger;
    /**
     * tournament options
     */
    TournamentOptions d_Options;
    /**
     * list for tournament results
     */
    List<TournamentResult> d_Results = new ArrayList<>();
    /**
     * current game map
     */
    GameMap d_CurrentMap;

    /**
     * constructor for single game engine
     */
    public SingleGameEngine() {
        d_Logger = LogEntryBuffer.getInstance();
        d_Options = new TournamentOptions();
        init();
    }

    /**
     * method for checking options
     */
    public void init() {
        d_Options = getTournamentOptions();
        if (Objects.isNull(d_Options)) {
            init();
        }
    }

    /**
     * method to check tournament options
     *
     * @return parsed command
     */
    private TournamentOptions getTournamentOptions() {
        Scanner l_Scanner = new Scanner(System.in);
        d_Logger.log("-----------------------------------------------------------------------------------------");
        d_Logger.log("You are in Single Game Mode");
        d_Logger.log("Enter the start command for single game- ");
        d_Logger.log("Sample Command: tournament -M Map1.map -P strategy1,strategy2 ");
        d_Logger.log("-----------------------------------------------------------------------------------------");
        String l_TournamentCommand = l_Scanner.nextLine();
        d_Options = parseCommand(l_TournamentCommand);
        if (Objects.isNull(d_Options)) {
            d_Logger.log("wrong command please re-enter");
            getTournamentOptions();
        }
        return d_Options;
    }

    /**
     * method to parse command
     *
     * @param p_TournamentCommand the tournament command
     * @return tournament options
     */
    private TournamentOptions parseCommand(String p_TournamentCommand) {
        try {
            d_Options = new TournamentOptions();
            if (!p_TournamentCommand.isEmpty() &&
                    p_TournamentCommand.contains("-M") && p_TournamentCommand.contains("-P")) {
                List<String> l_CommandList = Arrays.asList(p_TournamentCommand.split(" "));
                String l_MapValue = l_CommandList.get(l_CommandList.indexOf("-M") + 1);
                String l_PlayerTypes = l_CommandList.get(l_CommandList.indexOf("-P") + 1);
                String[] l_Maps = l_MapValue.split(",");
                if (l_Maps.length == 1) {
                    d_Options.getMap().add(l_Maps[0]);
                    for (String l_Strategy : l_PlayerTypes.split(",")) {
                        d_Options.getPlayerStrategies().add(PlayerStrategy.getStrategy(l_Strategy));
                    }
                } else {
                    d_Logger.log("Multiple maps not allowed in single game mode");
                    throw new Exception();
                }
            } else {
                return null;
            }
            return d_Options;
        } catch (Exception e) {
            d_Logger.log("Check your command");
            d_Logger.log("command should be in this format: tournament -M mapfile -P listofplayerstrategies");
            return null;
        }
    }

    /**
     * start of the soingle game mode
     *
     * @throws ValidationException if it occurs
     */
    public void start() throws ValidationException {
        String l_File = d_Options.getMap().get(0);
        GameSettings.getInstance().MAX_TRIES = 30;
        d_CurrentMap = GameMap.newInstance();
        d_CurrentMap.flushGameMap();
        TournamentResult l_Result = new TournamentResult();
        d_Results.add(l_Result);
        l_Result.setGame(1);
        l_Result.setMap(l_File);
        boolean l_ShouldUseConquestAdapter = MapReader.isConquestMap(l_File);
        DominationMap l_MapReader = l_ShouldUseConquestAdapter ? new Adapter(new Adaptee()) : new DominationMap();
        l_MapReader.readMap(d_CurrentMap, l_File);
        if (!MapValidation.validateMap(d_CurrentMap, 0)) {
            throw new ValidationException("Invalid Map");
        }
        for (PlayerStrategy l_PlayerStrategy : d_Options.getPlayerStrategies()) {
            Player l_Player = new Player(l_PlayerStrategy);
            l_Player.setName(l_PlayerStrategy.getClass().getSimpleName());
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
        System.out.printf("%15s %15s\n", l_Result.getMap(), l_Result.getWinner());
    }

    /**
     * method to set game phase
     *
     * @param p_GamePhase the game phase
     */
    //tournament -M Australia.map,newmap.map -P aggressive,random -G 2 -D 3
    @Override
    public void setGamePhase(GamePhase p_GamePhase) {

    }
}
