package view;

import controller.MapFromConsole;
import model.GameMap;
import utils.SaveMap;
import utils.ValidationException;

public class GameDriver {
    public static void main(String[] args) {

        try {
            GameMap d_GameMap = new GameMap();
            MapFromConsole l_MapFromConsole = new MapFromConsole(d_GameMap);
            l_MapFromConsole.start();
            SaveMap map = new SaveMap(d_GameMap);
            map.saveMapIntoFile(d_GameMap, "Output");
            //validation call goes here
        } catch (ValidationException e) {
            e.getMessage();
        }

    }
}
