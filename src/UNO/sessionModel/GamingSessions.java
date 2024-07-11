package UNO.sessionModel;

import UNO.personModel.*;
import UNO.cardModel.*;
import java.util.*;

public class GamingSessions {
    private List<Player> players; //玩家
    private Deck deck; //牌堆
    private Card currentCard = null; //记录牌桌上的最后一张牌
    private int direction = 1; //顺序1,逆序-1
    private int startingPlayerIndex = -1; //首发玩家的索引
    private int currentPlayerIndex = -1; //该轮次玩家的索引
    private Color currentColor = null;
    private Player winner = null;


    //仅仅只是实例化
    //需要在交互界面调用选定首发玩家
    public GamingSessions(List<Player> playerList) {
        this.players = playerList;

        this.deck = new Deck();
        currentCard = null;
        currentColor = null;
        direction = 1; //顺序1,逆序-1
        startingPlayerIndex = -1; //首发玩家的索引
        currentPlayerIndex = -1; //该轮次玩家的索引
        winner = null;
    }

    public Player getWinner(){
        return winner;
    }

    public Player getStartingPlayer(){
        return players.get(startingPlayerIndex);
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    //重置牌桌
    public void resetSessions(){
        if(winner == null){
            this.deck = new Deck();
            currentCard = null;
            currentColor = null;
            direction = 1; //顺序1,逆序-1
            startingPlayerIndex = -1; //首发玩家的索引
            currentPlayerIndex = -1; //该轮次玩家的索引
        }else{
            this.deck = new Deck();
            currentCard = null;
            currentColor = null;
            direction = 1; //顺序1,逆序-1
            startingPlayerIndex = players.indexOf(winner); //首发玩家的索引
            currentPlayerIndex = players.indexOf(winner); //该轮次玩家的索引
        }
        resetPlayers();
    }

    public void resetPlayers(){
        for (Player player: this.players){
            player.getHand().clear();
        }
    }


    //删去某玩家
    public void removePlayer(Player player){
        if(player == winner){
            winner = null;
        }
    }

    //发放第一张牌，确定首发玩家
    public Player determineStartingPlayer() {
        int highestValue = -1;
        int startingPlayerIndex = -1;
        for (int i = 0; i < players.size(); i++) {
            //Player player = players.get(i);
            Card card = deck.drawCard();

            //存放rank最大的
            if (card.getScore() > highestValue) {
                highestValue = card.getScore();
                startingPlayerIndex = i;
            }
        }
        this.startingPlayerIndex = startingPlayerIndex;
        this.currentPlayerIndex = startingPlayerIndex;
        return players.get(startingPlayerIndex);
    }

     public void initialPlayerHands() {
        for (Player player : players) {
            for (int i = 0; i < 7; i++) {
                Card card = deck.drawCard();
                player.addCardToHand(card);
            }
        }
    }

    //抽一张牌给指定玩家
    public Card drawCard(Player player) {
        Card card = deck.drawCard();
        player.addCardToHand(card);
        return card;
    }
    /*
    * play card
    * */
    //判断该牌是否可以打出
    public boolean isPlayable(Card card) {
        if (currentCard == null) {
            return true;
        }
        switch (currentCard.getType()) {
            case NUMBER:
                return isNumberCardPlayable(card);
            case ACTION:
                return isActionCardPlayable(card);
            case WILD:
                return isWildCardPlayable(card);
            default:
                return false;
        }
    }

    private boolean isNumberCardPlayable(Card card) {
        if (card.getType().equals(Type.NUMBER)) {
            return card.getColor().equals(currentCard.getColor()) || card.getScore() == currentCard.getScore();
        } else if (card.getType().equals(Type.WILD)) {
            return true;
        } else if (card.getType().equals(Type.ACTION)) {
            return card.getColor().equals(currentCard.getColor());
        }
        return false;
    }

    private boolean isActionCardPlayable(Card card) {
        if (card.getType().equals(Type.NUMBER)) {
            return card.getColor().equals(currentCard.getColor());
        } else if (card.getType().equals(Type.WILD)) {
            return true;
        } else if (card.getType().equals(Type.ACTION)) {
            return card.getColor().equals(currentCard.getColor()) || card.getDetail().equals(currentCard.getDetail());
        }
        return false;
    }

    private boolean isWildCardPlayable(Card card) {
        if (card.getType().equals(Type.NUMBER) || card.getType().equals(Type.ACTION)) {
            return card.getColor().equals(currentColor);
        } else if (card.getType().equals(Type.WILD)) {
            return true;
        }
        return false;
    }


    //判断该玩家是否有牌可出
    public boolean isPlayable(Player player) {
        boolean flag = false;
        for(int i = 0;i< player.getHandSize();i++) {
            if(isPlayable(player.getHand().getCard(i)))
            {
                flag = true;
            }

        }

        return flag;
    }

    private void handleCardEffect(Card card) {
        switch (card.getType()) {
            case NUMBER : {
                //无操作

                currentColor = card.getColor();//更新最近所出的牌的记录
                moveToNextPlayer();
            }
            case ACTION: {
                //{"SKIP", "REVERSE", "+2"};
                if(card.getDetail().equals("SKIP")){
                    currentColor = card.getColor();//更新最近所出的牌的记录
                    currentPlayerIndex = (currentPlayerIndex + direction * 2 + players.size()) % players.size();//下下位
                } else if (card.getDetail().equals("REVERSE")) {
                    direction *= -1;//改变遍历方向
                    currentColor = card.getColor();//更新最近所出的牌的记录
                    currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
                } else if (card.getDetail().equals("+2")) {
                    Player nextPlayer = players.get((currentPlayerIndex + direction + players.size()) % players.size());
                    drawCard(nextPlayer);
                    drawCard(nextPlayer);

                    currentColor = card.getColor();//更新最近所出的牌的记录
                    currentPlayerIndex = (currentPlayerIndex + direction * 2 + players.size()) % players.size();
                }
            }
            case WILD : {
                //{"CHANGE_COLOR", "+4"};
                if(card.getDetail().equals("CHANGE_COLOR")){
                    moveToNextPlayer();
                    Change_Color();
                } else if (card.getDetail().equals("+4")) {
                    Player nextPlayer = players.get((currentPlayerIndex + direction + players.size()) % players.size());
                    drawCard(nextPlayer);
                    drawCard(nextPlayer);
                    drawCard(nextPlayer);
                    drawCard(nextPlayer);//抽四张牌
                    currentPlayerIndex = (currentPlayerIndex + direction * 2 + players.size()) % players.size();

                    Change_Color();
                }

            }
        }

        currentCard = card;
    }

    //WILD CARD需要用户输入指定的颜色
    private void Change_Color() {
        Scanner in = new Scanner(System.in);
        String new_color = in.nextLine();
        new_color = new_color.trim().toUpperCase();

        switch(new_color) {
            case "RED":
                currentColor = Color.RED;
                break;

            case "YELLOW":
                currentColor = Color.YELLOW;
                break;

            case "BLUE":
                currentColor = Color.BLUE;
                break;

            case "GREEN":
                currentColor = Color.GREEN;
                break;
            default:
                System.out.println("please input correct color!");
                Change_Color();
                break;
        }
    }

    //玩家打牌，如果成功打出返回true，未成功打出返回false
    public boolean discardCard(Player player, Card card) {
        if (isPlayable(card)) {
            player.removeCardFromHand(card);
            currentCard = card;
            currentColor = card.getColor();
            handleCardEffect(card);
            return true;
        }
        return false;
    }

    /*
    * UNO
    * */
    //player举报上一个人没喊UNO
    public void reportUno(Player player) {
        Player previousPlayer = players.get((currentPlayerIndex - direction + players.size()) % players.size());
        player.report(previousPlayer,deck);
    }

    public boolean callUno(Player player) {
        if (isPlayable(player)){
            return player.callUno(deck);
        }else{
            System.out.println("Player "+ player.getName() + " shout uno wrongly.");
            player.addCardToHand(deck.drawCard());
            player.addCardToHand(deck.drawCard());
            return false;
        }
    }

    /*
    * draw result
    **/
    //是否有玩家已经出完牌
    public boolean CheckGameOver() {
        for (int i = 0;i<players.size();i++) {
            Player player = players.get(i);
            if (player.getHandSize() == 0) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    //Deck用尽时根据score得出赢家
    public boolean determineScoreWinner(){
        int min_score = Integer.MAX_VALUE;;
        Player min_score_player = null;
        for(Player player:players){
            if(player.getHand().getTotal_points() < min_score){
                min_score = player.getHand().getTotal_points();
                min_score_player = player;
            }
        }
        if(min_score_player!=null){
            winner= min_score_player;
            System.out.println(min_score_player.getName() + " win!!!!");
            return true;
        }
        return false;
    }

    //currentPlayerIndex变为下一个玩家的序号
    public void moveToNextPlayer(){
        this.currentPlayerIndex = (currentPlayerIndex + direction + players.size()) % players.size();
        //(currentPlayerIndex + direction + players.size()) % players.size()
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public String getCurrentCardFunction() {
        if (currentCard == null){
            return null;
        }
        return currentCard.getDetail();
    }

    public void showAllPlayersCardNum(){
        System.out.println("Number of cards:");
        for (Player player : players){
            System.out.println("Player " + player.getName() + " : " + player.getHandSize());
        }
    }

    public Card getCurrentCard(){ return currentCard;}

    public List<Player> getPlayers() {
        return players;
    }
}

