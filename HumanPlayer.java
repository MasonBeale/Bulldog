import javax.swing.*;

/**
 * The HumanPlayer class represents a human-controlled player in the game.
 * This player interacts with the game through a graphical user interface (GUI).
 * @author Mason Beale with DeepSeek
 */
public class HumanPlayer extends Player {
    private final RandomDice dice; // Dice object for rolling
    private Bulldog game; // Reference to the Bulldog for updating the game state

    /**
     * Constructor for the HumanPlayer class.
     *
     * @param name        The name of the player.
     * @param parentFrame The parent JFrame for the GUI.
     * @param game         The Bulldog instance for updating the game state.
     */
    public HumanPlayer(String name, RandomDice dice, JFrame parentFrame, Bulldog game) {
        super(name);
        this.dice = dice;
        this.game = game; // Initialize the reference to Bulldog
    }

    /**
     * Handles the roll action for the human player. Updates the game log and turn score in the Bulldog.
     *
     * @return The current turn score after rolling the dice.
     */
    @Override
    public int play() {
        int roll = dice.roll();
        game.appendToGameLog("   Player " + getName() + " rolled a " + roll + "\n");
        if (roll == 6) {
            game.appendToGameLog("   Rolled a 6! Turn over. Score for this turn: 0\n");
            game.setTurnScore(0); // Reset turn score
            game.endTurn(); // Signal to end the turn
            return 0; // Explicitly return 0 when a 6 is rolled
        } else {
            int newTurnScore = game.getTurnScore() + roll;
            game.setTurnScore(newTurnScore); // Update turn score
            game.appendToGameLog("   Current turn score: " + newTurnScore + "\n");
            return newTurnScore;
        }
    }
}