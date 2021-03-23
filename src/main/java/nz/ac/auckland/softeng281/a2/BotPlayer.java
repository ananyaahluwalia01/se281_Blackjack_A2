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
		// TODO
		return new Random().nextBoolean() ? Action.HIT : Action.HOLD; // FIXME
	}

	@Override
	public int makeABet() {
		// TODO
		return -1; // FIXME
	}
}
