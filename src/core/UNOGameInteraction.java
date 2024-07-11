package core;

import UNO.cardModel.Card;
import UNO.cardModel.Deck;
import UNO.sessionModel.GamingSessions;
import UNO.personModel.PlayerAction;
import UNO.personModel.Player;
import UNO.sessionModel.*;
import UNO.cardModel.Hand;
import core.GameInteractionMode;
import core.validationCheck;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UNOGameInteraction implements GameInteractionMode {
    private GamingSessions gamingSessions;
    private Scanner scanner;
    private List<PlayerAction> availableActions;
    private boolean remind = false;

    public UNOGameInteraction() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void startGame() {
        boolean restartGame = true;
        while (restartGame) {
            setupGame();
            playRounds();
            restartGame = askForNewGame();
        }
        System.out.println("Game ended. Thanks for playing!");
    }

    @Override
    public void setupGame() {
        gamingSessions = new GamingSessions(setupPlayers());
    }

    @Override
    public void prepareForRound() {
        gamingSessions.resetSessions();
        System.out.println("New round starting...");
        if (gamingSessions.getWinner() == null){
            gamingSessions.determineStartingPlayer();
        }
        gamingSessions.initialPlayerHands();
    }

    @Override
    public void playRounds() {
        boolean gameContinue = true;
        while (gameContinue) {
            prepareForRound();
            remind = false;
            handlePlayerTurns();
            concludeRound();
            gameContinue = checkForContinuation();
        }
    }

    @Override
    public boolean checkForContinuation(){
        System.out.println("Round ended. Thanks for playing!");
        List<Player> playerList = gamingSessions.getPlayers();
        for (Player player : playerList) {
            String input = validationCheck.getValidInput(scanner,player.getName() + ", do you want to play another round? (yes/no)","yes","no");
            if (input.equals("yes")) {
                continue;
            } else {
                if (player.equals(gamingSessions.getWinner())){
                    System.out.println("See you next time " + player.getName());
                    System.out.println("Winner exit! Restart!");
                    return false;
                }else {
                    gamingSessions.getPlayers().remove(player);
                    System.out.println("See you next time " + player.getName());
                }
            }
        }
        if (gamingSessions.getPlayers().size() < 2){
            System.out.println("There aren't enough people! exit");
            return false;
        }
        return true;
    }


    @Override
    public boolean askForNewGame() {
        Boolean flag = true;
        for (Player player : gamingSessions.getPlayers()){
            String choice = validationCheck.getValidInput(scanner, "Do you want to start a new game or exit? (new/no)", "new", "no");
            if (choice.equals("no")) {
                if (player.equals(gamingSessions.getWinner())){
                    return false;
                }
            }
        }
        return flag;
    }

    @Override
    public void handlePlayerTurns() {
        while (!gamingSessions.CheckGameOver()){
            playerTurn(gamingSessions.getCurrentPlayer());
        }
    }

    @Override
    public void concludeRound() {
        gamingSessions.determineScoreWinner();
    }

    public void playerTurn(Player player) {
        System.out.println("----------Current player : " + player.getName() + " ----------");
        System.out.println("    Number of your cards : " + player.getHandSize());
        System.out.println("    Current clolor : " + gamingSessions.getCurrentColor());
        System.out.println("    Current function : " + gamingSessions.getCurrentCardFunction());
        if (remind){
            System.out.println("    Your previous player call UNO!!!");
            remind = false;
        }
        gamingSessions.showAllPlayersCardNum();
        player.displayHand();
        availableActions = setPlayerAvailableActions();
        while (!playerAction(player,gamingSessions)){;}
    }

    public Boolean playerAction(Player player,GamingSessions gamingSessions) {
        PlayerAction action = askPlayerAction(availableActions);
        switch (action) {
            case DISCARD:
                if (gamingSessions.isPlayable(player)){
                    Card card = player.discardCard();
                    if (gamingSessions.discardCard(player,card)){
                        return true;
                    }
                    System.out.println("Invalid selection. This card cannot be played.");
                    return false;
                }
                else {
                    System.out.println("Invalid selection. You have no card to discard.");
                    return false;
                }
            case DRAW:
                Card drawCard = gamingSessions.drawCard(player);
                System.out.println("You got card :" + drawCard);
                if (gamingSessions.isPlayable(drawCard)){
                    String choice = validationCheck.getValidInput(scanner,"Do you want to discard this card? (Y/N)","y","n");
                    if (choice.equals("y")){
                        gamingSessions.discardCard(player,drawCard);
                    }else {
                        gamingSessions.moveToNextPlayer();
                    }
                }else {
                    System.out.println("The card you draw cannot be played now!");
                    gamingSessions.moveToNextPlayer();
                }
                return true;
            case UNO:
                remind = gamingSessions.callUno(player);
                if (remind){
                    Card card = player.discardCard();
                    while (gamingSessions.discardCard(player,card)){
                        return true;
                    }
                }
                else{
                    availableActions.remove(PlayerAction.UNO);
                    return false;
                }
            case REPORT:
                gamingSessions.reportUno(player);
                availableActions.remove(PlayerAction.REPORT);
                return false;
            default:
                System.out.println("Invalid action. No such action.");
                return false;
        }
    }

    public List<PlayerAction> setPlayerAvailableActions(){
        List<PlayerAction> availableActions = new ArrayList<>();
        availableActions.add(PlayerAction.DISCARD);
        availableActions.add(PlayerAction.DRAW);
        availableActions.add(PlayerAction.UNO);
        availableActions.add(PlayerAction.REPORT);
        return availableActions;
    }

    public PlayerAction askPlayerAction(List<PlayerAction> availableActions) {
        // 显示操作选项并获取玩家选择
        System.out.println("Choose an action: " + availableActions);
        String input = scanner.nextLine().toLowerCase();
        for (PlayerAction action : availableActions) {
            if (action.toString().equals(input)) {
                return action;
            }
        }
        System.out.println("Invalid input, please try again.");
        return askPlayerAction(availableActions);  // 递归调用直到得到有效输入
    }

    public ArrayList<UNO.personModel.Player> setupPlayers(){
        int playerNum = validationCheck.getValidIntegerInput(scanner,"Enter the number of players(2-4):",2,4);
        ArrayList<UNO.personModel.Player> players = new ArrayList<>();
        for (int i = 0; i < playerNum; i++) {
            System.out.println("Enter player " + (i + 1) + " name:");
            String name = scanner.nextLine();
            players.add(new UNO.personModel.Player(name));
        }
        return players;
    }

}
