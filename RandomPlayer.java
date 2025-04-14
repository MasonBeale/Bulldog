/**
 * The RandomPlayer class represents an AI player that randomly decides whether to continue rolling
 * after each roll. If the player rolls a 6, the turn ends with a score of 0.
 * @author Mason Beale with DeepSeek
 */
public class RandomPlayer extends Player {
    /**
     * Constructor for the RandomPlayer class.
     *
     * @param name The name of the player.
     */
    public RandomPlayer(String name, RandomDice dice) {
        super(name, dice);
    }

    /**
     * Randomly decides whether to continue rolling.
     *
     * @param gameStatus The current game status
     * @return true if the player should roll again, false otherwise
     */
    @Override
    protected boolean shouldContinue(GameStatus gameStatus) {
        return Math.random() < 0.5;
    }
}