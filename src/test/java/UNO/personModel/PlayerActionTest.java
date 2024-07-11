package UNO.personModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerActionTest {

    @Test
    public void testPlayerActionToString() {
        assertEquals("discard", PlayerAction.DISCARD.toString());
        assertEquals("draw", PlayerAction.DRAW.toString());
        assertEquals("uno", PlayerAction.UNO.toString());
        assertEquals("report", PlayerAction.REPORT.toString());
    }
}
