package model.order;

import model.GameMap;
import model.Player;
import utils.LogEntryBuffer;

public class NegotiateOrder extends Order {
    private GameMap d_GameMap;
    LogEntryBuffer d_leb = new LogEntryBuffer();

    /**
     * Constructor for class Negotiate Order
     */
    public NegotiateOrder() {
        super();
        setType("negotiate");
        d_GameMap = GameMap.getInstance();
    }

    /**
     * execute the Negotiate Order
     *
     * @return true if the execute was successful else false
     */
    @Override
    public boolean execute() {
        String l_PlayerName = getOrderInfo().getPlayerName();
        System.out.println("The order: " + getType() + " " + l_PlayerName);
        if(validateCommand()){
            Player l_Player = getOrderInfo().getPlayer();
            l_Player.addNeutralPlayers(l_Player);
        }
        return false;
    }

    /**
     * Validate the command
     *
     * @return true if successful or else false
     */
    @Override
    public boolean validateCommand() {
        Player l_Player = getOrderInfo().getPlayer();
        String l_PlayerName = getOrderInfo().getPlayerName();
        if(l_PlayerName == null || l_Player == null){
            System.out.println("The Player is not valid.");
            return false;
        }
        if(!d_GameMap.getPlayers().keySet().contains(l_PlayerName)){
            System.out.println("The Player name doesn't exist.");
            return false;
        }
        if(!l_Player.getPlayerCards().contains(getType())){
            System.out.println("Player doesn't have the card to be used.");
            return false;
        }
        return true ;
    }

    /**
     * Print the command
     *
     */
    @Override
    public void printOrderCommand() {
        System.out.println("Negotiated with " + getOrderInfo().getPlayerName() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_leb.logInfo("Negotiated with" + getOrderInfo().getPlayerName() + ".");
    }
}
