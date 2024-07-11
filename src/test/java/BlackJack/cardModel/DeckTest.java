package BlackJack.cardModel;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;


public class DeckTest {
    @Test
    public void testDeckInitialization() {
        Deck deck = new Deck(1); // 创建一副牌
        List<Card> cards = deck.getCards();
        assertEquals(52, cards.size()); // 检查牌数是否为52
    }

    @Test
    public void testDrawCard() {
        Deck deck = new Deck(1);
        Card card = deck.drawCard();
        assertNotNull(card); // 确保抽到的牌不是null
        assertEquals(51, deck.getCards().size()); // 抽一张牌后，牌数应为51
    }

    @Test
    public void testShuffle() {
        Deck deck1 = new Deck(1);
        Deck deck2 = new Deck(1);
        assertNotEquals(deck1.drawCard(), deck2.drawCard()); // 洗牌后两副牌的顺序应该不同
    }

}
