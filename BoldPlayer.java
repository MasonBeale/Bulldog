public class BoldPlayer extends Player {
    private final Dice dice; // Dice object for rolling
    private final int winningScore; // Winning score passed from BulldogGUI

    public BoldPlayer(String name, int winningScore) {
        super(name);
        this.dice = new Dice(6); // Standard 6-sided die
        this.winningScore = winningScore;
    }

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