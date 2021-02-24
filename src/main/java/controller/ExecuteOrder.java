package controller;

import model.GameController;
import model.GameMap;
import model.GamePhase;
import model.Player;
import model.order.Order;

import java.util.List;
import java.util.Scanner;

public class ExecuteOrder implements GameController {
    GamePhase d_NextGamePhase = GamePhase.ExitGame;
    GamePhase d_GamePhase = GamePhase.ExecuteOrder;
    GameMap d_GameMap;
    Order d_Order;

    public ExecuteOrder(){
        d_GameMap = GameMap.getInstance();
        d_Order = Order.getInstance();
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
        List<Order> l_OrderList = d_Order.getOrderList();
        System.out.println(l_OrderList);
        return true;
    }
}
