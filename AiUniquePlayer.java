public class AiUniquePlayer extends Player {

    public AiUniquePlayer(String name) {
        super(name);
    }

    @Override
    public int play() {
        int turnScore = 0;
        while (true) {
            int roll = (int) (Math.random() * 6 + 1);
            System.out.println("   Player " + getName() + " rolled a " + roll);
            if (roll == 6) {
                System.out.println("   Rolled a 6! Turn over. Score for this turn: 0");
                return 0;
            } else {
                turnScore += roll;
                System.out.println("   Current turn score: " + turnScore);
                // Unique strategy: End turn if turnScore is a multiple of 10
                if (turnScore % 10 == 0) {
                    System.out.println("   Turn score is a multiple of 10. Ending turn. Score for this turn: " + turnScore);
                    return turnScore;
                }
            }
        }
    }
}