package model.order;

import model.Card;
import model.Country;
import model.GameSettings;
import model.Player;
import model.strategy.GameStrategy;
import utils.LogEntryBuffer;

import java.util.Objects;

public class AdvanceOrder extends Order {

    LogEntryBuffer LOGGER = new LogEntryBuffer();
    GameSettings SETTINGS = GameSettings.getInstance();
    GameStrategy d_gameStrategy;

    /**
     * Constructor for class AdvanceOrder
     */
    public AdvanceOrder() {
        super();
        setType("advance");
        d_gameStrategy = SETTINGS.getStrategy();
    }

    @Override
    public boolean execute() {
        Player l_Player = getOrderInfo().getPlayer();
        Country l_From = getOrderInfo().getDeparture();
        Country l_To = getOrderInfo().getDestination();
        int l_Armies = getOrderInfo().getNumberOfArmy();
        if (l_Player.isCaptured(l_From) && l_From.isNeighbor(l_To)) {
            if (l_Player.isCaptured(l_To) || Objects.isNull(l_To.getPlayer())) {
                l_From.depleteArmies(l_Armies);
                l_To.deployArmies(l_Armies);
                /*
                 * captured countries are of Set datatype. So no need to check if it is not captured (contains in the captured list)
                 */
                l_Player.getCapturedCountries().add(l_To);
                l_To.setPlayer(l_Player);
                System.out.println("Advanced/Moved "+ l_Armies +" from " + l_From +" to "+ l_To);
            } else {
                if (d_gameStrategy.attack(l_Player, l_From, l_To, l_Armies)) {
                    l_Player.addPlayerCard(new Card());
                    System.out.println("Attacker: "+ l_Player +" received a card");
                }
            }
        }
        System.out.println("---------------------------------------------------");
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
