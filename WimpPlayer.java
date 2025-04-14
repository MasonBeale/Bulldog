public class WimpPlayer extends Player {
    public WimpPlayer(String name, RandomDice dice) {
        super(name, dice);
    }

    @Override
    public int play(GameStatus gameStatus) {
        int roll = dice.roll();
        if (roll != 6) {
            System.out.println("   Player " + getName() + " rolled " + roll + 
                             " and chose not to continue, scoring " + roll + " for the turn.");
        } else {
            roll = 0;
            System.out.println("   Player " + getName() + " rolled 6 and scored 0 for the turn.");
        }
        return roll;
    }

    @Override
    protected boolean shouldContinue(GameStatus gameStatus) {
        return false; // WimpPlayer never continues after first roll
    }
}