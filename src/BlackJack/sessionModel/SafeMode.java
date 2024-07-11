package BlackJack.sessionModel;

import BlackJack.cardModel.*;
import BlackJack.personModel.*;

import java.util.ArrayList;


public class SafeMode extends GamingSessions{

    public SafeMode(ArrayList<Player> players) {
        super(players);
    }

    @Override
    public boolean canDoubleDown(){return true;}
    @Override
    public boolean canPlaceBet() {
        return true;
    }

    @Override
    public boolean canSplitHands() {
        return true;
    }

    @Override
    public boolean canTakeInsurance() {
        return true;
    }

    @Override
    public boolean canSurrender() {
        return true;
    }

    //具体方法
    @Override
    public boolean playerPlaceBet(Player player, int amount) {
        //Player player = this.players.get(playerIndex);
        if(!player.setBet(amount)){
            System.out.println("玩家余额不足下注失败！");
            return false;
        }
        return true;
    }

    @Override
    public boolean canPlayerSplitHands(Player player){
        ArrayList<Hand> handList = player.getHandList();
        Card firstCard = handList.get(0).getOneCard(0);
        Card secondCard = handList.get(0).getOneCard(1);
        return (!player.isSplit() && firstCard.toString().equals(secondCard.toString()));//如果玩家可以分牌则返回true
    }

}
