package BlackJack.cardModel;

public class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    public int getRank() {
        return rank.getNum();
    }

    public Suit getSuit() {
        return suit;
    }
}
