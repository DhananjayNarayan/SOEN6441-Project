package model.order;

import utils.LogEntryBuffer;

public class NegotiateOrder extends Order {

    LogEntryBuffer d_leb = new LogEntryBuffer();
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
        // check if player has the card
        //check if player is valid and not itself
        //if all true then add the connections
        return false;
    }

    @Override
    public void printOrderCommand() {
        System.out.println("Negotiated with " + getOrderInfo().getPlayer() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
        d_leb.logInfo("Negotiated with " + getOrderInfo().getPlayer() + ".");
    }
}
