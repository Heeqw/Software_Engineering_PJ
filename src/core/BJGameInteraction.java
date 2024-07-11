package core;

import BlackJack.cardModel.Card;
import BlackJack.cardModel.Hand;
import BlackJack.cardModel.Rank;
import BlackJack.cardModel.Suit;
import BlackJack.personModel.Player;
import BlackJack.personModel.PlayerAction;
import BlackJack.sessionModel.*;
import core.validationCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BJGameInteraction implements GameInteractionMode {
    private GamingSessions gamingSessions;
    private Scanner scanner;


    public BJGameInteraction(){
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
        boolean restartGame = true;
        while (restartGame) {
            setupGame();
            playRounds();
            restartGame = askForNewGame();
        }
        System.out.println("Game ended. Thanks for playing!");
    }

    public void setupGame() {
        selectGameModeandPlayers();
    }

    public void playRounds() {
        boolean gameContinue = true;
        while (gameContinue) {
            prepareForRound();
            handlePlayerTurns();
            concludeRound();
            gameContinue = checkForContinuation();
        }
    }

    public void prepareForRound() {
        gamingSessions.resetSession();
        System.out.println("New round starting...");
        placeBets();
        gamingSessions.initializeCardToDealer();
        dealInitialCardsToPlayers();
//        setupPlayerHandsForSplit();
        setupPlayersHandForSimpleMode();
    }

    private void dealInitialCardsToPlayers() {
        ArrayList<Player> players = gamingSessions.getPlayers();
        for (Player player : players) {
            gamingSessions.dealCardToPlayer(player);
        }
    }

    private void setupPlayerHandsForSplit() {
        // 获取所有玩家
        ArrayList<Player> players = gamingSessions.getPlayers();

        // 硬编码设置第一个玩家的手牌为可以分牌的情况（例如，两张8）
        if (players.size() > 0) {
            Player firstPlayer = players.get(0);
            Hand initialHand = new Hand();
            initialHand.addCard(new Card(Rank.Eight, Suit.CLUBS)); // 添加第一张8
            initialHand.addCard(new Card(Rank.Eight, Suit.CLUBS)); // 添加第二张8

            // 清空玩家的手牌列表并添加硬编码的手牌
            firstPlayer.getHandList().clear();
            firstPlayer.getHandList().add(initialHand);
        }
    }

    private void setupPlayersHandForSimpleMode(){
        ArrayList<Player> players = gamingSessions.getPlayers();

        // 硬编码设置第一个玩家的手牌为可以分牌的情况（例如，两张8）
        if (players.size() > 0) {
            Player firstPlayer = players.get(0);
            Player secondPlayer = players.get(1);
            Hand initialHand = new Hand();
            initialHand.addCard(new Card(Rank.Nine, Suit.CLUBS)); // 添加第一张8
            initialHand.addCard(new Card(Rank.Ace, Suit.CLUBS)); // 添加第二张8

            Hand anotherHand = new Hand();
            anotherHand.addCard(new Card(Rank.Ten, Suit.SPADES));
            anotherHand.addCard(new Card(Rank.Ace, Suit.CLUBS));
            // 清空玩家的手牌列表并添加硬编码的手牌
            firstPlayer.getHandList().clear();
            firstPlayer.getHandList().add(initialHand);
            secondPlayer.getHandList().clear();
            secondPlayer.getHandList().add(anotherHand);
        }
    }

    public void handlePlayerTurns() {
        for (Player player : gamingSessions.getPlayers()) {
            if (player.isPlay()) {
                playerTurn(player);
            }
        }
        gamingSessions.dealCardToDealer();
    }

    public void concludeRound() {
        gamingSessions.SettleRound();
        endOfRound();
    }

    public boolean checkForContinuation() {
        return gamingSessions.getPlayers().stream().anyMatch(Player::isPlay);
    }

    public boolean askForNewGame() {
        String choice = validationCheck.getValidInput(scanner, "Do you want to start a new game or exit? (new/exit)", "new", "exit");
        if (choice.equals("exit")) {
            return false;
        }
        if (choice.equals("new")) {
            System.out.println("Starting a new game setup...");
            return true;
        }
        System.out.println("Invalid input, defaulting to new game setup.");
        return true;
    }



    public void playerTurn(Player player) {
        System.out.println("Player " + player.getName());
        int i = 0;
        while (i < player.getHandList().size()) {
            Hand hand = player.getHandList().get(i);
            boolean handFinished = false;
            while (!handFinished) {
                System.out.println("Your hand:");
                hand.printHand();
                System.out.println("Your points: " + hand.getTotal_points());
                List<PlayerAction> availableActions = setPlayerAvailableActions(player, gamingSessions, hand);
                PlayerAction action = askPlayerAction(player, gamingSessions, hand, availableActions);
                switch (action) {
                    case HIT:
                        gamingSessions.dealCard(hand);
                        handFinished = hand.isStop();
                        break;
                    case STAND:
                        System.out.println(player.getName() + " decides to stand.");
                        handFinished = true;
                        break;
                    case DOUBLE_DOWN:
                        System.out.println("Doubled down and dealt one more card.");
                        hand.doubleStake();
                        gamingSessions.dealCard(hand);
                        handFinished = true;
                        break;
                    case SPLIT:
                        player.splitHands();
                        gamingSessions.dealCard(player.GetHand());
                        gamingSessions.dealCard(player.getHandList().get(1));
                        break;
                    case INSURANCE:
                        player.insurance(hand);
                        System.out.println("Insurance purchased successfully!");
                        System.out.println("Player " + player.getName() + " current balance: " + player.getWallet().getMoney());
                        break;
                    case SURRENDER:
                        player.surrender(hand);
                        System.out.println("Player chooses to surrender. Ending game!");
                        return; // 投降后直接结束玩家的所有手牌处理
                }
            }
            i++;
        }
    }


    private void selectGameModeandPlayers(){
        System.out.println("Welcome to BlackJack !");
        //接收玩家数和模式选择后实例化牌桌
        System.out.println("1. Gambler Mode");
        System.out.println("2. Safe Mode");
        System.out.println("3. Simple Mode");
        int gameMode = validationCheck.getValidIntegerInput(scanner,"Please select game mode.",1,3);
        switch (gameMode){
            case 1:
                gamingSessions = new GamblerMode(setupPlayers());
                break;
            case 2:
                gamingSessions = new SafeMode(setupPlayers());
                break;
            case 3:
                gamingSessions = new SimpleMode(setupPlayers());
                break;
            default:
                System.out.println("Invalid choice. Defaulting to Gambler Mode.");
                gamingSessions = new GamblerMode(setupPlayers());
                break;
        }
    }

    public ArrayList<Player> setupPlayers(){
        int playerNum = validationCheck.getValidIntegerInput(scanner,"Enter the number of players(2-7):",2,7);
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            System.out.println("Enter player " + (i + 1) + " name:");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }
        return players;
    }

    private void placeBets() {
        if (!gamingSessions.canPlaceBet()) {
            return; // 如果当前游戏模式不允许下注，则直接返回
        }
        System.out.println("Time to place bets.");
        ArrayList<Player> players = gamingSessions.getPlayers();
        for (Player player : players) {
            if (player.isPlay())placeBet(player);
        }
    }

    public void placeBet(Player player) {
        System.out.println("Player " + player.getName());
        int stake;
        boolean validBet = false;
        while (!validBet) {
            System.out.println("Enter your bet (must be a positive multiple of 10):");
            try {
                stake = Integer.parseInt(scanner.nextLine());
                validBet = player.setBet(stake);
                if (validBet)
                    System.out.println("Player " + player.getName() + " set bet : $" + player.getStake());

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    public List<PlayerAction> setPlayerAvailableActions(Player player, GamingSessions session, Hand currentHand){
        List<PlayerAction> availableActions = new ArrayList<>();
        availableActions.add(PlayerAction.HIT);
        availableActions.add(PlayerAction.STAND);

        // 根据游戏模式添加可用的操作
        if (session.canDoubleDown() && player.canAfford(2 * player.getStake()) && currentHand.canDouble()) {
            availableActions.add(PlayerAction.DOUBLE_DOWN);
        }
        if (session.canSplitHands() && session.canPlayerSplitHands(player) && !player.isSplit()) {
            availableActions.add(PlayerAction.SPLIT);
        }
        if (session.canTakeInsurance() && session.CheckDealerA() && !currentHand.hasTakenInsurance() && player.canAfford((int)(1.5 * player.getStake()))) {
            availableActions.add(PlayerAction.INSURANCE);
        }
        if (session.canSurrender() && player.canAfford((int)(1.5 * player.getStake())) && currentHand.canSurrender()) {
            availableActions.add(PlayerAction.SURRENDER);
        }
        return availableActions;
    }

    public PlayerAction askPlayerAction(Player player, GamingSessions session, Hand currentHand,List<PlayerAction> availableActions) {
        // 显示操作选项并获取玩家选择
        System.out.println("Choose an action: " + availableActions);
        String input = scanner.nextLine().toLowerCase();
        for (PlayerAction action : availableActions) {
            if (action.toString().equals(input)) {
                return action;
            }
        }
        System.out.println("Invalid input, please try again.");
        return askPlayerAction(player, session, currentHand,availableActions);  // 递归调用直到得到有效输入
    }

    public void endOfRound() {
        System.out.println("Round ended. Thanks for playing!");
        for (Player player : gamingSessions.getPlayers()) {
            if (player.getWallet().getMoney() < 10) {
                System.out.println(player.getName() + " cannot continue due to insufficient funds.");
                player.setLoseTrue();
                player.setPlayFalse();// 资金不足，标记为非激活状态
                continue;
            }
            if (player.isLose()) {
                continue; // 如果玩家已经非激活，跳过询问
            }
            String input = validationCheck.getValidInput(scanner,player.getName() + ", do you want to play another round? (yes/no)","yes","no");
//            System.out.println(player.getName() + ", do you want to play another round? (yes/no)");
//            String input = scanner.nextLine().toLowerCase();
            if (!input.equals("yes")) {
                player.setPlayFalse();  // 玩家选择不继续，标记为非激活
            } else {
                player.setPlayTrue();  // 玩家选择继续，确保标记为激活
            }
        }
    }

}
