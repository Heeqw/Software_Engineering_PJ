package BlackJack.cardModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(int deckNum) {
        cards = new ArrayList<>();
        // 初始化一副牌
        initializeDeck(deckNum);
        // 洗牌
        shuffle();
    }

    private void initializeDeck(int deckNum) {
        for(int i=0;i<deckNum;i++) {
            for (Suit suit : Suit.values()) {
                for (Rank rank : Rank.values()) {
                    cards.add(new Card(rank, suit));
                }
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    // 从牌堆中抽取一张牌
    public Card drawCard() {
        if (cards.isEmpty()) {
            return null; // 如果牌堆已经没有牌了，则返回null
        }
        return cards.remove(0); // 抽取并返回牌堆顶部的一张牌
    }

    public List<Card> getCards() {
        return cards;
    }
}
