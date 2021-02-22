package controller;
import model.*;
import utils.ValidationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ReadDominationFile {
    GameMap d_GameMap;
    public ReadDominationFile(GameMap map) {
        this.d_GameMap = map;
    }
    public void ReadMap(String mapFileName) {

        File file = new File(mapFileName);
        //File file = new File(Maps + "/" + filename + ".map");
        if (!file.exists()) {
            System.out.println(mapFileName + " domination map file not found. Please try again");
        }else {
            //System.out.println("Read Domination File: "+mapFolder + "/" + mapFileName + ".map");
            System.out.println("Read Domination File:  mapFileName");
        }

        Scanner sc = null;
        //HashMap<String,Integer > map = new HashMap<>();
        try {
            sc = new Scanner(file);
            while (sc.hasNext()) {
                String a = sc.nextLine();
                if(a.equals("[Continents]")) {
                    String l_Input = sc.nextLine();
                    List<String> l_InputList = Arrays.stream(l_Input.split("\\s"))
                            .filter(s -> !s.isEmpty())
                            .map(String::trim)
                            .collect(Collectors.toList());
                    if(l_InputList.size() == 2) {
                        d_GameMap.addContinent(l_InputList.get(0), l_InputList.get(1));
                    }
                    System.out.println(d_GameMap.getContinents().get("africa").getName());
                    System.out.println(d_GameMap.getContinents().get("africa").getAwardArmies());
                }
                }
            }
        catch (ValidationException validationException) {
            validationException.printStackTrace();
        }
        catch (FileNotFoundException e) {
            System.out.println("Invalid filename or the file does not exist");
            e.printStackTrace();
        }


    }

}