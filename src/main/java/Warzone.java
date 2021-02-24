import model.GameController;
import model.GamePhase;
import utils.InvalidExecutionException;
import utils.ValidationException;

import java.util.Objects;

public class Warzone {
    GamePhase d_GamePhase = GamePhase.MapEditor;

    public static void main(String[] args) {
        new Warzone().start();
    }

    public void start() {
        try {
            if (!d_GamePhase.equals(GamePhase.ExitGame)) {
                GameController gc = d_GamePhase.getController();
                if(Objects.isNull(gc)) {
                    throw new Exception("No Controller found");
                }
                d_GamePhase = gc.start(d_GamePhase);
                System.out.println(d_GamePhase);
                start();
            }
        } catch (ValidationException | InvalidExecutionException e) {
            System.out.println(e.getMessage());
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
