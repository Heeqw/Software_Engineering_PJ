package BlackJack.personModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerActionTest {

    @Test
    public void testPlayerActionToString() {
        assertEquals("hit", PlayerAction.HIT.toString());
        assertEquals("stand", PlayerAction.STAND.toString());
        assertEquals("double", PlayerAction.DOUBLE_DOWN.toString());
        assertEquals("split", PlayerAction.SPLIT.toString());
        assertEquals("insurance", PlayerAction.INSURANCE.toString());
        assertEquals("surrender", PlayerAction.SURRENDER.toString());
    }
}
