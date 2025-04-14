/**
 * The FifteenPlayer class represents an AI player that aims to reach a score of 15 in each turn.
 * If the player rolls a 6, the turn ends with a score of 0. Otherwise, the player continues rolling
 * until the turn score reaches at least 15.
 * @author Mason Beale with DeepSeek
 */
public class FifteenPlayer extends Player {
    /**
     * Constructor for the FifteenPlayer class.
     *
     * @param name The name of the player.
     */
    public FifteenPlayer(String name, RandomDice dice) {
        super(name, dice);
    }

    /**
     * Determines if the player should continue rolling based on reaching 15 points.
     *
     * @param gameStatus The current game status
     * @return true if the player should roll again, false otherwise
     */
    @Override
    protected boolean shouldContinue(GameStatus gameStatus) {
        int currentTurnScore = gameStatus.getCurrentPlayer().getScore();
        return currentTurnScore < 15;
    }
}