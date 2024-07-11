package BlackJack.personModel;

public enum PlayerAction {
    HIT("hit"),
    STAND("stand"),
    DOUBLE_DOWN("double"),
    SPLIT("split"),
    INSURANCE("insurance"),
    SURRENDER("surrender");

    private final String action;

    PlayerAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}
