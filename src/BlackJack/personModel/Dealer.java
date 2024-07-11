package BlackJack.personModel;

import BlackJack.cardModel.Card;

public class Dealer extends People{
    final int MAX_DEALER_POINT = 17;
    private Boolean isPlay;

    public Dealer() {
        super();
    }

    public void play(Card card){
        GetOneCard(card);
        this.isPlay = dealerPointCheck();
    }

    public void openCard(Card card){
        GetOneCard(card); //明牌
        this.GetHand().printLastCard();
    }

    public boolean hiddenCard(Card card){
        GetOneCard(card);
        System.out.println("Dealer gets a hidden Card! ");
        return dealerPointCheck();
    }

    //判断庄家点数是否超过17
    public boolean dealerPointCheck(){
        return this.getPoints() < MAX_DEALER_POINT;
    }

    public void setPlayFalse() {
        this.isPlay = false;
    }

    public Boolean getPlay() {
        return isPlay;
    }

    public void reset(){
        this.isPlay = true;
        this.handClear();
    }
}
