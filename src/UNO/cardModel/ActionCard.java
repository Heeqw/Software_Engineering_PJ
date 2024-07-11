package UNO.cardModel;

public class ActionCard extends Card {
    public ActionCard(Color color, String detail) {
        super(color, Type.ACTION, 20, detail);
    }

    @Override
    public String toString() {
        return "ActionCard" +
                ": " + getColor() +
                ", " + getDetail() ;
    }
}



