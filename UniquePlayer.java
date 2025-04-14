public class UniquePlayer extends Player {
    public UniquePlayer(String name, RandomDice dice) {
        super(name, dice);
    }

    @Override
    protected boolean shouldContinue(GameStatus gameStatus) {
        int currentTurnScore = gameStatus.getCurrentTurnScore(); // Assuming this is added to GameStatus
        return currentTurnScore % 10 != 0;
    }

    @Override
    protected void logTurnEnd(int score, String message) {
        System.out.println("   Turn score is a multiple of 10. Ending turn. Score for this turn: " + score);
    }
}