package UNO.cardModel;
import org.junit.Test;
import static org.junit.Assert.*;
public class WildCardTest {
    @Test
    public void testWildCardCreation() {
        WildCard card = new WildCard("CHANGE_COLOR");
        assertEquals(Color.BLACK, card.getColor());
        assertEquals(Type.WILD, card.getType());
        assertEquals(50, card.getScore());
        assertEquals("CHANGE_COLOR", card.getDetail());
        assertEquals("WildCard: BLACK, CHANGE_COLOR", card.toString());
    }
}
