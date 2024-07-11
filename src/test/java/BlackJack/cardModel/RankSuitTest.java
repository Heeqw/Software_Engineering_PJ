package BlackJack.cardModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class RankSuitTest {

    @Test
    public void testRankValues() {
        assertEquals(11, Rank.Ace.getNum());
        assertEquals(10, Rank.King.getNum());
        assertEquals("A", Rank.Ace.toString());
        assertEquals("K", Rank.King.toString());
    }

    @Test
    public void testSuitValues() {
        assertEquals(Suit.CLUBS, Suit.valueOf("CLUBS"));
        assertEquals(Suit.DIAMONDS, Suit.valueOf("DIAMONDS"));
        assertEquals(Suit.HEARTS, Suit.valueOf("HEARTS"));
        assertEquals(Suit.SPADES, Suit.valueOf("SPADES"));
    }
}
