package view;

import controller.MapFromConsole;
import model.GameMap;
import utils.ValidationException;

public class GameDriver {
    public static void main(String[] args) {
        try {
            GameMap map = new GameMap();
            MapFromConsole l_MapFromConsole = new MapFromConsole(map);
            l_MapFromConsole.start();
            //validation call goes here
        } catch (ValidationException e) {
            e.getMessage();
        }
    }
}
