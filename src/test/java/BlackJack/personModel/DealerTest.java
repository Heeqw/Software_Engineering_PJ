package BlackJack.personModel;

import BlackJack.cardModel.Card;
import BlackJack.cardModel.Rank;
import BlackJack.cardModel.Suit;
import org.junit.Test;
import static org.junit.Assert.*;

public class DealerTest {

    @Test
    public void testPlay() {
        Dealer dealer = new Dealer();
        Card card = new Card(Rank.Ace, Suit.CLUBS);
        dealer.play(card);
        assertTrue(dealer.getPlay());
    }

    @Test
    public void testHiddenCard() {
        Dealer dealer = new Dealer();
        Card card = new Card(Rank.Seven, Suit.HEARTS);
        boolean continuePlaying = dealer.hiddenCard(card);
        assertTrue(continuePlaying);
    }

    @Test
    public void testDealerPointCheck() {
        Dealer dealer = new Dealer();
        Card card1 = new Card(Rank.Five, Suit.CLUBS);
        Card card2 = new Card(Rank.Ten, Suit.HEARTS);
        dealer.GetOneCard(card1);
        dealer.GetOneCard(card2);
        assertTrue(dealer.dealerPointCheck());
    }
}
