package core;

public interface GameInteractionMode {
    void startGame();
    void setupGame();
    void prepareForRound();
    void playRounds();
    boolean askForNewGame();
    void handlePlayerTurns();
    void concludeRound();
    boolean checkForContinuation();

}
