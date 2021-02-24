package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.Player;
import model.order.Order;

import java.util.Scanner;

public class ExecuteOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.ExitGame;
    GamePhase d_GamePhase = GamePhase.ExecuteOrder;
    GameMap d_GameMap;

    public ExecuteOrder(){
        d_GameMap = GameMap.getInstance();
    }

    @Override
    public GamePhase start(GamePhase p_GamePhase) throws Exception {
        d_GamePhase = p_GamePhase;
        Boolean d_Temp = null;
        d_Temp = ExecuteOrders();
        return p_GamePhase.nextState(d_NextGamePhase);
    }
    private boolean ExecuteOrders()
    {
        for (Player player:d_GameMap.getPlayers().values()){
            while (player.nextOrder()!=null){
                Player l_Order = player.nextOrder().getOrderInfo().getPlayer();
            }
        }
        return true;
    }
}
