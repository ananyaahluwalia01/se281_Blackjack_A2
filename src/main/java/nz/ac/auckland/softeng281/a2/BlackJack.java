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
		dealer = null; // FIXME Task 2
		players.add(new HumanPlayer("Player1"));
		// ADDHERE Task 1 - Task 1 Failure 1, Created 2 Test Bots in players array list.
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
			// ADDHERE Task 2
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
		for (Participant player : players) { // KEEPTHIS
			// ADDHERE
			// System.out.println(player.getName() + " wins"); // UNCOMMENT AND KEEPTHIS
		}
	}

	public void printPlayerHighestGain() {
		// TODO Task 4
		// System.out.println("The player with the highest gain is: " + name + " with "
		// + totalGain + " chips"); // UNCOMMENT AND KEEPTHIS
	}
}
