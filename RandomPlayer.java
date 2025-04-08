/**
 * The AiRandomPlayer class represents an AI player that randomly decides whether to continue rolling
 * after each roll. If the player rolls a 6, the turn ends with a score of 0.
 */
public class RandomPlayer extends Player {
    private final Dice dice; // Dice object for rolling

    /**
     * Constructor for the AiRandomPlayer class.
     *
     * @param name The name of the player.
     */
    public RandomPlayer(String name) {
        super(name);
        this.dice = new Dice(6); // Standard 6-sided die
    }

    /**
     * Simulates the player's turn. The player rolls the dice and randomly decides whether to continue
     * rolling or end the turn. If the player rolls a 6, the turn ends with a score of 0.
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
                boolean continueRolling = Math.random() < 0.5;
                if (!continueRolling) {
                    System.out.println("   Randomly chose to end turn. Score for this turn: " + turnScore);
                    return turnScore;
                }
            }
        }
    }
}