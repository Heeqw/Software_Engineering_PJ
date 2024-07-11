package BlackJack.personModel;

import BlackJack.cardModel.Card;
import BlackJack.cardModel.Rank;
import BlackJack.cardModel.Suit;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void testSetBet() {
        Player player = new Player("TestPlayer");
        assertTrue(player.setBet(50));
        assertFalse(player.setBet(-10));
        assertFalse(player.setBet(55));
    }

    @Test
    public void testSplitHands() {
        Player player = new Player("TestPlayer");
        player.GetOneCard(new Card(Rank.Eight, Suit.CLUBS));
        player.GetOneCard(new Card(Rank.Eight, Suit.CLUBS));
        player.splitHands();
        assertTrue(player.isSplit());
        assertEquals(2, player.getHandList().size());
    }

    @Test
    public void testSettlementMoney() {
        Player player = new Player("TestPlayer");
        player.setBet(50);
        player.settlementMoney(2.0, false, player.getHandList().get(0));
        assertEquals(150, player.getWallet().getMoney());
    }

    @Test
    public void testSurrender() {
        Player player = new Player("TestPlayer");
        player.setBet(50);
        player.surrender(player.getHandList().get(0));
        assertTrue(player.getHandList().get(0).isSurrendered());
        assertEquals(25, player.getWallet().getMoney());
    }
}
