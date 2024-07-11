package BlackJack.personModel;

public class Wallet {
    private int money;
    final int initial_money = 50;

    public Wallet() {
        this.money = initial_money;
    }

    public void changeMoney(int stake){
        this.money += stake;
    }

    public boolean canChangeMoney(int stake){
        return this.money >= stake;
    }
    public int getMoney(){return money;}
}
