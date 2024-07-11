package UNO.cardModel;
import org.junit.Test;
import static org.junit.Assert.*;
public class CardTest {
    @Test
    public void testCardCreation() {
        Card card = new Card(Color.RED, Type.NUMBER, 5, "5");
        assertEquals(Color.RED, card.getColor());
        assertEquals(Type.NUMBER, card.getType());
        assertEquals(5, card.getScore());
        assertEquals("5", card.getDetail());
    }
}
