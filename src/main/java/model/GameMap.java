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
    private SaveMap d_SaveMap;
    private HashMap<String, Continent> d_Continents = new HashMap<>();
    private HashMap<String, Country> d_Countries = new HashMap<>();
    private HashMap<String, Player> d_Players = new HashMap<>();
    private String name;
    private String errorMessage;

    private GameMap() {
    }

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
     * @param id Unique Continent name
     * @return the required Continent object
     */
    public Continent getContinent(String id) {
        return d_Continents.get(id);
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
     * @param id Unique Country name
     * @return the required Country object
     */
    public Country getCountry(String id) {
        return d_Countries.get(id);
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
     * @param id Unique Player name
     * @return the required Player object
     */
    public Player getPlayer(String id) {
        return d_Players.get(id);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

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

    public void removeCountry(String p_CountryName) throws ValidationException {
        Country l_Country = this.getCountry(p_CountryName);
        if (Objects.isNull(l_Country)) {
            throw new ValidationException("Country does not exist: " + p_CountryName);
        }
        this.getContinent(l_Country.getContinent()).getCountries().remove(l_Country);
        this.getCountries().remove(l_Country.getName());
        System.out.println("Successfully deleted the country");
    }

    public void addNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1) || Objects.isNull(l_Country2)) {
            throw new ValidationException("Atleast one of the mentioned Countries does not exist");
        }
        l_Country1.getNeighbors().add(l_Country2);
//        l_Country2.getNeighbors().add(l_Country1);
        System.out.printf("Successfully connected routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName);
    }


    public void removeNeighbor(String p_CountryName, String p_NeighborCountryName) throws ValidationException {
        Country l_Country1 = this.getCountry(p_CountryName);
        Country l_Country2 = this.getCountry(p_NeighborCountryName);
        if (Objects.isNull(l_Country1) || Objects.isNull(l_Country2)) {
            throw new ValidationException("Atleast one of the mentioned Countries does not exist");
        } else if (!l_Country1.getNeighbors().contains(l_Country2) || !l_Country2.getNeighbors().contains(l_Country1)) {
            throw new ValidationException("Mentioned Countries are not neighbors");
        } else {
            this.getCountry(p_CountryName).getNeighbors().remove(l_Country2);
//            this.getCountry(p_NeighborCountryName).getNeighbors().remove(l_Country1);
            System.out.printf("Successfully removed routes between mentioned Countries: %s - %s\n", p_CountryName, p_NeighborCountryName);
        }
    }

    public void addPlayer(String p_PlayerName) throws ValidationException {
        if (this.getPlayers().containsKey(p_PlayerName)) {
            throw new ValidationException("Player already exists");
        }
        Player l_Player = new Player();
        l_Player.setName(p_PlayerName);
        this.getPlayers().put(p_PlayerName, l_Player);
        System.out.println("Successfully added Player: " + p_PlayerName);
    }

    public void removePlayer(String p_PlayerName) throws ValidationException {
        Player l_Player = this.getPlayer(p_PlayerName);
        if (Objects.isNull(l_Player)) {
            throw new ValidationException("Player does not exist: " + p_PlayerName);
        }
        this.getPlayers().remove(l_Player.getName());
        System.out.println("Successfully deleted the player: " + p_PlayerName);
    }
    public void saveMap() throws ValidationException {
        //Ask p_size for minimum number of countries based on player
        if (MapValidation.validateMap(d_GameMap, 0)){
            d_SaveMap = new SaveMap();
            System.out.println("Done.");
            boolean bool = true;
            while (bool) {
                String mapName = null;
                System.out.println("Please enter the map name to save:");
                if (mapName != null) {
                    if (mapName.isEmpty()) {
                        System.out.println("Please enter a name..");
                    } else {
                        d_GameMap.setName(mapName);
                        if (d_SaveMap.saveMapIntoFile(d_GameMap, mapName)) {
                            System.out.println("Map saved.");
                        } else {
                            System.out.println("Map name already exists, enter different name.");
                        }
                        bool = false;
                    }
                } else {
                    bool = false;
                }
            }
        } else{
            throw new ValidationException("Invalid Map, can not be saved.");
        }
    }
    /**
     * This function reads the Continents from the file
     *
     * @param p_ContinentArray the value list for Continents
     * @throws ValidationException
     */
    public void readContinentsFromFile(List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split(" ");
            if (l_InputArray.length == 2) {
                d_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    /**
     * This function reads the Countries from the file
     *
     * @param p_CountryArray the value list for Countries
     * @throws ValidationException
     */

    public Map<String, List<String>> readCountriesFromFile(List<String> p_CountryArray) throws ValidationException {
        Map<String, List<String>> l_CountryNeighbors = new HashMap<>();
        for (String l_InputString : p_CountryArray) {
            List<String> l_InputArray = Arrays.stream(l_InputString.split(" ")).collect(Collectors.toList());
            if (l_InputArray.size() >= 2) {
                d_GameMap.addCountry(l_InputArray.get(0), l_InputArray.get(1));
                l_CountryNeighbors.put(l_InputArray.get(0), l_InputArray.subList(2, l_InputArray.size()));
            }
        }
        return l_CountryNeighbors;
    }

    /**
     * This function adds the neighbouring Countries
     *
     * @param p_NeighborList the neighbouring country list
     * @throws ValidationException
     */

    public void addNeighborsFromFile(Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                d_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}
