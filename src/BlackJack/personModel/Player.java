package BlackJack.personModel;

import BlackJack.cardModel.Hand;
import BlackJack.cardModel.Card;


public class Player extends People {
    private String name;
    private Wallet wallet;
    private int stake;
//    private int insurance;
    private boolean isPlay;
    private Boolean isSplit;
    private Boolean isLose;

    public Player(String name){
        super();
        this.name = name;
        this.wallet = new Wallet();
        this.stake = 0;
//        this.insurance = 0;
        this.isPlay = true;
        this.isSplit = false;
        this.isLose = false;
//        this.handList.add(new Hand());
    }



    public Boolean setBet(int stake){
        if (stake <= 0){
            System.out.println("Bet must be positive.");
            return false;
        }
        if (stake % 10 != 0){
            System.out.println("Bet must be a multiple of 10");
            return false;
        }
        if (this.wallet.canChangeMoney(stake)){
            this.stake = stake;
            for (Hand hand : handList){
                hand.setStake(stake);
            }

            return true;
        }
        else {
            System.out.println("Insufficient funds for bet: $" + stake);
            return false;
        }

    }
    public void insurance(Hand hand){
        hand.payInsurance();
        int stake = hand.getStake();
        int insurance = - stake / 2;
        this.wallet.changeMoney(insurance);
    }

    public void splitHands(){
        int currentStake = this.getStake();
        this.isSplit = true;
        Hand currentHand = this.handList.get(0);
        Card secondCard = currentHand.getCards().remove(1);
        Hand secondHand = new Hand();
        secondHand.addCard(secondCard);
        this.handList.add(secondHand);
        currentHand.setStake(currentStake);
        secondHand.setStake(currentStake);
    }

    public void surrender(Hand hand){
        if (hand != null && !hand.isSurrendered()){
            hand.surrender();
            int change = - stake / 2;
            this.wallet.changeMoney(change);
            System.out.println(this.getName() + " surrenders and loses half the bet: $" + -change);
        }

    }

    public void settlementMoney(double multiple,Boolean isBlackJack, Hand currentHand){
        int stake = currentHand.getStake();
        System.out.println("stake: " + stake);
        int change = (int) (multiple * stake);
        if (isBlackJack){
            change += 2 * currentHand.getInsurance();
        }
        this.wallet.changeMoney(change);
        this.isPlay = this.wallet.getMoney() >= 10;
    }

    public Wallet getWallet(){
        return wallet;
    }
    public int getStake() {
        return stake;
    }

    public void setStake(int stake){
        this.stake = stake;
    }

    public Boolean isPlay() {
        return isPlay;
    }


    public void setLoseTrue() {
        this.isLose = true;
    }

    public boolean isLose(){
        return isLose;
    }
    public boolean canAfford(int bet){
        return this.wallet.getMoney() >= bet;
    }
    public void setPlayFalse(){ this.isPlay = false;}
    public void setPlayTrue(){ this.isPlay = true;}
    public String getName(){return name;}

    public boolean isSplit(){return isSplit;}

    public void reset(){
        this.isSplit = false;
        this.handClear();
        handList.add(new Hand());
    }
}
