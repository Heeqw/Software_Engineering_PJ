package core;

import core.BJGameInteraction;

import java.util.Scanner;

public class GameInteraction {
    private Scanner scanner;

    public GameInteraction() {
        this.scanner = new Scanner(System.in);
    }

    public void startGameInteration(){
        boolean changeGame = true;
        while (changeGame) {
            setupGame();
            changeGame = askForNewGame();
        }
        System.out.println("Game ended. Thanks for playing!");
    }

    public void setupGame(){
        System.out.println("Welcome to OurGame !");
        String input = validationCheck.getValidInput(scanner,"Choose a game : BlackJack or UNO (bj/uno)","bj","uno");
        if (input.equals("bj")){
            BJGameInteraction BJgame = new BJGameInteraction();
            BJgame.startGame();
        }
        else if (input.equals("uno")){
            UNOGameInteraction UNgame = new UNOGameInteraction();
            UNgame.startGame();
        }
    }

    public boolean askForNewGame() {
        String choice = validationCheck.getValidInput(scanner, "Do you want to change a game or exit? (change/exit)", "change", "exit");
        if (choice.equals("exit")) {
            return false;
        }
        if (choice.equals("change")) {
            System.out.println("Back to the selection interface...");
            return true;
        }
        return true;
    }

    public static void main(String[] args) {
        GameInteraction game = new GameInteraction();
        game.startGameInteration();
    }
}
