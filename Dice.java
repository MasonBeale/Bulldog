import java.util.Random;

/**
 * The Dice class represents a dice with a specified number of sides.
 * It provides a method to roll the dice and generate a random number.
 */
public class Dice {
    private final int sides; // Number of sides on the dice
    private final Random random; // Random number generator

    /**
     * Constructor for the Dice class.
     *
     * @param sides The number of sides on the dice.
     */
    public Dice(int sides) {
        this.sides = sides;
        this.random = new Random();
    }

    /**
     * Rolls the dice and returns a random number between 1 and the number of sides.
     *
     * @return A random number between 1 and the number of sides.
     */
    public int roll() {
        return random.nextInt(sides) + 1; // Generates a number between 1 and sides (inclusive)
    }
}