package BlackJack.cardModel;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    final int MAX_POINT = 21;
    final int A_POINT = 11;
    private List<Card> cards;
    private boolean isStop = false;
    private int stake ;
    private int insurance ;
    private boolean surrendered = false;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.cards.add(card);
        this.setStop(this.calculateTotalPoints() > MAX_POINT);
    }

    public Card getOneCard(int i) {
        return this.cards.get(i);
    }

    public List<Card> getCards() {
        return cards;
    }

    public void clear(){
        cards.clear();
    }


    private Boolean CheckA(Card card) {
        return card.getRank() == A_POINT ;
    }


    public int calculateTotalPoints() {
        int totalPoints = 0;
        int aceCount = 0;
        for (Card card : cards) {
            int rank = card.getRank();
            totalPoints += rank;
            if (rank == A_POINT) {
                aceCount++;

            }
        }

        while (totalPoints > MAX_POINT && aceCount > 0) {
            totalPoints -= 10;
            aceCount--;
        }
        return totalPoints;
    }

    public void printHand() {
        for (Card card : this.cards) {
            System.out.println(card);
        }
    }

    public boolean isStop() {
        return this.isStop;
    }

    public void setStop(boolean isOver) {
        this.isStop = isOver;
    }

    public boolean isBlackJack() {
        return this.cards.size() == 2 && this.calculateTotalPoints() == MAX_POINT;
    }

    public void printLastCard() {
        System.out.println(this.cards.get(this.cards.size() - 1));
    }

    public int getLastCard() {
        return this.getOneCard(this.cards.size()-1).getRank();
    }
    public int getInsurance(){ return insurance;}
    public int getStake(){ return this.stake;}
    public void setStake(int stake){
        this.stake = stake;
    }
    public int getTotal_points() {
        return this.calculateTotalPoints();
    }

    public boolean canDouble() {
        return cards.size() == 2;
    }

    public void doubleStake(){
        if (canDouble()) this.stake *= 2;
    }
    public boolean hasTakenInsurance() {
        return insurance != 0 ;
    }
    public void payInsurance() {
        if ( !hasTakenInsurance()) this.insurance = this.stake / 2;
    }
    public boolean canSurrender(){ return cards.size() == 2;}
    public void surrender() {
        this.surrendered = true;

    }
    public boolean isSurrendered(){ return surrendered;}
}
