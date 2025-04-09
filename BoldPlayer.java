/**
 * The BoldPlayer class represents an AI player that continues rolling until they reach the winning score
 * or roll a 6. If a 6 is rolled, the turn ends with a score of 0.
 * @author Mason Beale with DeepSeek
 */
public class BoldPlayer extends Player {
    private final RandomDice dice; // Dice object for rolling
    private final int winningScore; // Winning score passed from BulldogGUI

    /**
     * Constructor for the BoldPlayer class.
     *
     * @param name         The name of the player.
     * @param winningScore The winning score required to win the game.
     */
    public BoldPlayer(String name, RandomDice dice, int winningScore) {
        super(name);
        this.dice = dice;
        this.winningScore = winningScore;
    }

    /**
     * Simulates the player's turn. The player continues rolling until they reach the winning score
     * or roll a 6. If a 6 is rolled, the turn ends with a score of 0.
     *
     * @return The score accumulated during the turn.
     */
    @Override
    public int play() {
        boolean win = false;
        int total = 0;
        while (!win) {
            int roll = dice.roll(); // Use the Dice object to roll
            System.out.println("   Player " + getName() + " rolled " + roll);
            if (roll != 6) {
                total = total + roll;
                if (total < winningScore) {
                    System.out.println("   Total is " + total + ". Rolling Again.");
                } else {
                    System.out.println("   Total is " + total + ".");
                    win = true;
                }
            } else {
                total = 0;
                System.out.println("   and scored 0 for the turn.");
                win = true;
                return total;
            }
        }
        System.out.println("   Player " + getName() + " scored " + total + " for their turn");
        return total;
    }
}