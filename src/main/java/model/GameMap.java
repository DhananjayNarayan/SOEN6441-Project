package model;

import model.strategy.player.PlayerStrategy;
import utils.MapValidation;
import utils.SaveMap;
import utils.ValidationException;
import utils.logger.LogEntryBuffer;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;


/**
 * Concrete Class to set and get all the properties of the GameMap.
 *
 * @author Neona Pinto
 * @author Dhananjay Narayan
 * @author Surya Manian
 * @author Madhuvanthi Hemanathan
 * @author Prathika Suvarna
 * @version 1.0.0
 */
public class GameMap implements Serializable {
    /**
     * An object of the gamemap
     */
    private static GameMap d_GameMap;
    /**
     * Gamephase object
     */
    private GamePhase d_GamePhase;
    /**
     * A hashmap to store the continents
     */
    private HashMap<String, Continent> d_Continents = new HashMap<>();
    /**
     * A hashmap to store the countries
     */
    private HashMap<String, Country> d_Countries = new HashMap<>();
    /**
     * A hashmap to store the players
     */
    private HashMap<String, Player> d_Players = new HashMap<>();
    /**
     * A string to store the name
     */
    private String d_Name;
    /**
     * A string to store the error message
     */
    private String d_ErrorMessage;

    /**
     * LogEntry Buffer Instance
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();

    /**
     * Current Player
     */
    private Player d_CurrentPlayer;

    /**
     * Default Constructor
     */
    private GameMap() {
    }

    /**
     * Method to get instance of Game map class
     *
     * @return the class object
     */
    public static GameMap getInstance() {
        if (Objects.isNull(d_GameMap)) {
            d_GameMap = new GameMap();
        }
        return d_GameMap;
    }

    /**
     * To get the current phase
     *
     * @return gamephase instance
     */
    public GamePhase getGamePhase() {
        return d_GamePhase;
    }

    /**
     * Set the current phase
     *
     * @param d_GamePhase gamephase instance
     */
    public void setGamePhase(GamePhase d_GamePhase) {
        this.d_GamePhase = d_GamePhase;
    }

    /**
     * Get the list of all the continents
     *
     * @return d_Continents List of the continents
     */
    public HashMap<String, Continent> getContinents() {
        return d_Continents;
    }

    /**
     * Get a single continent
     *
     * @param p_Id Unique Continent name
     * @return the required Continent object
     */
    public Continent getContinent(String p_Id) {
        return d_Continents.get(p_Id);
    }


    /**
     * Get the list of countries
     *
     * @return d_Countries List of the countries
     */
    public HashMap<String, Country> getCountries() {
        return d_Countries;
    }


    /**
     * Get a single country
     *
     * @param p_Id Unique Country name
     * @return the required Country object
     */
    public Country getCountry(String p_Id) {
        return d_Countries.get(p_Id);
    }


    /**
     * Get the list of players
     *
     * @return d_Players List of players
     */
    public HashMap<String, Player> getPlayers() {
        return d_Players;
    }

    /**
     * Get a single player
     *
     * @param p_Id Unique Player name
     * @return the required Player object
     */
    public Player getPlayer(String p_Id) {
        return d_Players.get(p_Id);
    }

    /**
     * Method to get error message.
     *
     * @return d_ErrorMessage - the error message set up
     */
    public String getErrorMessage() {
        return d_ErrorMessage;
    }

    /**
     * Method to set the error message.
     *
     * @param p_ErrorMessage - the actual error
     */
    public void setErrorMessage(String p_ErrorMessage) {
        this.d_ErrorMessage = p_ErrorMessage;
    }

    /**
     * Returns the name of the map.
     *
     * @return The map name
     */
    public String getName() {
        return d_Name;
    }

    /**
     * Sets the map name
     *
     * @param p_Name the map name
     */
    public void setName(String p_Name) {
        this.d_Name = p_Name;
    }

    /**
     * Get the current Player
     *
     * @return player
     */
    public Player getCurrentPlayer() {
        return d_CurrentPlayer;
    }

