package BlackJack.sessionModel;

import BlackJack.personModel.Player;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class SimpleModeTest {

    @Test
    public void testCanDoubleDown() {
        ArrayList<Player> players = new ArrayList<>();
        SimpleMode simpleMode = new SimpleMode(players);
        assertFalse(simpleMode.canDoubleDown());
    }

    @Test
    public void testCanPlaceBet() {
        ArrayList<Player> players = new ArrayList<>();
        SimpleMode simpleMode = new SimpleMode(players);
        assertFalse(simpleMode.canPlaceBet());
    }

    @Test
    public void testCanSplitHands() {
        ArrayList<Player> players = new ArrayList<>();
        SimpleMode simpleMode = new SimpleMode(players);
        assertFalse(simpleMode.canSplitHands());
    }

    @Test
    public void testCanTakeInsurance() {
        ArrayList<Player> players = new ArrayList<>();
        SimpleMode simpleMode = new SimpleMode(players);
        assertFalse(simpleMode.canTakeInsurance());
    }

    @Test
    public void testCanSurrender() {
        ArrayList<Player> players = new ArrayList<>();
        SimpleMode simpleMode = new SimpleMode(players);
        assertFalse(simpleMode.canSurrender());
    }

    @Test
    public void testPlayerPlaceBet() {
        ArrayList<Player> players = new ArrayList<>();
        SimpleMode simpleMode = new SimpleMode(players);
        Player player = new Player("TestPlayer");
        assertFalse(simpleMode.playerPlaceBet(player, 50));
    }

    @Test
    public void testCanPlayerSplitHands() {
        ArrayList<Player> players = new ArrayList<>();
        SimpleMode simpleMode = new SimpleMode(players);
        Player player = new Player("TestPlayer");
        assertFalse(simpleMode.canPlayerSplitHands(player));
    }
}
