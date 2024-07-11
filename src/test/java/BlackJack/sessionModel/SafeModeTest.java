package BlackJack.sessionModel;

import BlackJack.cardModel.Card;
import BlackJack.cardModel.Rank;
import BlackJack.cardModel.Suit;
import BlackJack.personModel.Player;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class SafeModeTest {

    @Test
    public void testCanDoubleDown() {
        ArrayList<Player> players = new ArrayList<>();
        SafeMode safeMode = new SafeMode(players);
        assertTrue(safeMode.canDoubleDown());
    }

    @Test
    public void testCanPlaceBet() {
        ArrayList<Player> players = new ArrayList<>();
        SafeMode safeMode = new SafeMode(players);
        assertTrue(safeMode.canPlaceBet());
    }

    @Test
    public void testCanSplitHands() {
        ArrayList<Player> players = new ArrayList<>();
        SafeMode safeMode = new SafeMode(players);
        assertTrue(safeMode.canSplitHands());
    }

    @Test
    public void testCanTakeInsurance() {
        ArrayList<Player> players = new ArrayList<>();
        SafeMode safeMode = new SafeMode(players);
        assertTrue(safeMode.canTakeInsurance());
    }

    @Test
    public void testCanSurrender() {
        ArrayList<Player> players = new ArrayList<>();
        SafeMode safeMode = new SafeMode(players);
        assertTrue(safeMode.canSurrender());
    }

    @Test
    public void testPlayerPlaceBet() {
        ArrayList<Player> players = new ArrayList<>();
        SafeMode safeMode = new SafeMode(players);
        Player player = new Player("TestPlayer");
        assertTrue(safeMode.playerPlaceBet(player, 50));
    }


    @Test
    public void testCanPlayerSplitHands() {
        // 创建玩家列表
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player("TestPlayer");
        players.add(player);

        // 初始化 SafeMode
        SafeMode safeMode = new SafeMode(players);

        // 给玩家发两张相同点数的牌
        player.GetOneCard(new Card(Rank.Eight, Suit.CLUBS));
        player.GetOneCard(new Card(Rank.Eight, Suit.CLUBS));

        // 确认玩家手中有两张牌
        assertEquals(2, player.getHandList().get(0).getCards().size());

        // 确认两张牌点数相同
        assertEquals(Rank.Eight.getNum(), player.getHandList().get(0).getOneCard(0).getRank());
        assertEquals(Rank.Eight.getNum(), player.getHandList().get(0).getOneCard(1).getRank());

        // 断言玩家可以分牌
        assertTrue(safeMode.canPlayerSplitHands(player));
    }
}
