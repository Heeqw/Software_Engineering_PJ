package BlackJack.sessionModel;

public interface CheckSessionMode {
    public boolean canDoubleDown();
    public boolean canPlaceBet();
    public boolean canSplitHands();
    public boolean canTakeInsurance();
    public boolean canSurrender();
}
