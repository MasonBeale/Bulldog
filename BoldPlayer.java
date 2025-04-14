public class BoldPlayer extends Player {
    private final int winningScore;

    public BoldPlayer(String name, RandomDice dice, int winningScore) {
        super(name, dice);
        this.winningScore = winningScore;
    }

    @Override
    protected boolean shouldContinue(GameStatus gameStatus) {
        int currentTotalScore = gameStatus.getCurrentPlayer().getScore();
        int currentTurnScore = gameStatus.getCurrentTurnScore();
        return (currentTotalScore + currentTurnScore) < winningScore;
    }

    @Override
    protected void logTurnEnd(int score, String message) {
        System.out.println("   Player " + getName() + " scored " + score + " for their turn");
    }
}