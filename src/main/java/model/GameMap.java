package model;

import utils.MapValidation;
import utils.SaveMap;
import utils.ValidationException;
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
public class GameMap {
    private static GameMap d_GameMap;
    private HashMap<String, Continent> d_Continents = new HashMap<>();
    private HashMap<String, Country> d_Countries = new HashMap<>();
    private HashMap<String, Player> d_Players = new HashMap<>();
    private String d_Name;
    private String d_ErrorMessage;

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
        System.out.println("Successfully added Continent: " + p_ContinentName);
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
        System.out.println("Successfully added Country: " + p_CountryName);
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
        System.out.println("Successfully deleted the continent: " + p_ContinentName);
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
        System.out.println("Successfully deleted the country");
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
        System.out.printf("Successfully connected routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName);
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
            System.out.printf("Successfully removed routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName);
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
        Player l_Player = new Player();
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        System.out.println("Successfully added Player: " + p_PlayerName);
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
        System.out.println("Successfully deleted the player: " + p_PlayerName);
    }

    /**
     * Saves map as a file, if valid with the specified name.
     *
     * @throws ValidationException if any input/output issue.
     */
    public void saveMap() throws ValidationException {
        //Ask p_size for minimum number of countries based on player
        if (MapValidation.validateMap(d_GameMap, 0)) {
            SaveMap d_SaveMap = new SaveMap();
            boolean bool = true;
            while (bool) {
                d_GameMap.getName();
                if (Objects.isNull(d_GameMap.getName()) || d_GameMap.getName().isEmpty()) {
                    throw new ValidationException("Please enter the file name:");
                } else {
                    if (d_SaveMap.saveMapIntoFile(d_GameMap, d_GameMap.getName())) {
                        System.out.println("The map has been validated and is saved.");
                    } else {
                        throw new ValidationException("Map name already exists, enter different name.");
                    }
                    bool = false;
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
        int d_player_index = 0;
        List<Player> d_players = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());

        List<Country> d_countryList = d_GameMap.getCountries().values().stream().collect(Collectors.toList());  //get all countries from each continent
        Collections.shuffle(d_countryList);
        for (int i = 0; i < d_countryList.size(); i++) {
            Country d_c = d_countryList.get(i);                // loop for get each country of the map
            Player d_p = d_players.get(d_player_index);          // find the corresponding player by the order of the player
            d_p.getCapturedCountries().add(d_c);
            d_c.setPlayer(d_p);
            System.out.println(d_c.getName() + " Assigned to " + d_p.getName());
            if (d_player_index < d_GameMap.getPlayers().size() - 1) {     //if not all players get a new country in this round
                d_player_index++;
            } else {                                         //if all players get a new counter in this round, start from player 1
                d_player_index = 0;
            }
        }
    }


    /**
     * A function to display the map chosen, its continents, countries, neighbours,
     * players and their ownership
     */

    public void showMap() {
        System.out.println("\nShowing the Map Details : \n");

        // Showing Continents in Map
        System.out.println("\nThe Continents in this Map are : \n");
        Iterator<Map.Entry<String, Continent>> d_iteratorForContinents = d_GameMap.getContinents().entrySet()
                .iterator();

        String table = "|%-18s|%n";

        System.out.format("+------------------+%n");
        System.out.format("| Continent's name |%n");
        System.out.format("+------------------+%n");

        while (d_iteratorForContinents.hasNext()) {
            Map.Entry<String, Continent> continentMap = (Map.Entry<String, Continent>) d_iteratorForContinents.next();
            String d_continentId = (String) continentMap.getKey();
            Continent d_continent = d_GameMap.getContinents().get(d_continentId); //Get the particular continent by its ID(Name)

            System.out.format(table, d_continent.getName());
        }
        System.out.format("+------------------+%n");


        // Showing Countries in the Continent and their details
        System.out.println("\nThe countries in this Map and their details are : \n");

        Iterator<Map.Entry<String, Continent>> d_iteratorForContinent = d_GameMap.getContinents().entrySet()
                .iterator();

        table = "|%-23s|%-18s|%-60s|%-15s|%n";

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");
        System.out.format(
                "     Country's name     | Continent's Name |   Neighbour Countries                                      | No. of armies |%n");
        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");

        while (d_iteratorForContinent.hasNext()) {
            Map.Entry<String, Continent> d_continentMap = (Map.Entry<String, Continent>) d_iteratorForContinent.next();
            String d_continentId = (String) d_continentMap.getKey();
            Continent d_continent = d_GameMap.getContinents().get(d_continentId); // to get the continent by its ID(Name)
            //ListIterator<Country> listIterator = continent.getCountries().listIterator();
            Iterator<Country> d_listIterator = d_continent.getCountries().iterator();

            while (d_listIterator.hasNext()) {

                Country d_country = (Country) d_listIterator.next();
                System.out.format(table, d_country.getName(), d_continent.getName(), d_country.createANeighborList(d_country.getNeighbors()), d_country.getArmies());
            }
        }

        System.out.format(
                "+--------------+-----------------------+------------------+----------------------------+---------------+---------------+%n");

        // Showing the players in game. Have to modify

        HashMap<String, Player> d_players = d_GameMap.getPlayers();
        System.out.println("\n\n\n\nPlayers in this game if the game has started are : ");
        if (d_players != null) {
            d_players.forEach((key, value) -> System.out.println((String) key));  // will slightly modify the output after testing with the entire project
            System.out.println();
        }


        //Showing the Ownership of the players
        System.out.println("\nThe Map ownership of the players are : \n");
        //  String table1 = "|%-15s|%-30s|%-21d|%n";

        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");
        System.out.format(
                "| Player's name |    Continent's Controlled    | No. of Armies Owned |%n");
        System.out.format(
                "+---------------+-----------------------+---------------------------+%n");


        List<Player> d_playerss = d_GameMap.getPlayers().values().stream().collect(Collectors.toList());
        String table1 = "|%-15s|%-30s|%-21d|%n";


        for (Player d_player : d_playerss) {

            //Iterator<Country> listIterator = continent.getCountries().iterator();

            System.out.format(table1, d_player.getName(), d_player.createACaptureList(d_player.getCapturedCountries()), d_player.getReinforcementArmies());


        }

        System.out.format(
                "+---------------+-----------------------+----------------------------+%n");

    }


}
