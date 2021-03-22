package model.order;

import model.Card;
import model.Player;

import java.util.List;

public class NegotiateOrder extends Order {
    /**
     * Constructor for class Negotiate Order
     */
    public NegotiateOrder() {
        super();
        setType("negotiate");
    }

    @Override
    public boolean execute() {
        return false;
    }

    @Override
    public boolean validateCommand() {
        String l_Card = getOrderInfo().getTheCard();
        Player l_Player = getOrderInfo().getPlayer();
        String l_PlayerName = getOrderInfo().getPlayerName();
        if(l_Player.getPlayerCards().contains(l_Card)){
            if(l_PlayerName == null || l_Player == null){
                System.out.println("The Player is not valid.");
            }
            //check if player id is valid?

        }
        return false;
    }

    @Override
    public void printOrderCommand() {
        System.out.println("Negotiated with " + getOrderInfo().getPlayer() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
    }
}
