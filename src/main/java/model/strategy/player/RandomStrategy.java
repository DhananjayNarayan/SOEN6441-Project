package model.strategy.player;

import model.*;
import model.order.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Random Strategy class, taking random commands for tournament mode.
 *
 * @author Neona Pinto
 * @version 1.0.0
 */
public class RandomStrategy extends PlayerStrategy implements  Serializable {
    /**
     * Random variable
     */
    private static final Random d_Random = new Random();
    /**
     * GameMap instance
     */
    private static final GameMap d_GameMap = GameMap.getInstance();

    /**
     * Constructor
     *
     * @param p_Player Player
     */
    RandomStrategy(Player p_Player) {
        super(p_Player);
    }

    /**
     * Get a random player other than itself
     *
     * @param p_Player current player
     * @return Random Player
     */
    protected  Player getRandomPlayer(Player p_Player){
        int l_Index = d_Random.nextInt(d_GameMap.getPlayers().size());
        Player l_Player = (Player) d_GameMap.getPlayers().values().toArray()[l_Index];
        while(l_Player.equals(p_Player)){
            l_Index = d_Random.nextInt(d_GameMap.getPlayers().size());
            l_Player = (Player) d_GameMap.getPlayers().values().toArray()[l_Index];
        }
        return l_Player;
    }

    /**
     * Get Random Country not belonging to the player
     *
     * @param p_Player current player
     * @return Random country
     */
    protected  Country getRandomUnconqueredCountry(Player p_Player) {
        int l_Index = d_Random.nextInt(d_GameMap.getCountries().size());
        Country l_RandomCountry=(Country) d_GameMap.getCountries().values().toArray()[l_Index];
        while(l_RandomCountry.getPlayer().equals(p_Player)){
            l_Index = d_Random.nextInt(d_GameMap.getCountries().size());
            l_RandomCountry=(Country) d_GameMap.getCountries().values().toArray()[l_Index];
        }
        return l_RandomCountry;
    }

    /**
     * Random country belonging to the player
     *
     * @param p_Player current player
     * @return random country
     */
    protected  Country getRandomConqueredCountry(Player p_Player) {
        int l_Index = d_Random.nextInt(p_Player.getCapturedCountries().size());
        return p_Player.getCapturedCountries().get(l_Index);
    }

    /**
     * Get the random neighbor of the country
     *
     * @param p_CurrentCountry current country
     * @return  random neighbor
     */
    protected  Country getRandomNeighbor(Country p_CurrentCountry) {
        if(p_CurrentCountry.getNeighbors().size()==0)
            return null;
        int l_Index = d_Random.nextInt(p_CurrentCountry.getNeighbors().size());
        return (Country) p_CurrentCountry.getNeighbors().toArray()[l_Index];
    }

    /**
     * Create the orders in random fashion
     *
     * @return command, the orders on creation
     */
    public String createCommand() {
        Order l_Order = null;
        List<String> l_Commands = new ArrayList<>();
        String[] l_CommandsArr;

        //check if player can still play
        int l_Random = d_Random.nextInt(3);
        switch(l_Random){
            case 0:
                l_Commands.add(0,"deploy");
                l_Commands.add(1,getRandomConqueredCountry(d_Player).getName());
                l_Commands.add(2,String.valueOf(d_Random.nextInt(d_Player.getReinforcementArmies()))) ;
                l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                l_Order = new DeployOrder();
                l_Order.setOrderInfo(OrderCreater.GenerateDeployOrderInfo(l_CommandsArr, getRandomPlayer(d_Player)));
                break;
            case 1:
                Country l_RandomConqueredCountry = getRandomConqueredCountry(d_Player);
                Country l_RandomNeighbor = getRandomNeighbor(l_RandomConqueredCountry);
                if(l_RandomNeighbor!=null) {
                    l_Commands.add(0, "advance");
                    l_Commands.add(1,l_RandomConqueredCountry.getName());
                    l_Commands.add(2,l_RandomNeighbor.getName());
                    l_Commands.add(3,String.valueOf(d_Random.nextInt(l_RandomConqueredCountry.getArmies() + 10))) ;
                    l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                    l_Order = new AdvanceOrder();
                    l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_CommandsArr, getRandomPlayer(d_Player)));
                }
                break;
            case 2:
                if(d_Player.getPlayerCards().size() <= 0)
                    return null;
                int l_RandomCardIdx = d_Random.nextInt(d_Player.getPlayerCards().size());
                Card l_Card = d_Player.getPlayerCards().get(l_RandomCardIdx);
                switch(l_Card.getCardType()){
                    case BLOCKADE:
                        l_Commands.add(0,"blockade");
                        l_Commands.add(1,getRandomConqueredCountry(d_Player).getName());
                        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                        l_Order = new BlockadeOrder();
                        l_Order.setOrderInfo(OrderCreater.GenerateBlockadeOrderInfo(l_CommandsArr, d_Player));
                        break;
                    case BOMB:
                        l_Commands.add(0, "bomb");
                        l_Commands.add(1,getRandomUnconqueredCountry(d_Player).getName());
                        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                        l_Order = new BombOrder();
                        l_Order.setOrderInfo(OrderCreater.GenerateBombOrderInfo(l_CommandsArr, d_Player));
                        break;
                    case AIRLIFT:
                        l_Commands.add(0, "airlift");
                        l_Commands.add(1,getRandomConqueredCountry(d_Player).getName());
                        l_Commands.add(2,getRandomConqueredCountry(d_Player).getName());
                        l_Commands.add(3, String.valueOf(d_Random.nextInt(10)));
                        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                        l_Order = new AirliftOrder();
                        l_Order.setOrderInfo(OrderCreater.GenerateAirliftOrderInfo(l_CommandsArr, d_Player));
                        break;
                    case DIPLOMACY:
                        l_Commands.add(0, "negotiate");
                        l_Commands.add(1,getRandomPlayer(d_Player).getName());
                        l_CommandsArr = l_Commands.toArray(new String[l_Commands.size()]);
                        l_Order = new NegotiateOrder();
                        l_Order.setOrderInfo(OrderCreater.GenerateNegotiateOrderInfo(l_CommandsArr, d_Player));
                        break;
                }
                break;
        }
        if(l_Order != null){
            return l_Order.getOrderInfo().getCommand();
        }
        return null;
    }
}
