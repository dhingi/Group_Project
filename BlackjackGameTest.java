import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class BlackjackGameTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player("TestPlayer", 500);
    }

    @Test
    public void testPlayerInitializationGood() {
        assertEquals("TestPlayer", player.getName());
        assertEquals(500, player.getBalance(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlayerInitializationBadEmptyName() {
        new Player("", 500);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPlayerInitializationBadNegativeBalance() {
        new Player("NegativeBalance", -100);
    }

    @Test
    public void testPlayerInitializationBoundaryMinBalance() {
        player.setBalance(0);
        assertEquals(0, player.getBalance(), 0);
    }

    @Test
    public void testPlayerInitializationBoundaryMaxBalance() {
        player.setBalance(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, player.getBalance(), 0);
    }

    // More tests for other methods and units

    // Manual Test Script 1
    // Test placing a bet within available balance
    @Test
    public void manualTestPlacingBetGood() {
        // Steps:
        // 1. Set up player balance: 1000
        // 2. Place a bet of 200
        // 3. Verify player's new balance is 800
        Player player = new Player("ManualTestPlayer", 1000);
        player.placeBet(200);
        assertEquals(800, player.getBalance(), 0);
    }

    // Manual Test Script 2
    // Test player busts by drawing cards
    @Test
    public void manualTestPlayerBust() {
        // Steps:
        // 1. Set up player hand: King of HEART, Queen of SPADE
        // 2. Draw a card (10 of CLUB)
        // 3. Draw another card (5 of DIAMOND)
        // 4. Verify player's hand value is 25 (bust)
        Player player = new Player("ManualTestPlayer", 500);
        player.addToHand(new Card(Suit.HEART, 10));
        player.addToHand(new Card(Suit.SPADE, 12)); // Queen
        player.addToHand(new Card(Suit.CLUB, 10));
        player.addToHand(new Card(Suit.DIAMOND, 5));
        assertEquals(25, player.getScore());
    }

    // Manual Test Script 3
// Test placing a bet exceeding available balance
@Test(expected = IllegalArgumentException.class)
public void manualTestPlacingBetExceedingBalance() {
    // Steps:
    // 1. Set up player balance: 100
    // 2. Try placing a bet of 200 (exceeds balance)
    Player player = new Player("ManualTestPlayer", 100);
    player.placeBet(200);
}

// Manual Test Script 4
// Test player wins with a high-value hand
@Test
public void manualTestPlayerWinsHighValueHand() {
    // Steps:
    // 1. Set up player hand: 8 of HEART, 7 of SPADE
    // 2. Dealer's face-up card: 6 of CLUB
    // 3. Player chooses to stand
    // 4. Verify player wins
    player.addToHand(new Card(Suit.HEART, 8));
    player.addToHand(new Card(Suit.SPADE, 7));
    dealer.addToHand(new Card(Suit.CLUB, 6));
    assertEquals("win", player.determineOutcome(dealer.getScore()));
}

// Manual Test Script 5
// Test dealer wins with a high-value hand
@Test
public void manualTestDealerWinsHighValueHand() {
    // Steps:
    // 1. Set up player hand: 10 of HEART, 9 of SPADE
    // 2. Dealer's face-up card: 8 of CLUB
    // 3. Player chooses to stand
    // 4. Verify dealer wins
    player.addToHand(new Card(Suit.HEART, 10));
    player.addToHand(new Card(Suit.SPADE, 9));
    dealer.addToHand(new Card(Suit.CLUB, 8));
    assertEquals("lose", player.determineOutcome(dealer.getScore()));
}

// Manual Test Script 6
// Test player and dealer tie
@Test
public void manualTestTieGame() {
    // Steps:
    // 1. Set up player hand: 9 of HEART, 9 of SPADE
    // 2. Dealer's face-up card: 10 of CLUB
    // 3. Player chooses to stand
    // 4. Verify it's a tie
    player.addToHand(new Card(Suit.HEART, 9));
    player.addToHand(new Card(Suit.SPADE, 9));
    dealer.addToHand(new Card(Suit.CLUB, 10));
    assertEquals("tie", player.determineOutcome(dealer.getScore()));
}

}
