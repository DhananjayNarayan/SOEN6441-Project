package utils.logger;
import utils.Observer;
import java.io.Serializable;

public class ConsoleWriter implements Observer, Serializable {

    @Override
    public void update(String p_s) {
        System.out.println(p_s);
    }

    @Override
    public void clearLogs() {
        System.out.print("\033[H\033[2J");
    }
}
