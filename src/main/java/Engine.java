import model.GamePhase;

public interface Engine {
    void start() throws Exception;

    void setGamePhase(GamePhase p_GamePhase);
}
