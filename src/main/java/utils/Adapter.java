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
    public static final String MapType = "Domination";

    Adaptee d_Adp = new Adaptee();

    /**
     * Constructor to initialize adaptee object
     * @param adp Object of adaptee class
     */
    public Adapter(Adaptee adp) {
        this.d_Adp = adp;
    }

    /**
     * This method loads the map file
     * @param p_GameMap  the game map
     * @param p_FileName the map file name
     * @throws ValidationException files exception
     */

    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_Adp.readMap(p_GameMap, p_FileName);
    }

    /**
     *Saves the input map to a given file name. Overwrites any existing map with the same name.
     * The map will only save if it is valid.
     * @param p_Map The map to save.
     * @param p_FileName The name of the file to save to, including the extension.
     * @return Whether the file was successfully saved.
     * @throws IOException files exception
     */
    public boolean saveMap(GameMap p_Map, String p_FileName)  throws IOException {
        return d_Adp.saveMap(p_Map, p_FileName);
    }

}