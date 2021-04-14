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

    private static String currentLine;
    private static BufferedReader l_Buffer;
    private static List<String> Continents = new ArrayList<>();
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

            l_Buffer = new BufferedReader(l_FileReader);
            while ((currentLine = l_Buffer.readLine()) != null) {
                if (currentLine.contains("[continents]")) {
                    readContinentsFromFile(p_GameMap);
                }
                if (currentLine.contains("[countries]")) {
                    readCountriesFromFile(p_GameMap);
                }
                if (currentLine.contains("[borders]")) {
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
     */
    public static void readContinentsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((currentLine = l_Buffer.readLine()) != null && !currentLine.contains("[")) {
            if (currentLine.length() == 0) {
                continue;
            }
            String[] continentDetails = currentLine.split(" ");
            p_GameMap.addContinent(continentDetails[0], continentDetails[1]);
            Continents.add(continentDetails[0]);
        }
    }

    /**
     * This function reads the Countries from the file
     *
     * @param p_GameMap      the game map
     * @return Neighbouring countries
     * @throws ValidationException when validation fails
     */

    public static void readCountriesFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((currentLine = l_Buffer.readLine()) != null && !currentLine.contains("[")) {
            if (currentLine.length() == 0) {
                continue;
            }
            String[] countryDetails = currentLine.split(" ");
            p_GameMap.addCountry(countryDetails[1], Continents.get((Integer.parseInt(countryDetails[2]) - 1)));
            Country.put(countryDetails[0], countryDetails[1]);
        }
    }

    /**
     * This function adds the neighbouring Countries
     *
     * @param p_GameMap      the game map
     * @throws ValidationException when validation fails
     */

    public static void addNeighborsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
        while ((currentLine = l_Buffer.readLine()) != null && !currentLine.contains("[")) {
            if (currentLine.length() == 0) {
                continue;
            }
            String[] neighbourDetails = currentLine.split(" ");
            for (int i = 1; i < neighbourDetails.length; i++) {
                p_GameMap.addNeighbor(Country.get(neighbourDetails[0]), Country.get(neighbourDetails[i]));
            }

        }
    }

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
        for (Map.Entry<String, Country> entry: map.getCountries().entrySet()) {
            System.out.println(entry.getKey());
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
        content += ("\r\n[borders]\r\n" + borders);
//        bwFile.write(content);
//        bwFile.close();
        System.out.println("Map file saved as: " + fileName + ".map");
    }

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
        saveMap(d_GameMap, "output.map");
    }
}
