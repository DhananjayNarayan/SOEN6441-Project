package utils;

import java.io.IOException;

import model.GameMap;
/**
 * @author surya manian
 * This class is used to implement Adapter Pattern
 *
 */
public class Adapter extends DominationMap {
    /**
     *  target map type
     */
    public static final String mapType="Domination";

    Adaptee d_adp = new Adaptee();

    /**
     * Constructor to initialize adaptee object
     * @param adp Object of adaptee class
     */
    public Adapter(Adaptee adp) {
        this.d_adp = adp;
    }

    /**
     * This method loads the map file
     * @param p_GameMap  the game map
     * @param p_FileName the map file name
     * @throws ValidationException files exception
     */

    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_adp.readMap(p_GameMap, p_FileName);
    }

    /**
     *Saves the input map to a given file name. Overwrites any existing map with the same name.
     * The map will only save if it is valid.
     * @param map The map to save.
     * @param fileName The name of the file to save to, including the extension.
     * @return Whether the file was successfully saved.
     * @throws IOException files exception
     */
    public boolean saveMap(GameMap map, String fileName)  throws IOException {
        return d_adp.saveMap(map, fileName);
    }

}