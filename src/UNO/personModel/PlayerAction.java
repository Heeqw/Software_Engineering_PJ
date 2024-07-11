package UNO.personModel;

public enum PlayerAction {
    DISCARD("discard"),// 出牌
    DRAW("draw"),// 摸牌
    UNO("uno"),// 喊uno
    REPORT("report");// 举报

    private final String action;

    PlayerAction(String action) {

        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}