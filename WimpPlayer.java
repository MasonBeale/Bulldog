public class WimpPlayer extends Player {
    private final Dice dice; // Dice object for rolling

    public WimpPlayer(String name) {
        super(name);
        this.dice = new Dice(6); // Standard 6-sided die
    }

    @Override
    public int play() {
        int roll = dice.roll(); // Use the Dice object to roll
        System.out.print("   Player " + getName() + " rolled " + roll);
        if (roll != 6) {
            System.out.println(" and chose not to continue, scoring " + roll + " for the turn.");
        } else {
            roll = 0;
            System.out.println(" and scored 0 for the turn.");
        }
        return roll;
    }
}