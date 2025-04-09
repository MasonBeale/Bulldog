import java.security.SecureRandom;
import java.util.Random;

/**
 * The Dice class represents a dice with a specified number of sides.
 * It extends RandomDice and provides secure random number generation.
 * @author Mason Beale with DeepSeek
 */
public class Dice extends RandomDice {
    private static final Random random = new SecureRandom(); // Singleton Random instance

    /**
     * Constructor for the Dice class.
     * @param sides The number of sides on the dice (must be > 1)
     */
    public Dice(int sides) {
        super(sides); // Calls RandomDice constructor which validates sides > 1
    }

    /**
     * Rolls the dice using secure random number generation.
     * @return A random number between 1 and the number of sides.
     */
    @Override
    public int roll() {
        return random.nextInt(sides) + 1;
    }
}