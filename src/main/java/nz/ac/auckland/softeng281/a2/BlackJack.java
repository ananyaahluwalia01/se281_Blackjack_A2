package nz.ac.auckland.softeng281.a2;

import java.util.ArrayList;
import java.util.List;

/**
 * you should change this class for TASK 1, 2, 3, 4.
 */
public class BlackJack {

	private List<Participant> players;
	private Participant dealer;

	public BlackJack() {
		players = new ArrayList<>();
		dealer = new BotDealer("Dealer", players); // FIXME Task 2 - Failure 1, created dealer instance
		players.add(new HumanPlayer("Player1"));
		// ADDHERE Task 1 - Failure 1, Created 2 test bots in players array list.
		players.add(new BotPlayer("Bot1"));
		players.add(new BotPlayer("Bot2"));
	}

	// getter setter for testing purposes
	public List<Participant> getPlayers() {
		return players;
	}

	public Participant getDealer() {
		return dealer;
	}

	public void setPlayers(List<Participant> players) {
		this.players = players;
	}

	public void setDealer(Participant dealer) {
		this.dealer = dealer;
	}

	public static void main(String[] args) {
		BlackJack game = new BlackJack();
		game.start();
	}

	protected void start() {
		Utils.printBlackJack();
		// create a new deck of cards
		Deck deck = new Deck();
		String result;
		do {
			for (Participant player : players) {
				player.play(deck);
			}
			// ADDHERE Task 2 - Failure 1, made dealer play after players and before winner check.
			dealer.play(deck);
			checkWinner();
			System.out.println("Do you want to play again?");
			result = Utils.scanner.next();
			while (!result.equals("yes") && !result.equals("no")) {
				System.out.println("please type either \"yes\" or \"no\"");
				result = Utils.scanner.next();
			}
		} while (result.equals("yes"));
		printPlayerHighestGain();
	}

	public void checkWinner() {
		// TODO Task 3
		// Creates an array of the total scores each player has
		int[] scores = new int[players.size()];
		for (int i=0; i < players.size(); i++) {
			scores[i] = players.get(i).getCurrentHand().getScore();
		}
		// Finds total score of dealer
		int d = dealer.getCurrentHand().getScore();
		
		// Compares dealer's and players' hands
		int i=0;
		for (Participant player : players) { // KEEPTHIS
			// ADDHERE

			if ((scores[i] > d) && (scores[i] <= 21)) {
				System.out.println(player.getName() + " wins"); // UNCOMMENT AND KEEPTHIS
			} else if ((d > 21) && (scores[i] <= 21)) {
				System.out.println(player.getName() + " wins"); // UNCOMMENT AND KEEPTHIS
			} else if ((scores[i] == 21) && (players.get(i).getCurrentHand().getCards().size() == 2)) {
				System.out.println(player.getName() + " wins"); // UNCOMMENT AND KEEPTHIS
			}
			i++;
		}
	}

	public void printPlayerHighestGain() {
		// TODO Task 4
		
		int gamesPlayed = players.get(0).getHands().size();
		
		// Create an array where each row is a different player and each column is a different game.
		int[][] scores = new int[gamesPlayed][players.size()];
		int[] dealerScores = new int[gamesPlayed];
		
		for (int counterGame = 0; counterGame < gamesPlayed; counterGame++) {
			dealerScores[counterGame] = dealer.getHands().get(counterGame).getScore();
		}
		
		int counterPlayer = 0;
		double[] balance = new double[players.size()]; 
		
		for (Participant player : players) {
		
			for (int counterGame = 0; counterGame < gamesPlayed; counterGame++) {
				
				int scoreForThatGame = players.get(counterPlayer).getHands().get(counterGame).getScore();
				scores[counterGame][counterPlayer] = scoreForThatGame;
				
				System.out.println("this player's " + counterPlayer + " score for game " + counterGame + " is " + scores[counterGame][counterPlayer]);	
				
				double betForGame = players.get(counterPlayer).getHands().get(counterGame).getBet();
				
				if (((scores[counterGame][counterPlayer] > dealerScores[counterGame]) && (scores[counterGame][counterPlayer] <= 21)) || ((dealerScores[counterGame] > 21) && (scores[counterGame][counterPlayer] <= 21))) {
					balance[counterPlayer] = balance[counterPlayer] + betForGame;
				} else if ((scores[counterGame][counterPlayer] == 21) && (players.get(counterPlayer).getHands().get(counterGame).getCards().size() == 2)) {
					balance[counterPlayer] = balance[counterPlayer] + betForGame * 1.5; 
				} else {
					balance[counterPlayer] = balance[counterPlayer] - betForGame;
				}
			}
			counterPlayer++;
		}
		
		int highestGain = 0;
		for (counterPlayer = 0; counterPlayer < players.size(); counterPlayer++) {
			if (balance[counterPlayer] > balance[highestGain]) {
				highestGain = counterPlayer;
			}
		}
		
		
		String name = players.get(highestGain).getName();
		double totalGain = balance[highestGain]; 
		
		System.out.println("The player with the highest gain is: " + name + " with "
		 + totalGain + " chips"); // UNCOMMENT AND KEEPTHIS
	}
} 
