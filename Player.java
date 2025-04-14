/********************************************************/
/* David Levine                                         */
/* Login ID: david.b.levine@maine.edu                   */
/* COS 497, Summer 2024                                 */
/* Programming Assignment 6                             */
/* abstract Player class: holds generic info about a    */
/*           player of the game Bulldog                 */
/*      See Kettering University, CS-101, Prog 6        */
/********************************************************/

public abstract class Player {
    
    private String name;    // The name of the Player
    private int score;      // The score earned by this Player during the game
    protected final RandomDice dice; // Dice object for rolling
    
    /********************************************************/
    /* Constructor: Player                                  */
    /* Purpose: Create a new Player object                  */
    /* Parameters:                                          */
    /*   String name:  the name of the Player being created */
    /*   RandomDice dice: the dice object to use for rolls  */
    /********************************************************/
    public Player(String name, RandomDice dice) {
        this.name = name;
        this.score = 0;
        this.dice = dice;
    }
    
    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public int play(GameStatus gameStatus) {
        int turnScore = 0;
        while (true) {
            int roll = dice.roll();
            logRoll(roll);
            if (roll == 6) {
                logTurnEnd(0, "Rolled a 6! Turn over.");
                return 0;
            } else {
                turnScore += roll;
                logTurnProgress(turnScore);
                if (!shouldContinue(gameStatus)) {
                    logTurnEnd(turnScore, "Ending turn.");
                    return turnScore;
                }
            }
        }
    }
    
    protected void logRoll(int roll) {
        System.out.println("   Player " + getName() + " rolled a " + roll);
    }
    
    protected void logTurnProgress(int currentScore) {
        System.out.println("   Current turn score: " + currentScore);
    }
    
    protected void logTurnEnd(int score, String message) {
        System.out.println("   " + message + " Score for this turn: " + score);
    }
    
    protected abstract boolean shouldContinue(GameStatus gameStatus);
}