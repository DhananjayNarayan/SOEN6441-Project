package controller;
import utils.ValidationException;
import java.io.File;
import java.io.FileNotFoundException;
import model.GameMap;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReadDominationFile {
    GameMap d_Map = new GameMap();
    Map<String, List<String>> l_CountryNeighbors = new HashMap<>();

    public void ReadMap(String mapFileName) {
        try {
            File file = new File(mapFileName);
            FileReader fileReader = new FileReader(file);
            Map<String, List<String>> l_MapFileContents = new HashMap<>();
            String l_CurrentKey = "";
            BufferedReader bf = new BufferedReader(fileReader);
            while (bf.ready()) {
                String s = bf.readLine();
                if (!s.isEmpty()) {
                    if (s.contains("[") && s.contains("]")) {
                        l_CurrentKey = s.replace("[", "").replace("]", "");
                        l_MapFileContents.put(l_CurrentKey, new ArrayList<>());
                    } else {
                        l_MapFileContents.get(l_CurrentKey).add(s);
                    }
                }
            }
            readContinentsFromFile(l_MapFileContents.get("Continents"));
            readCountriesFromFile(l_MapFileContents.get("Territories"));
            addNeighborsFromFile(l_CountryNeighbors);
        } catch (ValidationException | FileNotFoundException e) {
            e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readContinentsFromFile(List<String> p_ContinentArray) throws ValidationException {
        for (String l_InputString : p_ContinentArray) {
            String[] l_InputArray = l_InputString.split(" ");
            if (l_InputArray.length == 2) {
                d_Map.addContinent(l_InputArray[0], l_InputArray[1]);
            }
        }
    }

    public void readCountriesFromFile(List<String> p_Countryarray) throws ValidationException {
        for (String l_InputString : p_Countryarray) {
            List<String> l_InputArray = Arrays.stream(l_InputString.split(" ")).collect(Collectors.toList());
            if (l_InputArray.size() >= 2) {
                d_Map.addCountry(l_InputArray.get(0), l_InputArray.get(1));
                l_CountryNeighbors.put(l_InputArray.get(0), l_InputArray.subList(2, l_InputArray.size()));
            }
        }
    }

    public void addNeighborsFromFile(Map<String, List<String>> p_NeighborList) throws ValidationException {
        for (String l_Country : p_NeighborList.keySet()) {
            for (String l_Neighbor : p_NeighborList.get(l_Country)) {
                d_Map.addNeighbor(l_Country, l_Neighbor);
            }
        }
    }
}
