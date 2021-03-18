package utils;

import model.Continent;
import model.Country;
import model.GameMap;

import java.util.*;

/**
 * The class is used to create the map validation to check the if the map is a map which is strongly connected
 * or not and to check if there are any continents without country or countries without any neighbors.
 * @author Neona Pinto
 * @version 1.0.0
 */
public class MapValidation {

    /**
     * A function to check if the continent is empty or not.
     *
     * @param p_GameMap The GameMap object which contains all the data
     * @return true if continent is empty else return false
     */
    public static boolean checkIfContinentIsEmpty(GameMap p_GameMap){
        if(p_GameMap.getContinents().isEmpty()){
            return true;
        }
        else{
            for(Continent l_Continent : p_GameMap.getContinents().values()){
                if(l_Continent.getCountries().isEmpty()){
                   System.out.println("Continent " + l_Continent.getName() + " has no countries");
                   return true;
                }
            }
        }
        return false;
    }

    /**
     * A function to check if the country count is more than the minimum requirement
     *
     * @param p_GameMap The GameMap object which contains all the data
     * @param p_CountryCount The Minimum Number of countries required
     * @return true if the number of countries are equal to or more than minimum requirement else false
     */
    private static boolean checkCountryCount(GameMap p_GameMap, int p_CountryCount){
        if(p_GameMap.getCountries().size() >= p_CountryCount){
            return true;
        }
        else{
            System.out.println("The Number of countries is less than the minimum " + p_CountryCount + " , Invalid.");
            return false;
        }
    }

