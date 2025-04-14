/**
 * A simple class to encapsulate the current status of the Bulldog game.
 * Contains information about players (via PlayerList), their scores, and the current player.
 */
public class GameStatus {
    private PlayerList players;
    private Player currentPlayer;
    private int currentTurnScore;
    
    /**
     * Constructs a GameStatus object with the given PlayerList and current player.
     * 
     * @param players The PlayerList containing all players in the game
     * @param currentPlayer The player whose turn it currently is
     */
    public GameStatus(PlayerList players, Player currentPlayer) {
        this.players = players;
        this.currentPlayer = currentPlayer;
    }
    
    /**
     * Gets the PlayerList containing all players in the game.
     * 
     * @return The PlayerList instance
     */
    public PlayerList getPlayerList() {
        return players;
    }
    
    /**
     * Gets the current player (whose turn it is).
     * 
     * @return The current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public int getCurrentTurnScore() {
        return currentTurnScore;
    }
    
    public void setCurrentTurnScore(int score) {
        this.currentTurnScore = score;
    }
    
    /**
     * Gets a string representation of the game status.
     * 
     * @return String containing player names, scores, and current player
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Current Game Status:\n");
        for (int i = 0; i < players.getPlayerCount(); i++) {
            sb.append(String.format("  %s: %d points", 
                players.getPlayerName(i), 
                players.getPlayerScore(i)));
            if (players.getPlayers().get(i).equals(currentPlayer)) {
                sb.append(" (Current Player)");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}