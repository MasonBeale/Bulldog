
import org.junit.Test;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for the FakeRandom class
 */
public class FakeRandomTest {
    
    @Test
    public void testConstructor_validSequence() {
        List<Integer> sequence = Arrays.asList(1, 2, 3);
        FakeRandom fakeRandom = new FakeRandom(6, sequence);
        assertEquals(sequence, fakeRandom.getOriginalSequence());
    }

    @Test
    public void testConstructor_nullSequence_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FakeRandom(6, null);
        });
    }

    @Test
    public void testConstructor_emptySequence_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new FakeRandom(6, Arrays.asList());
        });
    }

    @Test
    public void testRoll_returnsNumbersInSequence() {
        List<Integer> sequence = Arrays.asList(1, 3, 5);
        FakeRandom fakeRandom = new FakeRandom(6, sequence);
        
        assertEquals(1, fakeRandom.roll());
        assertEquals(3, fakeRandom.roll());
        assertEquals(5, fakeRandom.roll());
    }

    @Test
    public void testRoll_wrapsAroundWhenSequenceEnds() {
        List<Integer> sequence = Arrays.asList(1, 2);
        FakeRandom fakeRandom = new FakeRandom(6, sequence);
        
        assertEquals(1, fakeRandom.roll());
        assertEquals(2, fakeRandom.roll());
        assertEquals(1, fakeRandom.roll()); // Should wrap around
    }

    @Test
    public void testRoll_numbersLargerThanSides_areWrapped() {
        List<Integer> sequence = Arrays.asList(7, 8, 12);
        FakeRandom fakeRandom = new FakeRandom(6, sequence);
        
        // (7-1)%6 +1 = 1, (8-1)%6 +1 = 2, (12-1)%6 +1 = 6
        assertEquals(1, fakeRandom.roll());
        assertEquals(2, fakeRandom.roll());
        assertEquals(6, fakeRandom.roll());
    }

    @Test
    public void testReset_resetsSequence() {
        List<Integer> sequence = Arrays.asList(1, 2);
        FakeRandom fakeRandom = new FakeRandom(6, sequence);
        
        assertEquals(1, fakeRandom.roll());
        assertEquals(2, fakeRandom.roll());
        fakeRandom.reset();
        assertEquals(1, fakeRandom.roll());
    }
}