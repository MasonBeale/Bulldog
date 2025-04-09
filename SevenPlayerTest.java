import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;
/**
 * Tests for sevenplayer
 * @author Mason Beale with DeepSeek
 */
public class SevenPlayerTest {

    // Helper method to create a SevenPlayer with controlled dice
    private SevenPlayer createPlayerWithFakeDice(List<Integer> rolls) {
        SevenPlayer player = new SevenPlayer("TestPlayer", new FakeRandom(6, rolls));
        return player;
    }

    @Test
    public void testPlay_immediateSix_returnsZero() {
        SevenPlayer player = createPlayerWithFakeDice(Arrays.asList(6));
        assertEquals(0, player.play());
    }

    @Test
    public void testPlay_reachesSevenInOneRoll() {
        SevenPlayer player = createPlayerWithFakeDice(Arrays.asList(7)); // Wraps to 1
        assertEquals(7, player.play());
    }

    @Test
    public void testPlay_reachesSevenInMultipleRolls() {
        SevenPlayer player = createPlayerWithFakeDice(Arrays.asList(2, 3, 2)); // Sum is 7
        assertEquals(7, player.play());
    }

    @Test
    public void testPlay_exceedsSevenInMultipleRolls() {
        SevenPlayer player = createPlayerWithFakeDice(Arrays.asList(4, 4)); // Sum is 8
        assertEquals(8, player.play());
    }

    @Test
    public void testPlay_rollsSixAfterSomePoints_returnsZero() {
        SevenPlayer player = createPlayerWithFakeDice(Arrays.asList(2, 3, 6)); // Would be 5 but then 6
        assertEquals(0, player.play());
    }

    @Test
    public void testPlay_wrappedNumbersWorkCorrectly() {
        // 7 wraps to 1, 8 wraps to 2, etc.
        SevenPlayer player = createPlayerWithFakeDice(Arrays.asList(7, 8, 9, 10)); // Wraps to 1, 2, 3, 4 (sum = 10)
        assertEquals(10, player.play());
    }

    @Test
    public void testPlay_continuesUntilSevenOrMore() {
        SevenPlayer player = createPlayerWithFakeDice(Arrays.asList(1, 1, 1, 1, 1, 1, 1)); // 7 rolls of 1
        assertEquals(7, player.play());
    }
}