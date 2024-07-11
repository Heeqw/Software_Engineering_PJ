package BlackJack.sessionModel;

import BlackJack.personModel.Player;

import java.util.ArrayList;

public class SimpleMode extends GamingSessions {

    public SimpleMode(ArrayList<Player> players) {
        super(players);
    }

    @Override
    public boolean canDoubleDown(){return false;}

    @Override
    public boolean canPlaceBet() {
        return false;
    }

    @Override
    public boolean canSplitHands() {
        return false;
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
    public boolean playerPlaceBet(Player player, int amount){return false;}

    @Override
    public boolean canPlayerSplitHands(Player player){return false;}

}
