package controller;
import utils.ValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import model.GameMap;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This class Reads the Domination file and loads the map
 *
 */

public class ReadDominationFile {
    GameMap d_Map = new GameMap();
    Map<String, List<String>> l_CountryNeighbors = new HashMap<>();

    /**
     * This function reads the file and places the contents of the file
     * in a Hash Map
     *
     * @param p_FileName the map file name
     */
    public void ReadMap(String p_FileName) {
        try {
            File l_File = new File(p_FileName);
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
            ReadContinentsFromFile(l_MapFileContents.get("Continents"));
            ReadCountriesFromFile(l_MapFileContents.get("Territories"));
            AddNeighborsFromFile(l_CountryNeighbors);
        } catch (ValidationException | FileNotFoundException e) {
            e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function reads the Continents from the file
     *
     * @param p_ContinentArray the value list for Continents
     * @throws ValidationException
     */
    public void ReadContinentsFromFile(List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split(" ");
            if (l_InputArray.length == 2) {
                d_Map.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    /**
     * This function reads the Countries from the file
     *
     * @param p_CountryArray the value list for Countries
     * @throws ValidationException
     */

    public void ReadCountriesFromFile(List<String> p_CountryArray) throws ValidationException {
        for (String l_InputString : p_CountryArray) {
            List<String> l_InputArray = Arrays.stream(l_InputString.split(" ")).collect(Collectors.toList());
            if (l_InputArray.size() >= 2) {
                d_Map.addCountry(l_InputArray.get(0), l_InputArray.get(1));
                l_CountryNeighbors.put(l_InputArray.get(0), l_InputArray.subList(2, l_InputArray.size()));
            }
        }
    }

    /**
     * This function adds the neighbouring Countries
     *
     * @param p_NeighborList the neighbouring country list
     * @throws ValidationException
     */

    public void AddNeighborsFromFile(Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                d_Map.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}
