package utils;

import model.Continent;
import model.Country;
import model.GameMap;
import utils.logger.LogEntryBuffer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author surya manian
 * This class is used for reading and writing map files in Conquest format
 *
 */
public class Adaptee {
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    /**
     * Loads a map from a given file and returns it.
     * Note that attributes not used by this game are ignored and not loaded.
     * This means that those attributes will not be present when saving a map from this game.
     *
     * @param p_GameMap the model game map
     * @param p_FileName The file to load from (including the extension).
     * @throws ValidationException file exception
     */
    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_Logger.clear();
        d_Logger.log("Conquest map is loaded \n");
        /**
         *
         */
        try {
            p_GameMap.flushGameMap();
            File l_File = new File("maps/" + p_FileName);

            FileReader l_FileReader = new FileReader(l_File);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader l_Buffer = new BufferedReader(l_FileReader);
            while (l_Buffer.ready()) {
                String l_Read = l_Buffer.readLine();
                if (!l_Read.isEmpty()) {
                    if (l_Read.contains("[") && l_Read.contains("]")) {
                        l_CurrentKey = l_Read.replace("[", "").replace("]", "");
                        l_MapFileContents.put(l_CurrentKey, new ArrayList<>());
                    } else {
                        l_MapFileContents.get(l_CurrentKey).add(l_Read);
                    }
                }
            }

            readContinentsFromFile(p_GameMap, l_MapFileContents.get("Continents"));
            Map<String, List<String>> l_CountryNeighbors = readCountriesFromFile(p_GameMap, l_MapFileContents.get("Territories"));
            addNeighborsFromFile(p_GameMap, l_CountryNeighbors);
        } catch (ValidationException | IOException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    /**
     * This function reads the Continents from the file
     *
     * @param p_ContinentArray the value list for Continents
     * @param p_GameMap        the game map
     * @throws ValidationException when validation fails
     */
    public void readContinentsFromFile(GameMap p_GameMap, List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split("=");
            if (l_InputArray.length == 2) {
                p_GameMap.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    /**
     * This function reads the Countries from the file
     *
     * @param p_CountryArray the value list for Countries
     * @param p_GameMap      the game map
     * @return Neighbouring countries
     * @throws ValidationException when validation fails
     */

    public Map<String, List<String>> readCountriesFromFile(GameMap p_GameMap, List<String> p_CountryArray) throws ValidationException {
        Map<String, List<String>> l_CountryNeighbors = new HashMap<>();
        for (String l_InputString : p_CountryArray) {
            List<String> l_InputArray = Arrays.stream(l_InputString.split(" ")).collect(Collectors.toList());
            if (l_InputArray.size() >= 2) {
                p_GameMap.addCountry(l_InputArray.get(0), l_InputArray.get(1));
                l_CountryNeighbors.put(l_InputArray.get(0), l_InputArray.subList(2, l_InputArray.size()));
            }
        }
        return l_CountryNeighbors;
    }

    /**
     * This function adds the neighbouring Countries
     *
     * @param p_NeighborList the neighbouring country list
     * @param p_GameMap      the game map
     * @throws ValidationException when validation fails
     */

    public void addNeighborsFromFile(GameMap p_GameMap, Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                p_GameMap.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }

    /**
     * function to create neighbor country list
     * @param p_Neighbors the neighbor player
     * @return the neighbor country list
     */
    public static String createANeighborList(Set<Country> p_Neighbors) {
        String l_Result = "";
        for (Country l_Neighbor : p_Neighbors) {
            l_Result += l_Neighbor.getName() + " ";
        }
        return l_Result.length() > 0 ? l_Result.substring(0, l_Result.length() - 1) : "";
    }
    /**
     * Save map into file as continent and country
     *
     * @param p_FileName name of file
     * @param p_Map parameter o GameMap class
     * @return boolean true if written
     * @throws IOException file exception
     */


    public boolean saveMap(GameMap p_Map, String p_FileName) throws IOException {
        String l_MapData = "[Map]\nauthor=Anonymous\n[Continents]\\n";
        for (Continent l_Continent : p_Map.getContinents().values()) {
            l_MapData += l_Continent.getName() + "=" + l_Continent.getAwardArmies();
            l_MapData += "\n";
        }

        l_MapData += "[Territories]\n";
        for (Continent l_Continent : p_Map.getContinents().values()) {
            for (Country l_Country : p_Map.getCountries().values()) {
                l_MapData += l_Country.getName() + " " + l_Country.getContinent() + " " + createANeighborList(l_Country.getNeighbors()) + "\n";
            }
            PrintWriter l_WriteData = null;
            try {
                final String PATH = "maps/";
                l_WriteData = new PrintWriter(PATH + p_FileName + ".map");
                l_WriteData.println(l_MapData);
                return true;
            } catch (Exception p_Exception) {
                return false;
            } finally { l_WriteData.close();
            }
        }
        return true;

    }


}
