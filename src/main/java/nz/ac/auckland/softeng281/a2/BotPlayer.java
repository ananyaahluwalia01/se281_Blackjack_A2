package nz.ac.auckland.softeng281.a2;

import java.util.Random;

/**
 * you should change this class for TASK 1
 */
public class BotPlayer extends Participant {

	public BotPlayer(String name) {
		super(name);
	}

	@Override
	public Action decideAction() {
		// TODO Task 1 - Failures 2 to 5, Wrote decideAction() method
		// Retrieves current hand using getCurrentHand method and counts the total score of that hand using getScore method.
		Hand current = getCurrentHand();
		int totalScore = current.getScore();
		
		// Simple conditional statement checking whether to hold or hit.
		if (totalScore < 17) {
			return Action.HIT;
		} else {
			return Action.HOLD; 
		}
	}

	@Override
	public int makeABet() {
		// TODO
		return -1; // FIXME
	}
}
