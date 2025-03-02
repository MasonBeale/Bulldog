public class AiRandomPlayer extends Player {
    private final Dice dice; // Dice object for rolling

    public AiRandomPlayer(String name) {
        super(name);
        this.dice = new Dice(6); // Standard 6-sided die
    }

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