/**
 * The Referee class manages the game logic for Bulldog and is implemented as a Singleton.
 * @author Mason Beale with DeepSeek
 */
public class Referee {
    private int winningScore;
    private static Referee instance;
    
    private Referee() {} // Private constructor
    
    public static Referee getInstance() {
        if (instance == null) {
            instance = new Referee();
        }
        return instance;
    }

    /**
     * Sets the winning score for the game.
     * @param winningScore The score needed to win the game
     */
    public void setWinningScore(int winningScore) {
        this.winningScore = winningScore;
    }
    
    /**
     * Plays a game of Bulldog with the given players.
     * @param players The list of players
     * @param gameLogCallback Callback to update game log
     * @param turnCallback Callback to handle turns
     */
    public void playGame(PlayerList players, GameLogCallback gameLogCallback, TurnCallback turnCallback) {
        boolean gameOver = false;
        while (!gameOver) {
            for (int i = 0; i < players.getPlayerCount(); i++) {
                Player currentPlayer = players.getPlayers().get(i);
                int turnScore = 0;
                
                gameLogCallback.appendToGameLog("\nPlayer " + currentPlayer.getName() + "'s turn:\n");
                
                if (currentPlayer instanceof HumanPlayer) {
                    // Handle human player's turn
                    turnScore = turnCallback.handleHumanTurn(currentPlayer);
                } else {
                    // Handle AI player's turn
                    turnScore = currentPlayer.play();
                    players.setPlayerScore(i, players.getPlayerScore(i) + turnScore);
                    gameLogCallback.appendToGameLog("   Scored: " + turnScore + " this turn.\n");
                }
                
                if (players.getPlayerScore(i) >= winningScore) {
                    gameLogCallback.appendToGameLog("\nPlayer " + currentPlayer.getName() + 
                            " wins with a score of " + players.getPlayerScore(i) + "!\n");
                    gameOver = true;
                    turnCallback.gameEnded();
                    break;
                }
                
                try {
                    Thread.sleep(500); // Small pause between turns
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

// Callback interfaces
interface GameLogCallback {
    void appendToGameLog(String message);
}

interface TurnCallback {
    int handleHumanTurn(Player player);
    void gameEnded();
}