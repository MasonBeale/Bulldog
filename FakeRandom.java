import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A deterministic implementation of RandomDice that uses a predefined sequence of numbers.
 * The sequence automatically repeats when exhausted.
 */
public class FakeRandom extends RandomDice {
    private Iterator<Integer> numberStream;
    private final List<Integer> originalSequence;

    /**
     * Constructor for FakeRandom with a sequence of numbers.
     * @param sides The number of sides on the dice (must be > 1)
     * @param numberStream The sequence of numbers to use for rolls
     */
    public FakeRandom(int sides, List<Integer> numberStream) {
        super(sides);
        if (numberStream == null || numberStream.isEmpty()) {
            throw new IllegalArgumentException("Number sequence cannot be null or empty");
        }
        this.originalSequence = new ArrayList<>(numberStream);
        this.numberStream = this.originalSequence.iterator();
    }

    /**
     * Rolls the dice by returning the next number in the sequence.
     * If the sequence is exhausted, it automatically resets to the beginning.
     * If the number is larger than the number of sides, it will be wrapped around using modulo.
     * @return A number between 1 and the number of sides
     */
    @Override
    public int roll() {
        if (!numberStream.hasNext()) {
            reset();
        }

        int next = numberStream.next();
        // Handle numbers that are too large by taking modulo sides and adding 1
        // This ensures the result is always between 1 and sides
        return ((next - 1) % sides) + 1;
    }

    /**
     * Resets the sequence to start from the beginning again.
     */
    public void reset() {
        this.numberStream = new ArrayList<>(originalSequence).iterator();
    }

    /**
     * Returns the original sequence used by this FakeRandom instance.
     * @return The original sequence of numbers
     */
    public List<Integer> getOriginalSequence() {
        return new ArrayList<>(originalSequence);
    }
}