import javax.swing.*;

public class HumanPlayer extends Player {
    private Bulldog game;

    public HumanPlayer(String name, RandomDice dice, JFrame parentFrame, Bulldog game) {
        super(name, dice);
        this.game = game;
    }

    @Override
    public int play(GameStatus gameStatus) {
        int roll = dice.roll();
        game.appendToGameLog("   Player " + getName() + " rolled a " + roll + "\n");
        if (roll == 6) {
            game.appendToGameLog("   Rolled a 6! Turn over. Score for this turn: 0\n");
            return 0; // Just return 0, let Bulldog handle the score update
        } else {
            int newTurnScore = game.getTurnScore() + roll;
            game.appendToGameLog("   Current turn score: " + newTurnScore + "\n");
            return roll; // Return just the roll, Bulldog will handle turn score
        }
    }

    @Override
    protected boolean shouldContinue(GameStatus gameStatus) {
        return false; // GUI handles continuation
    }
}