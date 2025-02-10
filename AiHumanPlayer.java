import java.util.Scanner;

public class AiHumanPlayer extends Player {

    public AiHumanPlayer(String name) {
        super(name);
    }

    @Override
    public int play() {
        Scanner scanner = new Scanner(System.in);
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
                System.out.print("   Continue rolling? (y/n): ");
                String input = scanner.nextLine().trim().toLowerCase();
                if (input.equals("n")) {
                    System.out.println("   Turn ended. Score for this turn: " + turnScore);
                    return turnScore;
                }
            }
        }
    }
}