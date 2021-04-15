package utils;

import java.io.IOException;

import model.GameMap;

public class Adapter extends DominationMap {

    public static final String mapType="Domination";

    Adaptee d_adp = new Adaptee();


    public Adapter(Adaptee adp) {
        this.d_adp = adp;
    }


    public void readMap(GameMap p_GameMap, String p_FileName) throws ValidationException {
        d_adp.readMap(p_GameMap, p_FileName);
    }


    public boolean saveMap(GameMap map, String fileName)  throws IOException {
        return d_adp.saveMap(map, fileName);
    }

}