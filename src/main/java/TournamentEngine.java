import model.GameMap;
import model.GamePhase;
import model.GameSettings;
import model.Player;
import model.strategy.player.PlayerStrategy;
import model.tournament.TournamentOptions;
import model.tournament.TournamentResult;
import utils.MapReader;
import utils.MapValidation;
import utils.ValidationException;
import utils.logger.LogEntryBuffer;
import java.util.*;

/**
 * Class to implement the tournament mode game
 *
 * @author Madhuvanthi
 *
 */
public class TournamentEngine implements Engine {
    /**
     * logger observable
     */
    private LogEntryBuffer d_Logger;
    /**
     * Tournament options variable
     */
    TournamentOptions d_Options;
    /**
     *  List to hold the tournament results
     */
    List<TournamentResult> d_Results = new ArrayList<>();
    /**
     * game map instace
     */
    GameMap d_CurrentMap;

    /**
     * default constructor
     */
    public TournamentEngine() {
        d_Logger = LogEntryBuffer.getInstance();
        init();
    }

    /**
     * method to check if object is null
     */
    public void init() {
        d_Options = getTournamentOptions();
        if (Objects.isNull(d_Options)) {
            d_Logger.log("re enter command");
            init();
        }
    }

    /**
     * Method to read the tournament command
     *
     * @return parsed command
     */
    //tournament -M Australia.map,newmap.map -P aggressive,random -G 2 -D 3
    public TournamentOptions getTournamentOptions() {
        Scanner l_Scanner = new Scanner(System.in);
        d_Logger.log("You are in Tournament Mode");
        d_Logger.log("enter the tournament command");
        String l_TournamentCommand = l_Scanner.nextLine();
        d_Options = parseCommand(l_TournamentCommand);
        if (Objects.isNull(d_Options)) {
            getTournamentOptions();
        }
        return d_Options;
    }

    /**
     * method to parse the tournament command
     *
     * @param p_TournamentCommand the tournament command
     * @return tournament options
     */
    public TournamentOptions parseCommand(String p_TournamentCommand) {
        try {
            d_Options = new TournamentOptions();
            if (!p_TournamentCommand.isEmpty() &&
                    p_TournamentCommand.contains("-M") && p_TournamentCommand.contains("-P")
                    && p_TournamentCommand.contains("-G") && p_TournamentCommand.contains("-D")) {
                List<String> l_CommandList = Arrays.asList(p_TournamentCommand.split(" "));
                String l_MapValue = l_CommandList.get(l_CommandList.indexOf("-M") + 1);
                String l_PlayerTypes = l_CommandList.get(l_CommandList.indexOf("-P") + 1);
                String l_GameCount = l_CommandList.get(l_CommandList.indexOf("-G") + 1);
                String l_maxTries = l_CommandList.get(l_CommandList.indexOf("-D") + 1);
                d_Options.getMap().addAll(Arrays.asList(l_MapValue.split(",")));
                for (String l_Strategy : l_PlayerTypes.split(",")) {
                    d_Options.getPlayerStrategies().add(PlayerStrategy.getStrategy(l_Strategy));
                }
                if (d_Options.getPlayerStrategies().size() < 2) {
                    return null;
                }
                int l_NumOfGames = Integer.parseInt(l_GameCount);
                int l_NumofTurns = Integer.parseInt(l_maxTries);
                if (l_NumOfGames > 0 && l_NumOfGames <= 5 && l_NumofTurns > 0 && l_NumofTurns <= 50) {
                    d_Options.setGames(l_NumOfGames);
                    d_Options.setMaxTries(l_NumofTurns);
                } else {
                    d_Logger.log("Give correct number of games and turns");
                    return null;
                }
            }
            return d_Options;
        } catch (Exception e) {
            d_Logger.log("Check your command");
            d_Logger.log("command should be in this format: tournament -M listofmapfiles -P listofplayerstrategies -G numberofgames -D maxnumberofturns");
            return null;
        }
    }

    /**
     * the start method to start the tournamend mode
     *
     * @throws ValidationException if exception occurs
     */
    public void start() throws ValidationException {
        for (String l_File : d_Options.getMap()) {
            for (int l_game = 1; l_game <= d_Options.getGames(); l_game++) {
                GameSettings.getInstance().MAX_TRIES = d_Options.getMaxTries();
                d_CurrentMap = GameMap.newInstance();
                d_CurrentMap.flushGameMap();
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
            }
        }

        for (TournamentResult l_Result : d_Results) {
            System.out.printf("%15s %15s\n", l_Result.getMap(), l_Result.getWinner());
        }

    }

    /**
     * Overrided method to set the game phase
     *
     * @param p_GamePhase the game phase
     */
    //tournament -M Australia.map,newmap.map -P aggressive,random -G 2 -D 3
    @Override
    public void setGamePhase(GamePhase p_GamePhase) {

    }
}
