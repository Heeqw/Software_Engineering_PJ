package UNO.cardModel;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        this.cards = new LinkedList<>();
        initiateDeck();
    }

    // 初始化牌堆
    public void initiateDeck() {
        addNumberCards();
        addActionCards();
        addWildCards();
        shuffleDeck();
    }

    // 添加数字牌
    private void addNumberCards() {
        for (Color color : Color.values()) {
            addNumberCardsForColor(color);
        }
    }

    private void addNumberCardsForColor(Color color) {
        if (color == Color.BLACK) {
            return;
        }
        for (int i = 0; i <= 9; i++) {
            addSpecificNumberCard(color, i);
        }
    }

    private void addSpecificNumberCard(Color color, int number) {
        if (number == 0) {
            cards.add(new NumberCard(color, String.valueOf(number)));
        } else {
            cards.add(new NumberCard(color, String.valueOf(number)));
            cards.add(new NumberCard(color, String.valueOf(number)));
        }
    }

    // 添加功能牌
    private void addActionCards() {
        String[] actionTypes = {"SKIP", "REVERSE", "+2"};
        for (Color color : Color.values()) {
            if (color != Color.BLACK) {
                for (String detail : actionTypes) {
                    cards.add(new ActionCard(color, detail));
                    cards.add(new ActionCard(color, detail));
                }
            }
        }
    }

    // 添加万能牌
    private void addWildCards() {
        String[] wildTypes = {"CHANGE_COLOR", "+4"};
        for (String detail : wildTypes) {
            for (int i = 0; i < 4; i++) {
                cards.add(new WildCard(detail));
            }
        }
    }

    // 洗牌
    private void shuffleDeck() {
        Collections.shuffle(cards);
    }

    // 从牌堆中抽一张牌
    public Card drawCard() {
        if (cards.isEmpty()) {
            System.out.println("The deck is empty!");
            return null; // 或者抛出异常
        }
        return cards.remove(0);
    }

    // 获取牌堆
    public List<Card> getCards() {
        return cards;
    }

    // 获取牌堆的大小
    public int size() {
        return cards.size();
    }
}
