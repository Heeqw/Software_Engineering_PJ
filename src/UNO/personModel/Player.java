package UNO.personModel;

import UNO.cardModel.*;
import java.util.*;
import core.*;
public class Player {
    private String name;
    private Hand hand;
    private boolean hasCalledUno;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
        this.hasCalledUno = false;
    }

    // 获取玩家的名字
    public String getName() {
        return name;
    }

    // 获取玩家的手牌
    public Hand getHand() {
        return hand;
    }

    // 添加一张卡片到玩家的手牌
    public void addCardToHand(Card card) {
        hand.addCard(card);
        hasCalledUno = false; // 添加牌后重置UNO状态
    }

    // 从玩家手牌中移除指定位置的卡片
    public void removeCardFromHand(Card card) {
        hand.removeCard(card);
    }

    // 清空玩家的手牌
    public void clearHand() {
        hand.clear();
        hasCalledUno = false;
    }

    // 获取玩家手牌中的卡片数量
    public int getHandSize() {
        return hand.size();
    }

    // 打印玩家手牌中的卡片数量
    public void displayHandSize() {
        System.out.println(name + "'s Hand:");
        System.out.println(getHandSize());
    }

    // 获取玩家手牌中指定位置的卡片
    public Card getCardFromHand(int index) {
        return hand.getCard(index);
    }

    // 打印玩家手牌中的所有卡片
    public void displayHand() {
        System.out.println(name + "'s Hand:");
        hand.display();
    }

    // 获取玩家手牌中所有卡片的点数总和
    public int getTotalPoints() {
        return hand.getTotal_points();
    }

    // 出牌方法
    public Card discardCard() {
        System.out.println(name + " decides to discard a card.");
        Card cardToDiscard = null;
        // 实现出牌逻辑，选择要出的牌
        if (hand.size() > 0) {
            Scanner scanner = new Scanner(System.in);

            int index = validationCheck.getValidIntegerInput(scanner,"Select the card to discard (0 to " + (hand.size() - 1) + "): ",0,(hand.size() - 1));
            cardToDiscard = discardCard(index);

            System.out.println(name + " discards: " + cardToDiscard);

        }
        return cardToDiscard;
    }

    public Card discardCard(int index) {
        if (index >= 0 && index < hand.size()) {
            Card cardToDiscard = hand.getCard(index);

            return cardToDiscard;
        }
        return null;
    }

    public void removeCard(Card card){
        hand.removeCard(card);
    }

    // 摸牌方法
    public void drawCard(Deck deck) {
        System.out.println(name + " decides to draw a card.");
        // 实现摸牌逻辑
        Card drawnCard = deck.drawCard();
        hand.addCard(drawnCard);
        System.out.println(name + " draws: " + drawnCard);
    }


    // 玩家喊UNO
    public boolean callUno(Deck deck) {
        if (hand.size() == 2) {
            hasCalledUno = true;
            System.out.println(name + " shouts UNO!");
            return true;
            //discardCard();
        } else {
            System.out.println(name + " cannot shout UNO because you do not have exactly two card.");
            System.out.println(" As punishment, you take two cards");
            //错喊拿两张牌
            addCardToHand(deck.drawCard());
            addCardToHand(deck.drawCard());
            this.displayHand();
            return false;
        }
    }

    // 举报上家没有喊UNO
    public void report(Player previousPlayer, Deck deck) {
        if (!previousPlayer.hasCalledUno && previousPlayer.getHandSize() == 1) {
            System.out.println(name + " reports that " + previousPlayer.getName() + " did not call UNO!");
            // 实现惩罚逻辑，让上家摸两张牌
            previousPlayer.addCardToHand(deck.drawCard());
            previousPlayer.addCardToHand(deck.drawCard());
        } else {
            System.out.println(name + " cannot report " + previousPlayer.getName() + " because they either called UNO or do not have exactly one cards.");
            System.out.println(" As punishment, you take two cards");
            // 如果举报失败，举报者需要自己摸两张牌
            addCardToHand(deck.drawCard());
            addCardToHand(deck.drawCard());
            displayHand();
        }
    }


}
