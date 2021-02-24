package model.order;

import model.Player;

public class OrderCreater {

    public static Order createOrder(String[] p_commands, Player player){
        String l_Type = p_commands[0].toLowerCase();
        Order l_Order;
        switch (l_Type) {
            case "deploy":
                l_Order = new DeployOrder();
                l_Order.setOrderInfo(generateDeployOrderInfo(p_commands, player));
                break;
            default:
                System.out.println("\nFail to create an order due to invalid arguments");
                l_Order = new Order();
        }
        return l_Order;
    }
    private static OrderInfo generateDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);

        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setDestination(l_CountryID);
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmy);
        return l_OrderInfo;
    }

}
