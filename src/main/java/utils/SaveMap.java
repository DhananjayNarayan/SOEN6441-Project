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
     * @param name name of file
     * @param p_GameMap parameter o GameMap class
     * @return boolean true if written
     */

    public boolean saveMapIntoFile(GameMap p_GameMap, String name) {
        String mapData = "[Map]\n\n[Continents]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            mapData += continent.getName() + " " + continent.getAwardArmies();
            mapData += "\n";
        }

        mapData += "[Territories]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            for (Country country : p_GameMap.getCountries().values()) {
                mapData += country.getName() + " " + country.getContinent() + " " + createANeighborList(country.getNeighbors()) + "\n";
            }

            PrintWriter writeData = null;
            try {
                writeData = new PrintWriter("maps/" + name + ".map");
                writeData.println(mapData);
                return true;
            } catch (Exception ex) {
                return false;
            } finally {
                writeData.close();
            }

        }
        return true;

    }
}
