package utils;

import model.Continent;
import model.Country;
import model.GameMap;
import utils.logger.LogEntryBuffer;
import java.io.*;
import java.util.*;


public class DominationMap {
    /**
     * Logger Observable
     */
    private static LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    /**
     * cuurent line
     */
    private static String d_CurrentLine;
    /**
     * buffer for reading
     */
    private static BufferedReader d_Buffer;
    /**
     * continent list
     */
    private static List<String> Continents = new ArrayList<>();
    /**
     * country hashmap
     */
    private static HashMap<String, String> Country = new HashMap<>();

    /**
     * This function reads the file and places the contents of the file
     * in a Hash Map
     *
     * @param p_FileName the map file name
     * @param p_GameMap  the game map
     * @throws ValidationException when validation fails
     */
    public static void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_Logger.clear();
        d_Logger.log("Reading Map \n");
        try {
            p_GameMap.flushGameMap();
            File l_File = new File("maps/" + p_FileName);
            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();

            d_Buffer = new BufferedReader(l_FileReader);
            while ((d_CurrentLine = d_Buffer.readLine()) != null) {
                if (d_CurrentLine.contains("[continents]")) {
                    readContinentsFromFile(p_GameMap);
                }
                if (d_CurrentLine.contains("[countries]")) {
                    readCountriesFromFile(p_GameMap);
                }
                if (d_CurrentLine.contains("[borders]")) {
                    addNeighborsFromFile(p_GameMap);
                }

            }
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * This function reads the Continents from the file
     *
     * @param p_GameMap        the game map
     * @throws ValidationException when validation fails
     * @throws IOException file IO exception
     */
    public static void readContinentsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_ContinentDetails = d_CurrentLine.split(" ");
            p_GameMap.addContinent(l_ContinentDetails[0], l_ContinentDetails[1]);
            Continents.add(l_ContinentDetails[0]);
        }
    }

    /**
     * This function reads the Countries from the file
     *
     * @param p_GameMap      the game map
     * @throws ValidationException when validation fails
     * @throws IOException file IO exception
     */

    public static void readCountriesFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_CountryDetails = d_CurrentLine.split(" ");
            p_GameMap.addCountry(l_CountryDetails[1], Continents.get((Integer.parseInt(l_CountryDetails[2]) - 1)));
            Country.put(l_CountryDetails[0], l_CountryDetails[1]);
        }
    }

    /**
     * This function adds the neighbouring Countries
     *
     * @param p_GameMap      the game map
     * @throws ValidationException when validation fails
     * @throws IOException file IO exception
     */

    public static void addNeighborsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((d_CurrentLine = d_Buffer.readLine()) != null && !d_CurrentLine.contains("[")) {
            if (d_CurrentLine.length() == 0) {
                continue;
            }
            String[] l_NeighbourDetails = d_CurrentLine.split(" ");
            for (int i = 1; i < l_NeighbourDetails.length; i++) {
                p_GameMap.addNeighbor(Country.get(l_NeighbourDetails[0]), Country.get(l_NeighbourDetails[i]));
            }
        }
    }

    /**
     * function to save the map
     *
     * @param map Gamemap instance
     * @param fileName file name to be save
     * @throws IOException exception for file save
     */
    public static void saveMap(GameMap map, String fileName) throws IOException {
        String message = " ";
        message = "yura.net Risk 1.0.9.2";
        String currentPath = System.getProperty("user.dir") + "\\Maps\\";
        String mapPath = currentPath + fileName + ".map";
        BufferedWriter bwFile = new BufferedWriter(new FileWriter(mapPath));
        String content = ";Map ";
        content += (fileName + ".map" + "");
        content += ("\rname " + fileName + ".map" + " Map");
        content += ("\r" + message + "\r");
        content += ("\r\n[continents]\r\n");
        HashMap<Integer, String> l_ContinentMap = createContinentList(map);
        for (Continent continent : map.getContinents().values()) {
            content += (continent.getName() + " " + continent.getAwardArmies() + " 00000\r\n");
        }
        content += ("\r\n[countries]\r\n");
        String borders = "";
        HashMap<Integer, String> l_CountryMap = createCountryList(map);
        for (Map.Entry<Integer, String> l_Country : l_CountryMap.entrySet()) {
            //System.out.println(l_Country.getKey() + " " + l_Country.getValue() + " " + map.getCountry(l_Country.getValue()).getContinent());
            for(Map.Entry<Integer, String> l_Continent : l_ContinentMap.entrySet()) {
                if(l_Continent.getValue() == map.getCountry(l_Country.getValue()).getContinent()) {
                    //System.out.println("The key for value " + map.getCountry(l_Country.getValue()).getContinent() + " is " + l_Continent.getKey());
                    content += (l_Country.getKey() + " " + l_Country.getValue() + " " + l_Continent.getKey() + "\r\n");
                    break;
                }
            }
            borders += (l_Country.getKey() + "");
            for (Country l_Neighbor : map.getCountry(l_Country.getValue()).getNeighbors()) {
                for(Map.Entry<Integer, String> l_CountryList : l_CountryMap.entrySet()){
                    if(l_Neighbor.getName() == l_CountryList.getValue()){
                        borders += (" " + l_CountryList.getKey());
                    }
                }
            }
            borders += ("\r\n");
        }

        content += ("\r\n[borders]\r\n" + borders);
        bwFile.write(content);
        bwFile.close();
        System.out.println("Map file saved as: " + fileName + ".map");
    }

    /**
     * Create hashmap of country
     *
     * @param p_GameMap instance of gamemap
     * @return hashmap of country with index
     */
    public static HashMap<Integer, String> createCountryList(GameMap p_GameMap){
        HashMap<Integer, String> l_CountryMap = new HashMap<>();
        int counter = 1;
        for(Country l_Country : p_GameMap.getCountries().values()){
            l_CountryMap.put(counter++, l_Country.getName());
        }
        return l_CountryMap;
    }

    /**
     * create a continent hashmap
     *
     * @param p_GameMap gamemap instance
     * @return hashmap of continent and index
     */
    public static HashMap<Integer, String> createContinentList(GameMap p_GameMap){
        HashMap<Integer, String> l_CountryMap = new HashMap<>();
        int counter = 1;
        for(Continent l_Continent : p_GameMap.getContinents().values()){
            l_CountryMap.put(counter++, l_Continent.getName());
        }
        return l_CountryMap;
    }

    /**
     * For testing, remove all static variables on integration.
     *
     * @param args arg
     * @throws ValidationException exception
     * @throws IOException exception
     */
    public static void main(String[] args) throws ValidationException, IOException {
        GameMap d_GameMap = GameMap.getInstance();
        readMap(d_GameMap, "output.map");
        for (Continent c : d_GameMap.getContinents().values()){
            System.out.println(c.getName() + " " + c.getAwardArmies());
        }

        for (Country c : d_GameMap.getCountries().values()){
            System.out.print("Country:" + c.getName() + " ");
            for(Country n : c.getNeighbors()){
                System.out.print("Neighbor: " + n.getName() + "-");
            }
            System.out.println("\n");
        }
        saveMap(d_GameMap, "test");
    }
}
