package model.player;

import java.util.Scanner;

public class HumanStrategy implements PlayerStrategy {

    /**
     * scanner to read from user
     */
    private final static Scanner SCANNER = new Scanner(System.in);

    @Override
    public String createCommand() {
        return SCANNER.nextLine();
    }

}
