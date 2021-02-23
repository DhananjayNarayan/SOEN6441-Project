package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import model.Continent;
import model.Country;
import model.GameMap;
import model.Player;
import utils.MapValidation;
import utils.ValidationException;

import java.util.Iterator;
import java.util.ListIterator;

public class MapView {

    public static void showMap(GameMap gameMap) {
        System.out.println("\nShowing the Map Details : \n");

        // Showing the players in game

        HashMap<String, Player> players=gameMap.getPlayers();
        System.out.println("Players: ");
        if(players!=null) {

            players.forEach((key, value) -> System.out.println(key + " : " + value));  // will slightly modify the output after testing with the entire project
            System.out.println();
        }

        // Showing Continents in Map
        System.out.println("\nThe Continents in this Map are : \n");
        Iterator<Entry<String, Continent>> iteratorForContinents = gameMap.getContinents().entrySet()
                .iterator();

        String table = "|%-18s|%n";

        System.out.format("+------------------+%n");
        System.out.format("| Continent's name |%n");
        System.out.format("+------------------+%n");

        while (iteratorForContinents.hasNext()) {
            Map.Entry<String, Continent> continentMap = (Map.Entry<String, Continent>) iteratorForContinents.next();
            String continentId = (String) continentMap.getKey();
            Continent continent = gameMap.getContinents().get(continentId); //Get the particular continent by its ID(Name)

            System.out.format(table, continent.getName());
        }
        System.out.format("+------------------+%n");

    }


    }


