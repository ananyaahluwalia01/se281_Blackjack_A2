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
		this.players = players;
		// ADDHERE
	}

	@Override
	public Action decideAction() {
		// TODO
		
		int[] scores = new int[players.size() - 1];

		for (int i=0; i < players.size()-1; i++) {
			scores[i] = players.get(i).getCurrentHand().getScore();
		}
		
		int d = getCurrentHand().getScore();
		
		int playersBetter = 0;
		
		for (int j = 0; j < players.size()-1; j++){
			if(d < scores[j]) {
				playersBetter++;
			}
		}
   
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