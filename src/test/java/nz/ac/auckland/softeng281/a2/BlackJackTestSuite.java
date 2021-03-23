package nz.ac.auckland.softeng281.a2;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(Suite.class)
@SuiteClasses({BlackJackTestSuite.Task1Test.class,
// BlackJackTestSuite.Task2Test.class,
// BlackJackTestSuite.Task3Test.class,
// BlackJackTestSuite.Task4Test.class,
// BlackJackTestSuite.YourTests.class
})
public class BlackJackTestSuite {
    @FixMethodOrder(MethodSorters.JVM)
    public static class Task1Test {
        Hand hand;
        Participant bot;

        ByteArrayOutputStream myOut;
        PrintStream origOut;

        @Before
        public void setUp() {
            hand = new Hand(10);
            bot = new BotPlayer("bot1");
            bot.getHands().add(hand);

            origOut = System.out;
            myOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(myOut));
        }

        @After
        public void tearDown() {
            System.setOut(origOut);
            if (myOut.toString().length() > 1) {
                System.out.println(System.lineSeparator() + "the System.out.print was :" + System.lineSeparator()
                        + myOut.toString());
            }
        }

        @Test
        public void testBotAreCreated() {
            BlackJack game = new BlackJack();
            assertNotNull("The field players in the BlackJack class should not be null", game.getPlayers());

            Utils.scanner = new Scanner("" + "5 hold " + System.getProperty("line.separator") + " "
                    + System.getProperty("line.separator") + " " + System.getProperty("line.separator") + " "
                    + System.getProperty("line.separator") + " " + "no");
            game.start();
            assertEquals("We should have three players, a human and two bots", 3, game.getPlayers().size());
            assertTrue("the first player should be human", game.getPlayers().get(0) instanceof HumanPlayer);
            assertTrue("the second player should be a bot", game.getPlayers().get(1) instanceof BotPlayer);
            assertTrue("the third player should be a bot", game.getPlayers().get(2) instanceof BotPlayer);
            assertTrue("I cannot find = \"Bot2's score is:\" did you name the Bots correctly: Bot1 and Bot2 ? ",
                    myOut.toString().contains("Bot2's score is: "));
        }

        @Test
        public void testBotHit10() {
            hand.addCard(new Card(Card.Rank.TWO, Card.Suit.DIAMONDS));
            hand.addCard(new Card(Card.Rank.EIGHT, Card.Suit.CLUBS));
            for (int i = 0; i <= 100; i++) {
                if (bot.decideAction().equals(Participant.Action.HOLD)) {
                    fail("the bot should not HOLD when the score is 10");
                }
            }

        }

        @Test
        public void testBotHit16() {
            hand.addCard(new Card(Card.Rank.QUEEN, Card.Suit.DIAMONDS));
            hand.addCard(new Card(Card.Rank.FIVE, Card.Suit.DIAMONDS));
            hand.addCard(new Card(Card.Rank.ACE, Card.Suit.DIAMONDS));
            for (int i = 0; i <= 100; i++) {
                if (bot.decideAction().equals(Participant.Action.HOLD)) {
                    fail("the bot should not HOLD when the score is 16");
                }
            }
        }

