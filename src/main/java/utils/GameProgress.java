package utils;
import model.GameMap;
import java.io.*;
import java.util.Scanner;

/**
 *  A class to save and load game progress
 *  @author Neona Pinto
 *  @version 1.0.0
 *
 */
public class GameProgress {
    static final String PATH = "savedFiles/";

    /**
     * A function to save the game progress
     *
     * @param p_GameMap instance of the game
     * @param p_Name file name
     */
    public static void SaveGameProgress(GameMap p_GameMap, String p_Name){
        try {
            FileOutputStream l_Fs = new FileOutputStream(PATH + p_Name + ".bin");
            ObjectOutputStream l_Os = new ObjectOutputStream(l_Fs);
            l_Os.writeObject(p_GameMap);
            System.out.println("The game has been saved successfully.");
            l_Os.flush();
            l_Fs.close();
        } catch(Exception p_Exception) {
            System.out.println(p_Exception);
        }
    }

    /**
     * A file to load the game progress
     *
     * @param p_Filename the file name string
     */
    public static void LoadGameProgress(String p_Filename){
        FileInputStream l_Fs = null;
        try {
            l_Fs = new FileInputStream(PATH + p_Filename);
            ObjectInputStream l_Os = new ObjectInputStream(l_Fs);
            GameMap l_LoadedGameMap = (GameMap) l_Os.readObject();
            GameMap.getInstance().gamePlayBuilder(l_LoadedGameMap);
            System.out.println("The game is loaded successfully will continue where it last stopped.");
            l_Os.close();
        } catch (FileNotFoundException p_Exception) {
            System.out.println("The file could not be loaded.");
            p_Exception.printStackTrace();
        } catch (IOException | ClassNotFoundException p_Exception) {
            System.out.println("The file could not be loaded.");
            p_Exception.printStackTrace();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

}
