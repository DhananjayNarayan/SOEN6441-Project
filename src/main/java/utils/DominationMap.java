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
        String content = ";Map";
        content += (fileName + "\r\n");
        content += ("\r\nname " + fileName + " Map\r\n");
        content += ("\r\n" + message + "\r\n");
        content += ("\r\n[continents]\r\n");
        for (Continent continent : map.getContinents().values()) {
            content += (continent.getName() + " " + continent.getAwardArmies() + " 00000\r\n");
        }
        content += ("\r\n[countries]\r\n");
        String borders = "";
        for (Country country : map.getCountries().values()) {
//            int countryIndex = map.getListOfCountries().indexOf(country) + 1;
//            int continentIndex = map.getListOfContinent().indexOf(map.getContinentFromName(country.getContinentName())) + 1;
//            content += (countryIndex + " " + country.getName() + " " + continentIndex + "\r\n");
//            borders += (countryIndex + "");
//            for (String neighborName : country.getNeighbors()) {
//                int neighborIndex = map.getListOfCountries().indexOf(map.getCountryFromName(neighborName)) + 1;
//                borders += (" " + neighborIndex);
//            }
//            borders += ("\r\n");
        }
//        content += ("\r\n[borders]\r\n" + borders);
//        bwFile.write(content);
//        bwFile.close();
        System.out.println("Map file saved as: " + fileName + ".map");
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

    }
}
