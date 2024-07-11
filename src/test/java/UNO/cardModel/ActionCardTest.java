package UNO.cardModel;
import org.junit.Test;
import static org.junit.Assert.*;
public class ActionCardTest {
    @Test
    public void testActionCardCreation() {
        ActionCard card = new ActionCard(Color.BLUE, "SKIP");
        assertEquals(Color.BLUE, card.getColor());
        assertEquals(Type.ACTION, card.getType());
        assertEquals(20, card.getScore());
        assertEquals("SKIP", card.getDetail());
        assertEquals("ActionCard: BLUE, SKIP", card.toString());
    }
}
