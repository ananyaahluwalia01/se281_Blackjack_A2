package nz.ac.auckland.softeng281.a2;

import java.util.List;
import java.util.Random;

/**
 * you should change this class for TASK 2
 */
public class BotDealer extends Participant {

	private List<Participant> players;

	public BotDealer(String name, List<Participant> players) {
		super(name);
		// ADDHERE Task 2 - So that we can access players array.
		this.players = players;
	}

	@Override
	public Action decideAction() {
		// TODO Task 2 - Wrote decideAction method
		
		// Creates an array of the total scores each player has
		int[] scores = new int[players.size()];
		for (int i=0; i < players.size(); i++) {
			scores[i] = players.get(i).getCurrentHand().getScore();
		}
		
		// Finds the dealers current score.
		int d = getCurrentHand().getScore();
		
		// Checks how many players have a better hand than the dealer
		int playersBetter = 0;
		
		for (int j = 0; j < players.size(); j++){
			if((d < scores[j]) && (scores[j] <= 21) || (d > 21)) {
				playersBetter++;
			} 
		}
   
		// If 2 or more players have a better hand than the dealer, then the dealer hits, else they hold.
		if (playersBetter >= 2) {
			return Action.HIT;
		} else {
			return Action.HOLD;
		}

	}

	@Override
	/**
	 * do not touch this method
	 */
	public int makeABet() {
		// the Dealer doesn't bet so is always zero
		return 0;
	}
}