    /**
     * Set the current Player
     *
     * @param d_CurrentPlayer player
     */
    public void setCurrentPlayer(Player d_CurrentPlayer) {
        this.d_CurrentPlayer = d_CurrentPlayer;
    }


    /**
     * Method to set the Game map object back to empty after
     * each phase.
     */
    public void flushGameMap() {
        GameMap.getInstance().getContinents().clear();
        GameMap.getInstance().getCountries().clear();
        GameMap.getInstance().getPlayers().clear();
    }

    /**
     * Adds the continent to the map's continent list.
     *
     * @param p_ContinentName Continent name
     * @param p_ControlValue  Continent control value
     * @throws ValidationException if any input or output issue
     */
    public void addContinent(String p_ContinentName, String p_ControlValue) throws ValidationException {

        if (this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationException("Continent already exists");
        }
        Continent l_Continent = new Continent();
        l_Continent.setName(p_ContinentName);
        l_Continent.setAwardArmies(Integer.parseInt(p_ControlValue));
        this.getContinents().put(p_ContinentName, l_Continent);
        d_Logger.log("Successfully added Continent: " + p_ContinentName);
    }

    /**
     * Adds country to the map's country list and continent's
     * country list.
     *
     * @param p_CountryName   Country name
     * @param p_ContinentName Continent name
     * @throws ValidationException if any input or output issue
     */
    public void addCountry(String p_CountryName, String p_ContinentName) throws ValidationException {

        if (this.getCountries().containsKey(p_CountryName)) {
            throw new ValidationException("Country already exist");
        }
        Country l_Country = new Country();
        l_Country.setName(p_CountryName);
        l_Country.setContinent(p_ContinentName);
        this.getCountries().put(p_CountryName, l_Country);
        this.getContinent(p_ContinentName).getCountries().add(l_Country);
        d_Logger.log("Successfully added Country: " + p_CountryName);
    }

    /**
     * Removes continent from the map's continent list and its
     * respective countries
     *
     * @param p_ContinentName Continent name
     * @throws ValidationException if any input/output issue
     */
    public void removeContinent(String p_ContinentName) throws ValidationException {

        if (!this.getContinents().containsKey(p_ContinentName)) {
            throw new ValidationException("Continent does not exist");
        }
        Set<String> l_CountrySet = this.getContinents().remove(p_ContinentName)
                .getCountries()
                .stream().map(Country::getName)
                .collect(Collectors.toSet());
        for (String l_CountryName : l_CountrySet) {
            this.getCountries().remove(l_CountryName);
        }
        d_Logger.log("Successfully deleted the continent: " + p_ContinentName);
    }

    /**
     * Removes country from the map's country list and continent's
     * country list.
     *
     * @param p_CountryName Country name
     * @throws ValidationException if any input/output issue
     */
    public void removeCountry(String p_CountryName) throws ValidationException {
        Country l_Country = this.getCountry(p_CountryName);
        if (Objects.isNull(l_Country)) {
            throw new ValidationException("Country does not exist: " + p_CountryName);
        }
        this.getContinent(l_Country.getContinent()).getCountries().remove(l_Country);
        this.getCountries().remove(l_Country.getName());
        d_Logger.log("Successfully deleted the country");
    }

