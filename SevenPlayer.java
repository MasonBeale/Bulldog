/**
 * The SevenPlayer class represents an AI player that aims to reach a score of 7 in each turn.
 * If the player rolls a 6, the turn ends with a score of 0. Otherwise, the player continues rolling
 * until the turn score reaches at least 7.
 * @author Mason Beale with DeepSeek
 */
public class SevenPlayer extends Player {
    private final RandomDice dice; // Dice object for rolling

    /**
     * Constructor for the SevenPlayer class.
     *
     * @param name The name of the player.
     */
    public SevenPlayer(String name, RandomDice dice) {
        super(name);
        this.dice = dice;
    }

    /**
     * Simulates the player's turn. The player rolls the dice and continues rolling until
     * they either roll a 6 (ending the turn with a score of 0) or reach a turn score of at least 7.
     *
     * @return The score accumulated during the turn.
     */
    @Override
    public int play() {
        int turnScore = 0;
        while (true) {
            int roll = dice.roll(); // Use the Dice object to roll
            System.out.println("   Player " + getName() + " rolled a " + roll);
            if (roll == 6) {
                System.out.println("   Rolled a 6! Turn over. Score for this turn: 0");
                return 0;
            } else {
                turnScore += roll;
                System.out.println("   Current turn score: " + turnScore);
                if (turnScore >= 7) {
                    System.out.println("   Reached 7 points. Ending turn. Score for this turn: " + turnScore);
                    return turnScore;
                }
            }
        }
    }
}