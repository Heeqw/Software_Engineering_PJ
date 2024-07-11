package BlackJack.sessionModel;

import BlackJack.cardModel.*;
import BlackJack.personModel.*;
import java.util.ArrayList;


public abstract class GamingSessions implements CheckSessionMode,GameSessionStrategy{
    Deck deck ;
    private final int initializedCardNum = 2;
    ArrayList<Player> players;
    private Dealer dealer = new Dealer();
    private int playerNum;


    public GamingSessions(ArrayList<Player> players){
        this.playerNum = players.size();
        initializeDeck();
        this.players = players;

        //initializeCardToDealer();
    }

    private void initializeDeck(){
        //牌堆初始化
        int num =2;
        if(playerNum >= 4){
            num = 4;
        }
        int deckNum = num;
        deck = new Deck(deckNum);
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void SettleRound() {
        System.out.println("Dealer's hand:");
        getDealer().GetHand().printHand();
        System.out.println("Dealer's points: " + getDealer().getPoints());

        for (Player player : getPlayers()) {
            if (!player.isPlay()) {
                continue; // Skip to the next player if this one is not active
            }
            System.out.println(player.getName() + "'s hands:");
            for (Hand hand : player.getHandList()) {
                System.out.println("Hand details: ");
                hand.printHand();  // Assuming printHand() method prints out all cards in the hand neatly
                System.out.println("Points for this hand: " + hand.getTotal_points());

                if (hand.isSurrendered()) {
                    System.out.println(player.getName() + " has surrendered this hand.");
                    continue;  // Skip further actions for this surrendered hand
                }

                GameOutcome result = drawResult(hand, player);
                switch (result) {
                    case PLAYER_BLACKJACK:
                        System.out.println(player.getName() + " wins with BlackJack!");
                        break;
                    case DEALER_BLACKJACK:
                        System.out.println("Dealer wins with BlackJack against " + player.getName());
                        break;
                    case PLAYER_BUSTS:
                        System.out.println(player.getName() + " busts!");
                        break;
                    case DEALER_BUSTS:
                        System.out.println("Dealer busts! " + player.getName() + " wins!");
                        break;
                    case PLAYER_WINS:
                        System.out.println(player.getName() + " wins with higher points!");
                        break;
                    case DEALER_WINS:
                        System.out.println("Dealer wins with higher points against " + player.getName());
                        break;
                    case TIE:
                        System.out.println("It's a tie between " + player.getName() + " and the dealer.");
                        break;
                }
            }
            // After settling each hand, print the player's wallet status
            if (canPlaceBet()){
                System.out.println(player.getName() + "'s current balance: $" + player.getWallet().getMoney());
            }
        }
    }

    public void initializeCardToDealer(){
        System.out.println("Dealer initializing... ");
        Card openCard = deck.drawCard();
//        Card openCard = new Card(Rank.Ace,Suit.CLUBS);
        dealer.openCard(openCard);

        Card hiddenCard = deck.drawCard();
//        Card hiddenCard = new Card(Rank.Nine,Suit.CLUBS);
        dealer.hiddenCard(hiddenCard);

    }

    //判断是否为blackjack
    public boolean checkBlackJack(Dealer dealer){
        return dealer.getHandList().get(0).isBlackJack();
    }

    // 给玩家的手牌发牌，返回值：玩家是否爆牌超过21
    public void dealCardToPlayer(Player player) {
        Card card = deck.drawCard(); // 从牌堆中抽一张牌
        Hand hand =player.getHandList().get(0);
        for (int i = 0; i < initializedCardNum; i++){
            dealCard(hand);
        }
    }

    public void dealCard (Hand hand){
        Card card = deck.drawCard();
        hand.addCard(card);
    }

    // 给庄家的手牌发牌，返回值：庄家是否爆牌超过21
    public void dealCardToDealer() {
        boolean dealerBusted = false;
        while (dealer.dealerPointCheck() ) {
            Card card = this.deck.drawCard();
            if (card == null) {
                System.out.println("The deck has run out of cards!");
                break;  // Exit if no more cards are available
            }
            dealer.play(card);  // Dealer plays the card

            if (dealer.getPoints() > 21) {
                System.out.println("Dealer has busted!");
                dealerBusted = true;
                break;
            }
        }
        if (!dealerBusted) {
            System.out.println("Dealer stands with points: " + dealer.getPoints());
        }
    }

    //check whether the open card of dealer is A;
    public boolean CheckDealerA(){
        return (dealer.GetHand().getCards().get(0).getRank() == 11);
    }

    //返回一个结果
    public GameOutcome drawResult(Hand playerHand, Player player){
        boolean playerBlackJack = playerHand.isBlackJack();
        boolean dealerBlackJack = checkBlackJack(this.dealer);

        double outcomeMultiplier = getOutcomeMultiplier(playerHand, player, dealerBlackJack);
        if (outcomeMultiplier != Double.MIN_VALUE) {
            handleMoneyTransfer(player, outcomeMultiplier, dealerBlackJack, playerHand);
        }

        return determineOutcome(playerHand, player, dealerBlackJack);
    }

    private double getOutcomeMultiplier(Hand playerHand, Player player, boolean dealerBlackJack) {
        if (!this.canPlaceBet()) {
            return Double.MIN_VALUE;  // Indicates no monetary operation should occur
        }
        if (playerHand.isBlackJack() && !dealerBlackJack) {
            return 1.5;
        } else if (!playerHand.isBlackJack() && dealerBlackJack) {
            return -1.5;
        } else if (playerHand.getTotal_points() > 21) {
            return -1;
        } else if (dealer.getPoints() > 21) {
            return 1;
        } else if (playerHand.getTotal_points() > dealer.getPoints()) {
            return 1;
        } else if (playerHand.getTotal_points() < dealer.getPoints()) {
            return -1;
        } else {
            return 0;
        }
    }

    private GameOutcome determineOutcome(Hand playerHand, Player player, boolean dealerBlackJack) {
        if (playerHand.isBlackJack() && !dealerBlackJack) {
            return GameOutcome.PLAYER_BLACKJACK;
        } else if (!playerHand.isBlackJack() && dealerBlackJack) {
            return GameOutcome.DEALER_BLACKJACK;
        } else if (playerHand.getTotal_points() > 21) {
            return GameOutcome.PLAYER_BUSTS;
        } else if (dealer.getPoints() > 21) {
            return GameOutcome.DEALER_BUSTS;
        } else if (playerHand.getTotal_points() > dealer.getPoints()) {
            return GameOutcome.PLAYER_WINS;
        } else if (playerHand.getTotal_points() < dealer.getPoints()) {
            return GameOutcome.DEALER_WINS;
        } else {
            return GameOutcome.TIE;
        }
    }

    private void handleMoneyTransfer(Player player, double multiplier, boolean dealerBlackJack, Hand hand) {
        int change = (int) (multiplier * hand.getStake());
        if (dealerBlackJack) {
            change += 2 * hand.getInsurance();
        }
        player.getWallet().changeMoney(change);
    }


    //重置牌桌
    //未发牌
    public void resetSession(){
        initializeDeck();
        dealer.reset();
        for (Player player: players){
            player.reset();
        }
    }

    public Dealer getDealer(){ return this.dealer;}
    public abstract boolean canDoubleDown();
    public abstract boolean canPlaceBet();
    public abstract boolean canSplitHands();
    public abstract boolean canTakeInsurance();
    public abstract boolean canSurrender();
    public abstract boolean playerPlaceBet(Player player, int amount);
    public abstract boolean canPlayerSplitHands(Player player);
}
