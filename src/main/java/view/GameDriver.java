package view;

import controller.MapFromConsole;
import controller.ReadDominationFile;
import model.GameMap;
import utils.ValidationException;

public class GameDriver {
    public static void main(String[] args) {
        try {
            GameMap map = new GameMap();
            MapFromConsole l_MapFromConsole = new MapFromConsole(map);
//            l_MapFromConsole.start();
            ReadDominationFile l_readFile = new ReadDominationFile(map);
            l_readFile.ReadMap("trialMap.map");
            //validation call goes here
        } catch(Exception e) {
            e.getMessage();
        }
    }
}
