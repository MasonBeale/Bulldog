/**
 * Abstract superclass for dice implementations that keeps track of the number of sides
 * and defines the core dice functionality.
 * @author Mason Beale with DeepSeek
 */
public abstract class RandomDice {
    protected final int sides; // Number of sides on the dice

    /**
     * Constructor for the RandomDice class.
     * @param sides The number of sides on the dice (must be > 1)
     */
    public RandomDice(int sides) {
        if (sides <= 1) {
            throw new IllegalArgumentException("Dice must have at least 2 sides");
        }
        this.sides = sides;
    }

    /**
     * Abstract method to roll the dice.
     * @return A random number between 1 and the number of sides.
     */
    public abstract int roll();

}