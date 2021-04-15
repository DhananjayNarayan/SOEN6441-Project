package utils;

import model.Continent;
import model.Country;
import model.GameMap;
import utils.logger.LogEntryBuffer;
import java.io.*;
import java.util.*;

/**
 * Domination class
 * @author Surya
 * @author Neona
 * @version 1.0.0
 */
public class DominationMap {
    /**
     * Logger Observable
     */
    private LogEntryBuffer d_Logger = LogEntryBuffer.getInstance();
    /**
     * current line
     */
    private String d_CurrentLine;
    /**
     * buffer for reading
     */
    private BufferedReader d_Buffer;
    /**
     * continent list
     */
    private List<String> Continents = new ArrayList<>();
    /**
     * country hashmap
     */
    private HashMap<String, String> Country = new HashMap<>();

    /**
     * This function reads the file and places the contents of the file
     * in a Hash Map
     *
     * @param p_FileName the map file name
     * @param p_GameMap  the game map
     * @throws ValidationException when validation fails
     */
    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
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
    public void readContinentsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
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

    public void readCountriesFromFile(GameMap p_GameMap) throws ValidationException, IOException {
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

    public void addNeighborsFromFile(GameMap p_GameMap) throws ValidationException, IOException {
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
     * @param p_GameMap Gamemap instance
     * @param p_FileName file name to be save
     * @return the saved map
     * @throws IOException exception for file save
     */
    public boolean saveMap(GameMap p_GameMap, String p_FileName) throws IOException {
        String l_Message = " ";
        l_Message = "yura.net Risk 1.0.9.2";
        String l_CurrentPath = System.getProperty("user.dir") + "\\Maps\\";
        String l_MapPath = l_CurrentPath + p_FileName + ".map";
        BufferedWriter bwFile = new BufferedWriter(new FileWriter(l_MapPath));
        String d_Content = ";Map ";
        d_Content += (p_FileName + ".map" + "");
        d_Content += ("\rname " + p_FileName + ".map" + " Map");
        d_Content += ("\r" + l_Message + "\r");
        d_Content += ("\r\n[continents]\r\n");
        HashMap<Integer, String> l_ContinentMap = createContinentList(p_GameMap);
        for (Continent continent : p_GameMap.getContinents().values()) {
            d_Content += (continent.getName() + " " + continent.getAwardArmies() + " 00000\r\n");
        }
        d_Content += ("\r\n[countries]\r\n");
        String borders = "";
        HashMap<Integer, String> l_CountryMap = createCountryList(p_GameMap);
        for (Map.Entry<Integer, String> l_Country : l_CountryMap.entrySet()) {
            for(Map.Entry<Integer, String> l_Continent : l_ContinentMap.entrySet()) {
                if(l_Continent.getValue().equals(p_GameMap.getCountry(l_Country.getValue()).getContinent())) {
                    d_Content += (l_Country.getKey() + " " + l_Country.getValue() + " " + l_Continent.getKey() + "\r\n");
                    break;
                }
            }
            borders += (l_Country.getKey() + "");
            for (Country l_Neighbor : p_GameMap.getCountry(l_Country.getValue()).getNeighbors()) {
                for(Map.Entry<Integer, String> l_CountryList : l_CountryMap.entrySet()){
                    if(l_Neighbor.getName().equals(l_CountryList.getValue())){
                        borders += (" " + l_CountryList.getKey());
                    }
                }
            }
            borders += ("\r\n");
        }
        d_Content += ("\r\n[borders]\r\n" + borders);
        bwFile.write(d_Content);
        bwFile.close();
        System.out.println("Map file saved as: " + p_FileName + ".map");
        return true;
    }

    /**
     * Create hashmap of country
     *
     * @param p_GameMap instance of gamemap
     * @return hashmap of country with index
     */
    public HashMap<Integer, String> createCountryList(GameMap p_GameMap){
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
    public HashMap<Integer, String> createContinentList(GameMap p_GameMap){
        HashMap<Integer, String> l_CountryMap = new HashMap<>();
        int counter = 1;
        for(Continent l_Continent : p_GameMap.getContinents().values()){
            l_CountryMap.put(counter++, l_Continent.getName());
        }
        return l_CountryMap;
    }


}
