package UNO.cardModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void testAddAndRemoveCard() {
        Hand hand = new Hand();
        Card card1 = new NumberCard(Color.RED, "5");
        Card card2 = new ActionCard(Color.BLUE, "SKIP");

        hand.addCard(card1);
        hand.addCard(card2);

        assertEquals(2, hand.size());

        hand.removeCard(card1);
        assertEquals(1, hand.size());

        assertEquals(card2, hand.getCard(0));
    }

    @Test
    public void testClearHand() {
        Hand hand = new Hand();
        Card card = new NumberCard(Color.GREEN, "7");

        hand.addCard(card);
        assertEquals(1, hand.size());

        hand.clear();
        assertEquals(0, hand.size());
    }

    @Test
    public void testGetTotalPoints() {
        Hand hand = new Hand();
        hand.addCard(new NumberCard(Color.RED, "5"));
        hand.addCard(new ActionCard(Color.BLUE, "SKIP"));

        assertEquals(25, hand.getTotal_points());
    }
}
