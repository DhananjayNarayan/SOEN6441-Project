package utils;

import java.io.PrintWriter;
import java.util.Set;

import model.Continent;
import model.Country;
import model.GameMap;
/**
 * This class is used to save map as a text file
 */
public class SaveMap {

    private GameMap gameMap;
    /**
     * Create A neighbor list as a string for the countries
     * @param p_Neighbors parameter of Set<Country> d_Neighbors in Country class
     * @return String result of neighbor list
     */
    public String createANeighborList(Set<Country> p_Neighbors) {
        String result = "";
        for (Country l_Neighbor : p_Neighbors) {
            result += l_Neighbor.getName() + " ";
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
    }
    /**
     * Save map into file as continent and country
     * @param p_GameMap parameter o GameMap class
     * @return boolean true if written
     */

    public boolean saveMapIntoFile(GameMap p_GameMap, String name) {
        /**
         * Add continent name and control values
         */
        String mapData = "[Map]\n\n[Continents]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            mapData += continent.getName() + " " + continent.getAwardArmies();
            mapData += "\n";
        }
        /**
         * Add country name, respective continent and neighbor list of countries
         */
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