    /**
     * A function to check if the neighbor is a part of the country list
     *
     * @param p_GameMap The GameMap object which contains all the data
     * @return true if the neighbor is a country in the country list else false
     */
    private static boolean checkIfNeighbourExist(GameMap p_GameMap) {
        HashMap<String, Country> l_Countries =  p_GameMap.getCountries();
        List<String> l_ListOfCountries = new ArrayList<>();
        for(String l_Name : l_Countries.keySet()) {
            l_ListOfCountries.add(l_Name.toLowerCase());
        }
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                for (Country l_Neighbour : l_Country.getNeighbors()) {
                    if (!l_ListOfCountries.contains(l_Neighbour.getName().toLowerCase())) {
                        System.out.println("Neighbor is not a part of the countries list - " + l_Neighbour + ":neighbor");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * A function to check for duplicate continents
     *
     * @param p_GameMap The GameMap object which contains all the data
     * @return true if there any duplicates of the continent are present else false
     */
    public static boolean checkDuplicateContinents(GameMap p_GameMap) {
        HashMap<String, Continent> l_ContinentNames = p_GameMap.getContinents();
        HashSet<String> l_ContinentSet = new HashSet<>(l_ContinentNames.keySet());
        ArrayList<String> l_Result = new ArrayList<>(l_ContinentSet);
        if (!(l_Result.size() == l_ContinentNames.size())) {
            System.out.println("There are duplicate continents present in the map.");
            return true;
        }
        return false;
    }

    /**
     * A function to check for duplicate continents
     *
     * @param p_GameMap The GameMap object which contains all the data
     * @return true if there any duplicates of the continent are present else false
     */
    private static boolean checkDuplicateCountries(GameMap p_GameMap) {
        HashMap<String, Country> l_CountryNames = p_GameMap.getCountries();
        HashSet<String> l_Set = new HashSet<>(l_CountryNames.keySet());
        ArrayList<String> l_Result = new ArrayList<>(l_Set);
        if (!(l_Result.size() == l_CountryNames.size())) {
            System.out.println("There are duplicate countries present in the map.");
            return true;
        }
        return false;
    }

    /**
     * A function to check if there are duplicate Neighbors
     *
     * @param p_GameMap The GameMap object which contains all the data
     * @return true if any duplicates are present else false
     */
    public static boolean checkDuplicateNeighbours(GameMap p_GameMap) {
        for (Continent l_Continent : p_GameMap.getContinents().values()) {
            for (Country l_Country : l_Continent.getCountries()) {
                Set<Country> l_Neighbours = l_Country.getNeighbors();
                  if(l_Neighbours.contains(l_Country)) {
                      System.out.println("There are duplicate neighbors present in the map.");
                      return true;
                  }
            }
        }
        return false;
    }


    /**
     * A function to check the Validity of  a given Map
     *
     * @param p_GameMap The GameMap object which contains all the data
     * @param p_Size The Minimum Country required for a player to play
     * @return true if the satisfies all the conditions else false
     */
    public static boolean validateMap(GameMap p_GameMap, int p_Size) {
        if(checkIfContinentIsEmpty(p_GameMap)){
            System.out.println("continent empty");
            return false;
        }
        if(checkDuplicateContinents(p_GameMap)){
            System.out.println("duplicate continent");
            return false;
        }
        if(checkDuplicateCountries(p_GameMap)){
            System.out.println("duplicate countries");
            return false;
        }
        if(checkDuplicateNeighbours(p_GameMap)){
            System.out.println("duplicate neighbors");
            return false;
        }
        if(!checkCountryCount(p_GameMap, p_Size)){
            System.out.println("country size");
            return false;
        }
        if(checkIfNeighbourExist(p_GameMap)){
            if(!checkIfContinentIsConnected(p_GameMap)){
                System.out.println("continent check");
                return false;
            }
            if(!checkIfMapIsConnected(p_GameMap.getCountries())) {
                System.out.println("whole map check");
                return false;
            }
        }
        else{
            return false;
        }
        return true;
    }

    /**
     * Nested Class for checking the Connectivity of the graph
     */
    static class ConnectedGraph {
        private int d_Vertices;
        private ArrayList[] d_Edges;

        ConnectedGraph(int val) {
            this.d_Vertices = val;
            d_Edges = new ArrayList[val];
            for (int l_Index = 0; l_Index < val; ++l_Index) {
                d_Edges[l_Index] = new ArrayList();
            }
        }

        /**
         * A function to add the  directions between nodes (connecting the nodes)
         *
         * @param p_Egde1 The first vertex
         * @param p_Edge2 The second vertex
         */
         void addEdge(int p_Egde1, int p_Edge2) {
            d_Edges[p_Egde1].add(p_Edge2);
        }

        /**
         * A function to perform the DFS Traversal
         *
         * @param p_Node The node where the DFS Traversal starts
         * @param p_Visited The array which holds the boolean value if node visited or not
         */
        private void dfsTraversal(int p_Node, Boolean[] p_Visited) {
            p_Visited[p_Node] = true;
            for (Integer integer : (Iterable<Integer>) d_Edges[p_Node]) {
                int l_NextNode;
                l_NextNode = integer;
                if (!p_Visited[l_NextNode]) {
                    dfsTraversal(l_NextNode, p_Visited);
                }
            }
        }

        /**
         * A function to get the Transpose of the Graph
         *
         * @return the revered graph
         */
        private ConnectedGraph getTranspose() {
            ConnectedGraph l_Graph = new ConnectedGraph(d_Vertices);
            for (int l_Vertex = 0; l_Vertex < d_Vertices; l_Vertex++) {
                for (Integer integer : (Iterable<Integer>) d_Edges[l_Vertex]) {
                    l_Graph.d_Edges[integer].add(l_Vertex);
                }
            }
            return l_Graph;
        }

        /**
         * A function to check if the graph is strongly connected or not
         *
         * @return true if strongly connected else false
         */
        Boolean checkIfStronglyConnected() {
            Boolean[] l_Visited = new Boolean[d_Vertices];
            for (int l_Index = 0; l_Index < d_Vertices; l_Index++) {
                l_Visited[l_Index] = false;
            }
            dfsTraversal(0, l_Visited);

            for (int l_Index = 0; l_Index < d_Vertices;l_Index++) {
                if (!l_Visited[l_Index]) {
                    return false;
                }
            }
            ConnectedGraph l_Graph = getTranspose();
            for (int l_Index= 0;l_Index < d_Vertices; l_Index++) {
                l_Visited[l_Index] = false;
            }

            l_Graph.dfsTraversal(0, l_Visited);

            for (int i = 0; i < d_Vertices; i++) {
                if (!l_Visited[i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * A function to check if the Continent is connected graph
     * @param p_GameMap The GameMap Continent object which contains all the data
     * @return true if continent is strongly connected else false
     */
    public static boolean checkIfContinentIsConnected(GameMap p_GameMap){
        for(Continent l_Continent : p_GameMap.getContinents().values()){
             if(!checkContinent(l_Continent)){
                 return false;
             }
        }
        return true;
    }

    /**
     * utility function to check the continent
     * @param p_Continent Continent object
     * @return true if continent is connected else false
     */
    private static  boolean checkContinent(Continent p_Continent){
        HashMap<String, Country> l_CountriesMap = new HashMap<>();
        Set<Country> l_Countries = p_Continent.getCountries();
        for(Country l_Country : l_Countries){
            l_CountriesMap.put(l_Country.getName(), l_Country);
        }
        return checkIfMapIsConnected(l_CountriesMap);
    }
    /**
     * A function to check if the Whole Map is connected graph
     * @param p_Countries The GameMap Country object which contains all the data
     * @return true if whole map is strongly connected else false
     */
    public static boolean checkIfMapIsConnected(HashMap<String, Country> p_Countries){
        List<String> l_ListOfCountries = new ArrayList<>();
        for(String l_Name : p_Countries.keySet()) {
            l_ListOfCountries.add(l_Name.toLowerCase());
        }

        int l_NoOfVertices = l_ListOfCountries.size();
        ConnectedGraph l_Graph = new ConnectedGraph(l_NoOfVertices);
        int temp = 0;
        for (Map.Entry<String, Country> l_Country : p_Countries.entrySet()) {
            Set<Country> l_Neighbors = l_Country.getValue().getNeighbors();
            for (Country l_Current : l_Neighbors) {
                int l_Index = l_ListOfCountries.indexOf(l_Current.getName().toLowerCase());
                if(l_Index != -1){
                    l_Graph.addEdge(temp, l_Index);
                }
            }
            temp++;
        }
        return checkMapConnectivity(l_Graph);
    }


    /**
     * A function to check the connectivity based on the graph
     *
     * @param p_Graph The Connected class object to check if connected or not
     * @return true if the graph is connected else false
     */
    private static boolean checkMapConnectivity(ConnectedGraph p_Graph) {
        if (p_Graph.checkIfStronglyConnected()) {
            return true;
        }
        else {
            return false;
        }
    }
}
