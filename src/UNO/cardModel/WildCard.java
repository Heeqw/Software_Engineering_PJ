package UNO.cardModel;

// 万能牌类，继承自卡片类
public class WildCard extends Card {
    public WildCard(String detail) {
        super(Color.BLACK, Type.WILD, 50, detail);
    }

    @Override
    public String toString() {
        return "WildCard" +
                ": " + getColor() +
                ", " + getDetail() ;
    }
}
