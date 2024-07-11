package UNO.cardModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class DeckTest {

    @Test
    public void testDeckInitialization() {
        Deck deck = new Deck();
        assertEquals(108, deck.size()); // 108张牌
    }

    @Test
    public void testDrawCard() {
        Deck deck = new Deck();
        Card card = deck.drawCard();
        assertNotNull(card);
        assertEquals(107, deck.size());
    }

    @Test
    public void testDrawCardFromEmptyDeck() {
        Deck deck = new Deck();
        for (int i = 0; i < 108; i++) {
            deck.drawCard();
        }
        Card card = deck.drawCard();
        assertNull(card);
    }
}
