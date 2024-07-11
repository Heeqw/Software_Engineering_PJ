package BlackJack.sessionModel;

import BlackJack.cardModel.Card;
import BlackJack.cardModel.Hand;
import BlackJack.personModel.Player;

import java.util.ArrayList;

//赌徒模式：有下注、分牌功能；无保险、投降
public class GamblerMode extends GamingSessions{

    public GamblerMode(ArrayList<Player> players) {
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
        return false;
    }

    @Override
    public boolean canSurrender() {
        return false;
    }

    @Override
    public boolean playerPlaceBet(Player player, int amount) {
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
