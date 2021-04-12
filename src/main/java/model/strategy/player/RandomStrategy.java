package model.strategy.player;

import model.Country;
import model.GameMap;
import model.Player;
import model.order.*;

import java.io.Serializable;
import java.util.Random;

/**
 * Random Strategy class, taking random commands for tournament mode.
 *
 * @author Neona Pinto
 * @version 1.0.0
 */
public class RandomStrategy implements PlayerStrategy, Serializable {
    /**
     * Random variable
     */
    private static Random d_Random = new Random();
    private GameMap d_GameMap = GameMap.getInstance();

    protected Player getRandomPlayer(){
        int l_Index = d_Random.nextInt(d_GameMap.getPlayers().size());
        Player l_Player = (Player) d_GameMap.getPlayers().values().toArray()[l_Index];
//        while(l_Player.equals(d_player)){
//            l_Index = d_Random.nextInt(d_GameMap.getPlayers().size());
//            l_Player = (Player) d_GameMap.getPlayers().values().toArray()[l_Index];
//        }
        return l_Player;
    }

    protected Country getRandomUnconqueredCountry() {
        int l_idx=d_Random.nextInt(d_GameMap.getCountries().size());
        Country l_randomCountry=(Country) d_GameMap.getCountries().values().toArray()[l_idx];
//        while(l_randomCountry.getPlayer().equals(d_player)){
//            l_idx=d_Random.nextInt(d_GameMap.getCountries().size());
//            l_randomCountry=(Country) d_GameMap.getCountries().values().toArray()[l_idx];
//        }
        return l_randomCountry;
    }

    protected Country getRandomConqueredCountry() {
//        int l_idx=d_Random.nextInt(d_player.getConqueredCountries().size());
//        Country l_randomCountry=(Country) d_player.getConqueredCountries().values().toArray()[l_idx];
//        return l_randomCountry;
        return null;
    }

    protected Country getRandomNeighbor(Country p_currentCountry) {
        if(p_currentCountry.getNeighbors().size()==0)
            return null;
        int l_idx=d_Random.nextInt(p_currentCountry.getNeighbors().size());
        Country l_randNeighbor=(Country) p_currentCountry.getNeighbors().toArray()[l_idx];
        return l_randNeighbor;
    }


    /**
     * Create the orders in random fashion
     *
     * @return command, the orders on creation
     */
    @Override
    public String createCommand() {
        Order l_Order = null;
        String[] l_Commands = null;
        //check if player can still play
        int l_Random = d_Random.nextInt(3);
        switch(l_Random){
            case 1:
                l_Order = new DeployOrder();
                l_Commands[1] = getRandomConqueredCountry().getName();
                l_Commands[2] = String.valueOf(d_Random.nextInt(10));
                l_Order.setOrderInfo(OrderCreater.GenerateDeployOrderInfo(l_Commands, getRandomPlayer()));
                break;
            case 2:
                Country l_randomConqueredCountry = getRandomConqueredCountry();
                Country l_randomNeighbor = getRandomNeighbor(l_randomConqueredCountry);
                if(l_randomNeighbor!=null) {
                    l_Commands[1] = l_randomConqueredCountry.getName();
                    l_Commands[2] = l_randomNeighbor.getName();
                    l_Commands[3] = String.valueOf(d_Random.nextInt(l_randomConqueredCountry.getArmies() + 10));
                    l_Order = new AdvanceOrder();
                    l_Order.setOrderInfo(OrderCreater.GenerateAdvanceOrderInfo(l_Commands, getRandomPlayer()));
                }
                break;
            case 3:
//                if(d_player.getCards().size() <= 0)
//                    return null;
//                int l_randomCardIdx = d_Random.nextInt(d_player.getCards().size());
//                Card l_card = d_player.getCards().get(l_randomCardIdx);
//                switch(l_card){
//                    case BLOCKADE:
//                        l_Order = new BlockadeOrder(d_player, getRandomConqueredCountry());
//                        break;
//                    case BOMB:
//                        l_order = new BombOrder(d_player, getRandomUnconqueredCountry());
//                        break;
//                    case AIRLIFT:
//                        l_order = new AirliftOrder(d_player, getRandomConqueredCountry(), getRandomConqueredCountry(),l_rand.nextInt(10));
//                        break;
//                    case NEGOTIATE:
//                        l_order = new NegotiateOrder(d_player, getRandomPlayer());
//                        break;
//                }
                break;
        }
        return l_Order.getOrderInfo().getCommand();
    }
}
