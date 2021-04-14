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
		// TODO Task 1 - Failures 2 to 6, Wrote decideAction() method
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
		// TODO Task 1 - Failures 7 and 8, wrote makeABet() method
		
		// Uses Math.Random function to find a random double between 0 and 99 
		double random = (Math.random())*100;
		
		// Converts to double to integer and adds one to get range between 1 and 100.
		int randomBet = (int)random + 1;
		
		
		return randomBet;
	}
}
