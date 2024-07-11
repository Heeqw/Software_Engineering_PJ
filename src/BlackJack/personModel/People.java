package BlackJack.personModel;

import BlackJack.cardModel.*;

import java.util.ArrayList;

public class People {
    ArrayList<Hand> handList;

    public People() {
        this.handList = new ArrayList<>();
        Hand hand = new Hand();
        this.handList.add(hand);
    }

    public Boolean GetOneCard(Card card) {
        Hand hand = this.GetHand();
        hand.addCard(card);
        return hand.isStop();
    }

    public void handClear(){
        this.handList = new ArrayList<>();
    }

    public Hand GetHand(){
        if (handList.isEmpty()) {
            handList.add(new Hand());  // Add a new hand if the list is empty
        }
        return handList.get(0);
    }

    public int getPoints(){
        return GetHand().getTotal_points();
    }

    public ArrayList<Hand> getHandList() {
        return handList;
    }

}
