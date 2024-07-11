package UNO.sessionModel;

import UNO.cardModel.*;
import UNO.personModel.Player;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class GamingSessionsTest {

    private GamingSessions gamingSessions;
    private List<Player> players;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        gamingSessions = new GamingSessions(players);
    }

    @Test
    public void testDetermineStartingPlayer() {
        Player startingPlayer = gamingSessions.determineStartingPlayer();
        assertNotNull(startingPlayer);
        assertTrue(players.contains(startingPlayer));
    }

    @Test
    public void testInitialPlayerHands() {
        gamingSessions.initialPlayerHands();
        for (Player player : players) {
            assertEquals(7, player.getHandSize());
        }
    }

    @Test
    public void testDrawCard() {
        Player player = players.get(0);
        int initialSize = player.getHandSize();
        gamingSessions.drawCard(player);
        assertEquals(initialSize + 1, player.getHandSize());
    }

    @Test
    public void testIsPlayableCard() {
        Card numberCard = new NumberCard(Color.RED, "5");
        Card actionCard = new ActionCard(Color.RED, "SKIP");
        Card wildCard = new WildCard("CHANGE_COLOR");

        // 没有当前牌的情况下
        assertTrue(gamingSessions.isPlayable(numberCard));
        assertTrue(gamingSessions.isPlayable(actionCard));
        assertTrue(gamingSessions.isPlayable(wildCard));

        // 设置当前牌
        gamingSessions.discardCard(players.get(0), numberCard);
        assertTrue(gamingSessions.isPlayable(numberCard));
        assertTrue(gamingSessions.isPlayable(actionCard));
        assertTrue(gamingSessions.isPlayable(wildCard));
    }

    @Test
    public void testDiscardCard() {
        Player player = players.get(0);
        Card card = new NumberCard(Color.RED, "5");
        player.addCardToHand(card);

        assertTrue(gamingSessions.discardCard(player, card));
        assertEquals(0, player.getHandSize());
        assertEquals(card, gamingSessions.getCurrentCard());
    }

    @Test
    public void testCallUno() {
        Player player = players.get(0);
        player.addCardToHand(new NumberCard(Color.RED, "5"));
        player.addCardToHand(new NumberCard(Color.BLUE, "7"));

        gamingSessions.callUno(player);
        assertTrue(player.getHandSize() == 2 || player.getHandSize() == 4);
    }

    @Test
    public void testReportUno() {
        Player player = players.get(0);
        Player previousPlayer = players.get(1);

        previousPlayer.addCardToHand(new NumberCard(Color.RED, "5"));
        previousPlayer.addCardToHand(new NumberCard(Color.YELLOW, "7"));
        previousPlayer.removeCardFromHand(previousPlayer.getCardFromHand(0)); // 模拟出一张牌

        gamingSessions.reportUno(player);
        assertTrue(previousPlayer.getHandSize() == 1 || previousPlayer.getHandSize() == 3); // 根据逻辑，可能是举报成功或失败后的牌数
    }

    @Test
    public void testCheckGameOver() {
        Player player = players.get(0);
        player.clearHand(); // 模拟玩家出完所有牌
        assertTrue(gamingSessions.CheckGameOver());
        assertEquals(player, gamingSessions.getWinner());
    }

    @Test
    public void testDetermineScoreWinner() {
        Player player1 = players.get(0);
        Player player2 = players.get(1);

        player1.addCardToHand(new NumberCard(Color.RED, "5"));
        player2.addCardToHand(new NumberCard(Color.YELLOW, "7"));
        player2.addCardToHand(new NumberCard(Color.BLUE, "3"));

        gamingSessions.determineScoreWinner();
        assertEquals(player1, gamingSessions.getWinner());
    }
}
