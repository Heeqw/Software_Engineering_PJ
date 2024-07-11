package BlackJack.sessionModel;
import BlackJack.personModel.*;

public interface GameSessionStrategy {
    //下注
    public boolean playerPlaceBet(Player player,int amount);

    //判断玩家当前是否可以分牌后，询问是否分牌
    public boolean canPlayerSplitHands(Player player);
}
