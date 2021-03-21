package model.order;

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
        // check if player has the card
        //check if player is valid and not itself
        //if all true then add the connections
        return false;
    }

    @Override
    public void printOrderCommand() {
        System.out.println("Negotiated with " + getOrderInfo().getPlayer() + ".");
        System.out.println("---------------------------------------------------------------------------------------------");
    }
}
