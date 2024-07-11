package UNO.personModel;

import UNO.cardModel.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

    private Player player;
    private Deck deck;

    @Before
    public void setUp() {
        player = new Player("TestPlayer");
        deck = new Deck();
    }

    @Test
    public void testAddCardToHand() {
        Card card = new NumberCard(Color.RED, "5");
        player.addCardToHand(card);
        assertEquals(1, player.getHandSize());
        assertEquals(card, player.getCardFromHand(0));
    }

    @Test
    public void testRemoveCardFromHand() {
        Card card = new NumberCard(Color.RED, "5");
        player.addCardToHand(card);
        player.removeCardFromHand(card);
        assertEquals(0, player.getHandSize());
    }

    @Test
    public void testClearHand() {
        player.addCardToHand(new NumberCard(Color.RED, "5"));
        player.clearHand();
        assertEquals(0, player.getHandSize());
    }

    @Test
    public void testGetTotalPoints() {
        player.addCardToHand(new NumberCard(Color.RED, "5"));
        player.addCardToHand(new ActionCard(Color.BLUE, "SKIP"));
        assertEquals(25, player.getTotalPoints());
    }

    @Test
    public void testDiscardCardWithIndex() {
        Card card = new NumberCard(Color.RED, "5");
        player.addCardToHand(card);
        Card discardedCard = player.discardCard(0);
        assertEquals(card, discardedCard);
    }

    @Test
    public void testDiscardCardWithUserInput() {
        Card card = new NumberCard(Color.RED, "5");
        player.addCardToHand(card);

        // Temporarily change System.in to simulate user input
        String input = "0\n";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        Card discardedCard = player.discardCard();
        assertEquals(card, discardedCard);
        assertEquals(1, player.getHandSize()); // 卡片未被移除
    }

    @Test
    public void testDrawCard() {
        int initialSize = deck.size();
        player.drawCard(deck);
        assertEquals(1, player.getHandSize());
        assertEquals(initialSize - 1, deck.size());
    }

    @Test
    public void testCallUno() {
        player.addCardToHand(new NumberCard(Color.RED, "5"));
        player.addCardToHand(new NumberCard(Color.YELLOW, "7"));
        player.callUno(deck);
        assertTrue(player.getHandSize() == 2 || player.getHandSize() == 4); // 根据逻辑，可能是喊UNO成功或失败后的牌数
    }

    @Test
    public void testReport() {
        Player previousPlayer = new Player("PreviousPlayer");
        previousPlayer.addCardToHand(new NumberCard(Color.RED, "5"));
        previousPlayer.addCardToHand(new NumberCard(Color.YELLOW, "7"));
        previousPlayer.removeCardFromHand(previousPlayer.getCardFromHand(0)); // 模拟出一张牌
        player.report(previousPlayer, deck);
        assertTrue(previousPlayer.getHandSize() == 1 || previousPlayer.getHandSize() == 3); // 根据逻辑，可能是举报成功或失败后的牌数
    }
}
