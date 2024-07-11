package UNO.cardModel;


public class Card {
    private Color color;
    private Type type;
    private int score;
    private String detail;

    public Card(Color color, Type type, int score, String detail) {
        this.color = color;
        this.type = type;
        this.score = score;
        this.detail = detail;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    public String getDetail() {
        return detail;
    }

}