    /**
     * Adds the neighbor to particular country
     *
     * @param p_CountryName         Country name
     * @param p_NeighborCountryName Neighbor country name
     * @throws ValidationException if any input/output issue
     */
    public void addNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1) || Objects.isNull(l_Country2)) {
            throw new ValidationException("Atleast one of the mentioned Countries does not exist");
        }
        l_Country1.getNeighbors().add(l_Country2);
        d_Logger.log(String.format("Successfully connected routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName));
    }


    /**
     * Removes neighbor to a particular country
     *
     * @param p_CountryName         Country name
     * @param p_NeighborCountryName Neighbor country name
     * @throws ValidationException if any input/output issue
     */
    public void removeNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1)) {
            throw new ValidationException("Atleast one of the mentioned Countries does not exist");
        } else if (!l_Country1.getNeighbors().contains(l_Country2) || !l_Country2.getNeighbors().contains(l_Country1)) {
            throw new ValidationException("Mentioned Countries are not neighbors");
        } else {
            this.getCountry(p_CountryName).getNeighbors().remove(l_Country2);
            d_Logger.log(String.format("Successfully removed routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName));
        }
    }

    /**
     * Adds player to the game map.
     *
     * @param p_PlayerName Player name
     * @throws ValidationException if any input/output issue
     */
    public void addPlayer(String p_PlayerName) throws ValidationException {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationException("Player already exists");
        }
        Player l_Player = new Player(PlayerStrategy.getStrategy("human"));
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        d_Logger.log("Successfully added Player: " + p_PlayerName);
    }

    /**
     * Adds player to the game map.
     *
     * @param p_PlayerName Player name
     * @param p_Strategy   Player Strategy
     * @throws ValidationException if any input/output issue
     */
    public void addPlayer(String p_PlayerName, String p_Strategy) throws ValidationException {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationException("Player already exists");
        }
        Player l_Player = new Player(PlayerStrategy.getStrategy(p_Strategy));
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        d_Logger.log("Successfully added Player: " + p_PlayerName);
    }

    /**
     * Removes player from game map.
     *
     * @param p_PlayerName Player name
     * @throws ValidationException if any input/output issue
     */
    public void removePlayer(String p_PlayerName) throws ValidationException {
        Player l_Player = this.getPlayer(p_PlayerName);
        if (Objects.isNull(l_Player)) {
            throw new ValidationException("Player does not exist: " + p_PlayerName);
        }
        this.getPlayers().remove(l_Player.getName());
        d_Logger.log("Successfully deleted the player: " + p_PlayerName);
    }

    /**
     * Saves map as a file, if valid with the specified name.
     *
     * @throws ValidationException if any input/output issue.
     */
    public void saveMap() throws ValidationException {
        //Ask p_size for minimum number of countries based on player
        if (MapValidation.validateMap(d_GameMap, 0)) {
            SaveMap l_SaveMap = new SaveMap();
            boolean l_Bool = true;
            while (l_Bool) {
                d_GameMap.getName();
                if (Objects.isNull(d_GameMap.getName()) || d_GameMap.getName().isEmpty()) {
                    throw new ValidationException("Please enter the file name:");
                } else {
                    if (l_SaveMap.saveMapIntoFile(d_GameMap, d_GameMap.getName())) {
                        d_Logger.log("The map has been validated and is saved.");
                    } else {
                        throw new ValidationException("Map name already exists, enter different name.");
                    }
                    l_Bool = false;
                }
            }
        } else {
            throw new ValidationException("Invalid Map, can not be saved.");
        }
    }

    /**
     * Assign countries to each player of the game in random.
     */
    public void assignCountries() {
        int l_PlayerIndex = 0;
        List<Player> l_Players = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());

        List<Country> l_CountryList = d_GameMap.getCountries().values().stream().collect(Collectors.toList());
        Collections.shuffle(l_CountryList);
        for (int i = 0; i < l_CountryList.size(); i++) {
            Country l_Country = l_CountryList.get(i);
            Player l_Player = l_Players.get(l_PlayerIndex);
            l_Player.getCapturedCountries().add(l_Country);
            l_Country.setPlayer(l_Player);
            d_Logger.log(l_Country.getName() + " Assigned to " + l_Player.getName());

            if (l_PlayerIndex < d_GameMap.getPlayers().size() - 1) {
                l_PlayerIndex++;
            } else {
                l_PlayerIndex = 0;
            }
        }
    }


    /**
     * A function to display the map chosen, its continents, countries, neighbours,
     * players and their ownership
     */

    public void showMap() {
        d_Logger.log("\nShowing the Map Details : \n");

        // Showing Continents in Map
        d_Logger.log("\nThe Continents in this Map are : \n");
        Iterator<Map.Entry<String, Continent>> l_IteratorForContinents = d_GameMap.getContinents().entrySet()
                .iterator();

        String l_Table = "|%-18s|%n";

        System.out.format("+------------------+%n");
        System.out.format("| Continent's name |%n");
        System.out.format("+------------------+%n");

        while (l_IteratorForContinents.hasNext()) {
            Map.Entry<String, Continent> continentMap = (Map.Entry<String, Continent>) l_IteratorForContinents.next();
            String l_ContinentId = (String) continentMap.getKey();
            Continent l_Continent = d_GameMap.getContinents().get(l_ContinentId); //Get the particular continent by its ID(Name)

            System.out.format(l_Table, l_Continent.getName());
        }
        System.out.format("+------------------+%n");


        // Showing Countries in the Continent and their details
        d_Logger.log("\nThe countries in this Map and their details are : \n");

        Iterator<Map.Entry<String, Continent>> l_IteratorForContinent = d_GameMap.getContinents().entrySet()
                .iterator();

        l_Table = "|%-23s|%-18s|%-60s|%-15s|%n";

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");
        System.out.format(
                "     Country's name     | Continent's Name |   Neighbour Countries                                      | No. of armies |%n");
        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");

        while (l_IteratorForContinent.hasNext()) {
            Map.Entry<String, Continent> l_ContinentMap = (Map.Entry<String, Continent>) l_IteratorForContinent.next();
            String l_ContinentId = (String) l_ContinentMap.getKey();
            Continent l_Continent = d_GameMap.getContinents().get(l_ContinentId); // to get the continent by its ID(Name)
            Iterator<Country> l_ListIterator = l_Continent.getCountries().iterator();

            while (l_ListIterator.hasNext()) {

                Country l_Country = (Country) l_ListIterator.next();
                System.out.format(l_Table, l_Country.getName(), l_Continent.getName(), l_Country.createANeighborList(l_Country.getNeighbors()), l_Country.getArmies());
            }
        }

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");

        // Showing the players in game. Have to modify

        HashMap<String, Player> l_Players = d_GameMap.getPlayers();
        d_Logger.log("\n\n\n\nPlayers in this game if the game has started are : ");
        if (l_Players != null) {
            l_Players.forEach((key, value) -> d_Logger.log((String) key));  // will slightly modify the output after testing with the entire project
            d_Logger.log("");
        }

        //Showing the Ownership of the players
        d_Logger.log("\nThe Map ownership of the players are : \n");

        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");
        System.out.format(
                "| Player's name |    Continent's Controlled    | No. of Armies Owned |%n");
        System.out.format(
                "+---------------+-----------------------+---------------------------+%n");
        String l_Table1 = "|%-15s|%-30s|%-21d|%n";
        for (Player l_Player : d_GameMap.getPlayers().values()) {
            //Iterator<Country> listIterator = continent.getCountries().iterator();
            System.out.format(l_Table1, l_Player.getName(), l_Player.createACaptureList(l_Player.getCapturedCountries()), l_Player.getReinforcementArmies());
        }
        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");
    }

    public void gamePlayBuilder(GameMap p_GameMap) throws ValidationException {
        this.flushGameMap();
        for (Map.Entry<String, Continent> l_Continent : p_GameMap.getContinents().entrySet()) {
            this.addContinent(l_Continent.getKey(), String.valueOf(l_Continent.getValue().getAwardArmies()));
        }
        for (Map.Entry<String, Country> l_Country : p_GameMap.getCountries().entrySet()) {
            this.addCountry(l_Country.getKey(), l_Country.getValue().getContinent());
        }
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                for (Country l_Neighbour : l_Country.getNeighbors()) {
                    p_GameMap.addNeighbor(l_Country.getName(), l_Neighbour.getName());
                }
            }
        }
        for (Map.Entry<String, Player> l_Player : p_GameMap.getPlayers().entrySet()) {
            this.addPlayer(l_Player.getKey());
            l_Player.getValue().setCapturedCountries(l_Player.getValue().getCapturedCountries());
            l_Player.getValue().setReinforcementArmies(l_Player.getValue().getReinforcementArmies());
            l_Player.getValue().setOrders(l_Player.getValue().getOrders());
        }
        this.setGamePhase(p_GameMap.getGamePhase());
        if(p_GameMap.getGamePhase().equals("IssueOrder")){
            this.setCurrentPlayer(p_GameMap.getCurrentPlayer());
        }
    }

}
