package model.order;

import model.GameMap;
import utils.LogEntryBuffer;

/**
 * This class implements the bomb order card
 * 
 * @author Prathika 
 */
public class BombOrder extends Order{

    private GameMap d_GameMap;
    LogEntryBuffer d_leb = new LogEntryBuffer();

    public BombOrder() {
        super();
        setType("bomb");
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public boolean validateCommand() {
        return false;
    }

    @Override
    public void printOrderCommand() {

    }
}
