package BlackJack.personModel;

import BlackJack.cardModel.Card;
import BlackJack.cardModel.Rank;
import BlackJack.cardModel.Suit;
import org.junit.Test;
import static org.junit.Assert.*;

public class PeopleTest {

    @Test
    public void testGetOneCard() {
        People person = new People();
        Card card = new Card(Rank.Ace, Suit.CLUBS);
        boolean isStop = person.GetOneCard(card);
        assertFalse(isStop);
        assertEquals(1, person.getHandList().get(0).getCards().size());
    }

    @Test
    public void testHandClear() {
        People person = new People();
        person.handClear();
        assertTrue(person.getHandList().isEmpty());
    }

    @Test
    public void testGetPoints() {
        People person = new People();
        person.GetOneCard(new Card(Rank.Ace, Suit.CLUBS));
        person.GetOneCard(new Card(Rank.King, Suit.HEARTS));
        assertEquals(21, person.getPoints());
    }
}
