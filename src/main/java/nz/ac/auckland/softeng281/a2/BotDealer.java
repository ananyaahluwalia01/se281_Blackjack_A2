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
		// ADDHERE
	}

	@Override
	public Action decideAction() {
		// TODO
		return new Random().nextBoolean() ? Action.HIT : Action.HOLD; // FIXME

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
