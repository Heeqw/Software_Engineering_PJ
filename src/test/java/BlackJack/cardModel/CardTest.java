package BlackJack.cardModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {

    @Test
    public void testCardCreation() {
        Card card = new Card(Rank.Ace, Suit.CLUBS);
        assertEquals(Rank.Ace.getNum(), card.getRank());
        assertEquals(Suit.CLUBS, card.getSuit());
    }

    @Test
    public void testCardToString() {
        Card card = new Card(Rank.King, Suit.HEARTS);
        assertEquals("K of HEARTS", card.toString());
    }
}
