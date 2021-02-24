package utils;

import java.io.PrintWriter;
import java.util.Set;

import model.Continent;
import model.Country;
import model.GameMap;

public class SaveMap {

    private GameMap gameMap;

    public String createANeighborList(Set<Country> p_Neighbors){
        String result = "";
        for (Country l_Neighbor : p_Neighbors ){
            result += l_Neighbor.getName() + " ";
        }
        return result.length() > 0 ? result.substring(0, result.length() - 1): "";
    }


    public boolean saveMapIntoFile(GameMap p_GameMap, String name) {
        String mapData = "[Map]\n\n[Continents]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            mapData += continent.getName() + " " + continent.getAwardArmies();
            mapData += "\n";
        }
        mapData += "[Territories]\n";
        for (Continent continent : p_GameMap.getContinents().values()) {
            for (Country country :p_GameMap.getCountries().values()) {
                mapData += country.getName() + " " + country.getContinent() + " " + createANeighborList(country.getNeighbors()) + "\n";
            }

            PrintWriter writeData = null;{
                try {
                    writeData = new PrintWriter(name+".map");
                    writeData.println(mapData);
                    return true;
                }
                catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    return false;
                }
                finally{
                    writeData.close();
                }
            }

        }
        return true;

    }
}
