package BlackJack.cardModel;

public enum Rank {
    Ace("A",11),//在后续计分中再判断A是否需要变为1
    Two("2",2),
    Three("3",3),
    Four("4",4),
    Five("5",5),
    Six("6",6),
    Seven("7",7),
    Eight("8",8),
    Nine("9",9),
    Ten("10",10),
    Jack("J",10),
    Queen("Q",10),
    King("K",10);

    Rank(String rank,int num){
        this.rank = rank;
        this.num = num;
    }

    private String rank;
    private int num;

    @Override
    public String toString() {
        return rank ;
    }

    public int getNum(){
        return num;
    }
}
