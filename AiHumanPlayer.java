import javax.swing.*;

/**
 * The AiHumanPlayer class represents a human-controlled player in the game.
 * This player interacts with the game through a graphical user interface (GUI).
 */
public class AiHumanPlayer extends Player {
    private final Dice dice; // Dice object for rolling
    private BulldogGUI gui; // Reference to the BulldogGUI for updating the game state

    /**
     * Constructor for the AiHumanPlayer class.
     *
     * @param name        The name of the player.
     * @param parentFrame The parent JFrame for the GUI.
     * @param gui         The BulldogGUI instance for updating the game state.
     */
    public AiHumanPlayer(String name, JFrame parentFrame, BulldogGUI gui) {
        super(name);
        this.dice = new Dice(6); // Standard 6-sided die
        this.gui = gui; // Initialize the reference to BulldogGUI
    }

    /**
     * Handles the roll action for the human player. Updates the game log and turn score in the BulldogGUI.
     *
     * @return The current turn score after rolling the dice.
     */
    @Override
    public int play() {
        int roll = dice.roll(); // Use the Dice object to roll
        gui.appendToGameLog("   Player " + getName() + " rolled a " + roll + "\n");
        if (roll == 6) {
            gui.appendToGameLog("   Rolled a 6! Turn over. Score for this turn: 0\n");
            gui.setTurnScore(0); // Reset turn score
            gui.endTurn(); // Signal to end the turn
        } else {
            int newTurnScore = gui.getTurnScore() + roll;
            gui.setTurnScore(newTurnScore); // Update turn score
            gui.appendToGameLog("   Current turn score: " + newTurnScore + "\n");
        }
        return gui.getTurnScore();
    }
}