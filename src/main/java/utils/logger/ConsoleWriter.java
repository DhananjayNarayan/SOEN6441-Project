package utils.logger;

import utils.Observer;

public class ConsoleWriter implements Observer {

    @Override
    public void update(String p_s) {
        System.out.println(p_s);
    }

    @Override
    public void clearLogs() {
        System.out.print("\033[H\033[2J");
    }
}
