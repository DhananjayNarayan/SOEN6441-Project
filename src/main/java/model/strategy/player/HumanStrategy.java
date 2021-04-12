package model.strategy.player;

import java.io.Serializable;
import java.util.Scanner;

/**
 * Human Strategy class, taking command inputs from human player.
 *
 * @author Madhuvanthi Hemanathan
 * @version 1.0.0
 */
public class HumanStrategy implements PlayerStrategy, Serializable {

    /**
     * scanner to read from user
     */
    private final static Scanner SCANNER = new Scanner(System.in);

    /**
     * Function to read and return the next input of user
     *
     * @return next input of the user
     */
    @Override
    public String createCommand() {
        return SCANNER.nextLine();
    }

}
