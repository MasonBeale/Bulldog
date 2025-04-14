public class SevenPlayer extends Player {
    public SevenPlayer(String name, RandomDice dice) {
        super(name, dice);
    }

    @Override
    protected boolean shouldContinue(GameStatus gameStatus) {
        int currentTurnScore = gameStatus.getCurrentTurnScore(); // Assuming this is added to GameStatus
        return currentTurnScore < 7;
    }

    @Override
    protected void logTurnEnd(int score, String message) {
        System.out.println("   Reached 7 points. Ending turn. Score for this turn: " + score);
    }
}