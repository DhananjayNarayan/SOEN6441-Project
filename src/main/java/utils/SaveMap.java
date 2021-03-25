package utils;

import java.io.PrintWriter;
import java.util.Set;

import model.Continent;
import model.Country;
import model.GameMap;
/**
 * This class is used to save map as a text file
 * @author Dhananjay Narayan
 * @author Neona
 * @author Surya
 */
public class SaveMap {

    /**
     * An object of gamemap
     */
    private GameMap gameMap;
    /**
     * Create a neighbor list as a string for the countries
     *
     * @param p_Neighbors the country to add to neighbor list
     * @return String result of neighbor list
     */
    public String createANeighborList(Set<Country> p_Neighbors) {
        String l_result = "";
        for (Country l_Neighbor : p_Neighbors) {
            l_result += l_Neighbor.getName() + " ";
        }
        return l_result.length() > 0 ? l_result.substring(0, l_result.length() - 1) : "";
    }
    /**
     * Save map into file as continent and country
     *
     * @param p_Name name of file
     * @param p_GameMap parameter o GameMap class
     * @return boolean true if written
     */

    public boolean saveMapIntoFile(GameMap p_GameMap, String p_Name) {
        String l_MapData = "[Map]\nauthor=Anonymous\n[Continents]\n";
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            l_MapData += l_Continent.getName() + "=" + l_Continent.getAwardArmies();
            l_MapData += "\n";
        }

        l_MapData += "[Territories]\n";
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : p_GameMap.getCountries().values()) {
                l_MapData += l_Country.getName() + " " + l_Country.getContinent() + " " + createANeighborList(l_Country.getNeighbors()) + "\n";
            }
            PrintWriter l_WriteData = null;
            try {
                final String PATH = "maps/";
                l_WriteData = new PrintWriter(PATH + p_Name + ".map");
                l_WriteData.println(l_MapData);
                return true;
            } catch (Exception ex) {
                return false;
            } finally {
                l_WriteData.close();
            }
        }
        return true;

    }
}
