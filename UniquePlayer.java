/**
 * The AiUniquePlayer class represents an AI player that ends their turn when the turn score
 * is a multiple of 10. If the player rolls a 6, the turn ends with a score of 0.
 */
public class UniquePlayer extends Player {
    private final RandomDice dice; // Dice object for rolling

    /**
     * Constructor for the AiUniquePlayer class.
     *
     * @param name The name of the player.
     */
    public UniquePlayer(String name, RandomDice dice) {
        super(name);
        this.dice = dice;
    }

    /**
     * Simulates the player's turn. The player rolls the dice and continues rolling until
     * the turn score is a multiple of 10 or a 6 is rolled.
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
                if (turnScore % 10 == 0) {
                    System.out.println("   Turn score is a multiple of 10. Ending turn. Score for this turn: " + turnScore);
                    return turnScore;
                }
            }
        }
    }
}