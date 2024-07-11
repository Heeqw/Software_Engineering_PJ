package UNO.cardModel;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    // 添加卡片到手牌
    public void addCard(Card card) {
        cards.add(card);
    }

    // 从手牌移除指定位置的卡片
    public void removeCard(Card card) {
        cards.remove(card);
        // 记得index从0开始计数
    }

    // 清空手牌
    public void clear() {
        cards.clear();
    }

    // 获取手牌中的卡片数量
    public int size() {
        return cards.size();
    }

    // 获取指定位置的卡片
    public Card getCard(int index) {
        return cards.get(index);
    }

    // 打印手牌中的所有卡片
    public void display() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    // 获取手牌中所有卡片的点数总和
    public int getTotal_points() {
        int totalPoints = 0;
        for (Card card : cards) {
            totalPoints += card.getScore();
        }
        return totalPoints;
    }

}
