package BlackJack.sessionModel;

import BlackJack.cardModel.Card;
import BlackJack.cardModel.Rank;
import BlackJack.cardModel.Suit;
import BlackJack.personModel.Player;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class GamblerModeTest {

    @Test
    public void testCanDoubleDown() {
        ArrayList<Player> players = new ArrayList<>();
        GamblerMode gamblerMode = new GamblerMode(players);
        assertTrue(gamblerMode.canDoubleDown());
    }

    @Test
    public void testCanPlaceBet() {
        ArrayList<Player> players = new ArrayList<>();
        GamblerMode gamblerMode = new GamblerMode(players);
        assertTrue(gamblerMode.canPlaceBet());
    }

    @Test
    public void testCanSplitHands() {
        ArrayList<Player> players = new ArrayList<>();
        GamblerMode gamblerMode = new GamblerMode(players);
        assertTrue(gamblerMode.canSplitHands());
    }

    @Test
    public void testCanTakeInsurance() {
        ArrayList<Player> players = new ArrayList<>();
        GamblerMode gamblerMode = new GamblerMode(players);
        assertFalse(gamblerMode.canTakeInsurance());
    }

    @Test
    public void testCanSurrender() {
        ArrayList<Player> players = new ArrayList<>();
        GamblerMode gamblerMode = new GamblerMode(players);
        assertFalse(gamblerMode.canSurrender());
    }

    @Test
    public void testPlayerPlaceBet() {
        ArrayList<Player> players = new ArrayList<>();
        GamblerMode gamblerMode = new GamblerMode(players);
        Player player = new Player("TestPlayer");
        assertTrue(gamblerMode.playerPlaceBet(player, 50));
    }

    @Test
    public void testCanPlayerSplitHands() {
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player("TestPlayer");
        players.add(player);
        GamblerMode gamblerMode = new GamblerMode(players);
        player.GetOneCard(new Card(Rank.Eight, Suit.CLUBS));
        player.GetOneCard(new Card(Rank.Eight, Suit.CLUBS));
        assertEquals(2, player.getHandList().get(0).getCards().size());
        assertEquals(Rank.Eight.getNum(), player.getHandList().get(0).getOneCard(0).getRank());
        assertEquals(Rank.Eight.getNum(), player.getHandList().get(0).getOneCard(1).getRank());

        assertTrue(gamblerMode.canPlayerSplitHands(player));
    }
}
