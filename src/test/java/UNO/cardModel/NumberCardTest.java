package UNO.cardModel;
import org.junit.Test;
import static org.junit.Assert.*;
public class NumberCardTest {
    @Test
    public void testNumberCardCreation() {
        NumberCard card = new NumberCard(Color.YELLOW, "3");
        assertEquals(Color.YELLOW, card.getColor());
        assertEquals(Type.NUMBER, card.getType());
        assertEquals(3, card.getScore());
        assertEquals("3", card.getDetail());
        assertEquals("NumberCard: YELLOW, 3", card.toString());
    }
}
