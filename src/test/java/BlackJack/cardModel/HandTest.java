package BlackJack.cardModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void testAddCard() {
        Hand hand = new Hand();
        Card card = new Card(Rank.Ace, Suit.CLUBS);
        hand.addCard(card);
        assertEquals(1, hand.getCards().size());
    }

    @Test
    public void testCalculateTotalPoints() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.Ace, Suit.CLUBS));
        hand.addCard(new Card(Rank.King, Suit.DIAMONDS));
        assertEquals(21, hand.calculateTotalPoints());
    }

    @Test
    public void testBlackJack() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.Ace, Suit.CLUBS));
        hand.addCard(new Card(Rank.King, Suit.DIAMONDS));
        assertTrue(hand.isBlackJack());
    }

    @Test
    public void testSurrender() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.Seven, Suit.CLUBS));
        hand.addCard(new Card(Rank.Nine, Suit.DIAMONDS));
        hand.surrender();
        assertTrue(hand.isSurrendered());
    }
}
