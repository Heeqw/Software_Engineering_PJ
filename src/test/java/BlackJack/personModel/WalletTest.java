package BlackJack.personModel;

import org.junit.Test;
import static org.junit.Assert.*;

public class WalletTest {

    @Test
    public void testChangeMoney() {
        Wallet wallet = new Wallet();
        wallet.changeMoney(20);
        assertEquals(70, wallet.getMoney());
        wallet.changeMoney(-30);
        assertEquals(40, wallet.getMoney());
    }

    @Test
    public void testCanChangeMoney() {
        Wallet wallet = new Wallet();
        assertTrue(wallet.canChangeMoney(30));
        assertFalse(wallet.canChangeMoney(60));
    }
}
