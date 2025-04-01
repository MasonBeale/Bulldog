import javax.swing.*;

/**
 * The AiHumanPlayer class represents a human-controlled player in the game.
 * This player interacts with the game through a graphical user interface (GUI).
 */
public class AiHumanPlayerMVC extends Player {
    private final Dice dice; // Dice object for rolling
    private BulldogMVC mvc; // Reference to the BulldogMVC for updating the game state

    /**
     * Constructor for the AiHumanPlayer class.
     *
     * @param name        The name of the player.
     * @param parentFrame The parent JFrame for the GUI.
     * @param mvc         The BulldogMVC instance for updating the game state.
     */
    public AiHumanPlayerMVC(String name, JFrame parentFrame, BulldogMVC mvc) {
        super(name);
        this.dice = new Dice(6); // Standard 6-sided die
        this.mvc = mvc; // Initialize the reference to BulldogMVC
    }

    /**
     * Handles the roll action for the human player. Updates the game log and turn score in the BulldogMVC.
     *
     * @return The current turn score after rolling the dice.
     */
    @Override
    public int play() {
        int roll = dice.roll();
        mvc.appendToGameLog("   Player " + getName() + " rolled a " + roll + "\n");
        if (roll == 6) {
            mvc.appendToGameLog("   Rolled a 6! Turn over. Score for this turn: 0\n");
            mvc.setTurnScore(0); // Reset turn score
            mvc.endTurn(); // Signal to end the turn
            return 0; // Explicitly return 0 when a 6 is rolled
        } else {
            int newTurnScore = mvc.getTurnScore() + roll;
            mvc.setTurnScore(newTurnScore); // Update turn score
            mvc.appendToGameLog("   Current turn score: " + newTurnScore + "\n");
            return newTurnScore;
        }
    }
}