        @Test
        public void testBotHold17() {
            hand.addCard(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));
            hand.addCard(new Card(Card.Rank.SEVEN, Card.Suit.DIAMONDS));
            for (int i = 0; i <= 100; i++) {
                if (bot.decideAction().equals(Participant.Action.HIT)) {
                    fail("the bot should not HIT when the score is 17");
                }
            }
        }

        @Test
        public void testBotHold18() {
            hand.addCard(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));
            hand.addCard(new Card(Card.Rank.EIGHT, Card.Suit.DIAMONDS));
            for (int i = 0; i <= 100; i++) {
                if (bot.decideAction().equals(Participant.Action.HIT)) {
                    fail("the bot should not HIT when the score is 18");
                }
            }
        }

        @Test
        public void testBotHold21() {
            hand.addCard(new Card(Card.Rank.JACK, Card.Suit.DIAMONDS));
            hand.addCard(new Card(Card.Rank.ACE, Card.Suit.CLUBS));
            for (int i = 0; i <= 100; i++) {
                if (bot.decideAction().equals(Participant.Action.HIT)) {
                    fail("the bot should not HIT when the score is 21");
                }
            }
        }

        @Test
        public void testBotMakeBet() {
            assertTrue("a bet should be greater than zero", bot.makeABet() > 0);
        }

        @Test
        public void testBotMakeBetRandom() {
            for (int i = 0; i <= 100; i++) {
                int bet = bot.makeABet();
                assertFalse("should be from 1 to 100", bet < 1 || bet > 100);
            }
        }
    }

    @FixMethodOrder(MethodSorters.JVM)
    public static class Task2Test {

        Participant dealer;
        List<Participant> players;

        ByteArrayOutputStream myOut;
        PrintStream origOut;

        @Before
        public void setUp() {
            players = new ArrayList<>();
            players.add(new BotPlayer("Bot1"));
            players.add(new BotPlayer("Bot2"));
            players.add(new BotPlayer("Bot3"));
            dealer = new BotDealer("Dealer", players);
            origOut = System.out;
            myOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(myOut));
        }

        @After
        public void tearDown() {
            System.setOut(origOut);
            if (myOut.toString().length() > 1) {
                System.out.println(System.lineSeparator() + "the System.out.print was :" + System.lineSeparator()
                        + myOut.toString());
            }
        }

        @Test
        public void testDealerCreated() {
            BlackJack game = new BlackJack();
            assertNotNull("The field dealer in the BlackJack class should not be null", game.getDealer());
            Utils.scanner = new Scanner("" + "5 hold " + System.getProperty("line.separator") + " "
                    + System.getProperty("line.separator") + " " + System.getProperty("line.separator") + " "
                    + System.getProperty("line.separator") + " " + "no");
            game.start();
            assertNotNull("The field dealer in the BlackJack class should not be null", game.getDealer());
            // check again if Bots are fine
            assertTrue("the first player should be human", game.getPlayers().get(0) instanceof HumanPlayer);
            assertTrue("the second player should be a bot", game.getPlayers().get(1) instanceof BotPlayer);
            assertTrue("the third player should be a bot", game.getPlayers().get(2) instanceof BotPlayer);
            assertTrue("I cannot find = \"Dealer's score is:\" did you name the Dealer correctly and did they play? ",
                    myOut.toString().contains("Dealer's score is: "));
        }

        @Test
        public void testDealerHoldWinningAll() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));

            for (int i = 0; i <= 100; i++) {
                if (dealer.decideAction().equals(Participant.Action.HIT)) {
                    fail("the dealer is winning all three players it should not HIT");
                }
            }
        }

        @Test
        public void testDealerHoldWinningAllBusted() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.THREE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));

            for (int i = 0; i <= 100; i++) {
                if (dealer.decideAction().equals(Participant.Action.HIT)) {
                    fail("the dealer is winning all three players it should not HIT");
                }
            }
        }

        @Test
        public void testDealerHoldWinningAllSomeBusted() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.KING, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));

            for (int i = 0; i <= 100; i++) {
                if (dealer.decideAction().equals(Participant.Action.HIT)) {
                    fail("the dealer is winning all three players it should not HIT");
                }
            }
        }

        @Test
        public void testDealerHitLoosingAll() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TWO, Card.Suit.CLUBS),
                    new Card(Card.Rank.TWO, Card.Suit.DIAMONDS), new Card(Card.Rank.TEN, Card.Suit.CLUBS));

            for (int i = 0; i <= 100; i++) {
                if (dealer.decideAction().equals(Participant.Action.HOLD)) {
                    fail("the dealer is loosing with all 3 players why decided to HOLD?");
                }
            }
        }

        @Test
        public void testDealerHitLoosingAllDealerBusted() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TWO, Card.Suit.CLUBS),
                    new Card(Card.Rank.TWO, Card.Suit.DIAMONDS), new Card(Card.Rank.TEN, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.HEARTS));

            for (int i = 0; i <= 100; i++) {
                if (dealer.decideAction().equals(Participant.Action.HOLD)) {
                    fail("the dealer is loosing with all 3 players why decided to HOLD?");
                }
            }
        }

        @Test
        public void testDealerHoldWinningTwo() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.THREE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TWO, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.NINE, Card.Suit.CLUBS));

            for (int i = 0; i <= 100; i++) {
                if (dealer.decideAction().equals(Participant.Action.HIT)) {
                    fail("the dealer is winning 2 out of 3 players it should not HIT");
                }
            }
        }

        @Test
        public void testDealerHitLoosingTwo() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.THREE, Card.Suit.CLUBS),
                    new Card(Card.Rank.THREE, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TWO, Card.Suit.CLUBS),
                    new Card(Card.Rank.TWO, Card.Suit.DIAMONDS), new Card(Card.Rank.TEN, Card.Suit.CLUBS));

            for (int i = 0; i <= 100; i++) {
                if (dealer.decideAction().equals(Participant.Action.HOLD)) {
                    fail("the dealer is loosing with 2 out of 3 players it should not HOLD");
                }
            }
        }

    }

    @FixMethodOrder(MethodSorters.JVM)
    public static class Task3Test {

        Participant dealer;
        List<Participant> players;
        BlackJack game;
        ByteArrayOutputStream myOut;
        PrintStream origOut;

        @Before
        public void setUp() {
            game = new BlackJack();
            players = new ArrayList<>();
            players.add(new BotPlayer("Bot1"));
            players.add(new BotPlayer("Bot2"));
            players.add(new BotPlayer("Bot3"));
            dealer = new BotDealer("Dealer", players);
            game.setPlayers(players);
            game.setDealer(dealer);
            origOut = System.out;
            myOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(myOut));
        }

        @After
        public void tearDown() {
            System.setOut(origOut);
            if (myOut.toString().length() > 1) {
                System.out.println(System.lineSeparator() + "the System.out.print was :" + System.lineSeparator()
                        + myOut.toString());
            }
        }

        @Test
        public void testAllPlayersLost() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();
            assertFalse("none of the players won the game should not print that some one won",
                    myOut.toString().contains("wins"));
        }

        @Test
        public void testOnePlayerWon() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TWO, Card.Suit.CLUBS), new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.QUEEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.QUEEN, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            assertTrue("Bot1 won, you should print \"Bot1 wins\" ", myOut.toString().contains("Bot1 wins"));
            assertFalse("Bot2 didn't won, you should not print \"Bot2 wins\"", myOut.toString().contains("Bot2 wins"));
            assertFalse("Bot3 didn't won, you should not print \"Bot3 wins\"", myOut.toString().contains("Bot3 wins"));
        }

        @Test
        public void testTwoPlayersWon() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.KING, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            assertTrue("Bot1 won, you should print \"Bot1 wins\" ", myOut.toString().contains("Bot1 wins"));
            assertTrue("Bot2  won, you should print \"Bot2 wins\"", myOut.toString().contains("Bot2 wins"));
            assertFalse("Bot3 didn't won, you should not print \"Bot3 wins\"", myOut.toString().contains("Bot3 wins"));
        }

        @Test
        public void testAllPlayersWonDealerBust() {
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.ACE, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(2), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.KING, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            game.checkWinner();

            assertTrue("Bot1 won, you should print \"Bot1 wins\" ", myOut.toString().contains("Bot1 wins"));
            assertTrue("Bot2 won, you should print \"Bot2 wins\"", myOut.toString().contains("Bot2 wins"));
            assertTrue("Bot3 won, you should  print \"Bot3 wins\"", myOut.toString().contains("Bot3 wins"));
        }

    }

    @FixMethodOrder(MethodSorters.JVM)
    public static class Task4Test {

        Participant dealer;
        List<Participant> players;
        BlackJack game;
        ByteArrayOutputStream myOut;
        PrintStream origOut;

        @Before
        public void setUp() {
            players = new ArrayList<>();
            players.add(new BotPlayer("Bot1"));
            players.add(new BotPlayer("Bot2"));
            players.add(new BotPlayer("Bot3"));
            dealer = new BotDealer("Dealer", players);
            game = new BlackJack();
            game.setPlayers(players);
            game.setDealer(dealer);
            origOut = System.out;
            myOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(myOut));
        }

        @After
        public void tearDown() {
            System.setOut(origOut);
            if (myOut.toString().length() > 1) {
                System.out.println(System.lineSeparator() + "the System.out.print was :" + System.lineSeparator()
                        + myOut.toString());
            }
        }

        @Test
        public void testExampleAssignment() {
            // lost 50
            HandFactory.addHand(players.get(0), 50, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // win 100 Blackjack
            HandFactory.addHand(players.get(0), 100, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // win 10
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.DIAMONDS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // lost 20
            HandFactory.addHand(players.get(0), 20, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // lost 30
            HandFactory.addHand(players.get(0), 30, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            game.printPlayerHighestGain();
            assertTrue("Bot1 should have total gain of 60.0 chips",
                    myOut.toString().contains("The player with the highest gain is: Bot1 with 60.0 chips"));
        }

        @Test
        public void testPositiveGain() {
            // lost 50
            HandFactory.addHand(players.get(0), 50, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // win 100
            HandFactory.addHand(players.get(0), 100, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // win 10
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.DIAMONDS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // lost 20
            HandFactory.addHand(players.get(0), 20, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // lost 30
            HandFactory.addHand(players.get(0), 30, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(1), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            game.printPlayerHighestGain();
            assertTrue("Bot1 should have total gain of 10.0 chips",
                    myOut.toString().contains("The player with the highest gain is: Bot1 with 10.0 chips"));
        }

        @Test
        public void testTwoSameTopGain() {
            // win 100
            HandFactory.addHand(players.get(0), 100, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            HandFactory.addHand(players.get(1), 100, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            // win 10
            HandFactory.addHand(players.get(0), 10, new Card(Card.Rank.ACE, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.DIAMONDS));
            HandFactory.addHand(players.get(1), 10, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(players.get(2), 1, new Card(Card.Rank.EIGHT, Card.Suit.CLUBS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS), new Card(Card.Rank.TEN, Card.Suit.HEARTS));
            HandFactory.addHand(dealer, 10, new Card(Card.Rank.TEN, Card.Suit.DIAMONDS),
                    new Card(Card.Rank.TEN, Card.Suit.CLUBS));
            game.checkWinner();

            game.printPlayerHighestGain();
            assertTrue("Bot1 or Bot2 should have the highest gain",
                    myOut.toString().contains("The player with the highest gain is: Bot1 with 110.0 chips")
                            || myOut.toString().contains("The player with the highest gain is: Bot2 with 110.0 chips"));
        }

    }

    static class HandFactory {

        public static void addHand(Participant player, int bet, Card... cards) {
            player.createNewHand(bet);
            for (Card card : cards) {
                player.getCurrentHand().addCard(card);
            }
        }
    }

    public static class YourTests {

        @Test
        public void test1() {
            assertTrue(true);
        }

    }

}