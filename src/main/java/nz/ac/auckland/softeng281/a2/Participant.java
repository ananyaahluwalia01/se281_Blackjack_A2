package nz.ac.auckland.softeng281.a2;

import java.util.ArrayList;
import java.util.List;

/**
 * you cannot modify existing methods
 * but you can add new methods/fields
 */
public abstract class Participant {

    enum Action {
        HIT, HOLD
    }

    private String name;
    private List<Hand> hands;


    public Participant(String name) {
        this.name = name;
        hands = new ArrayList<>();
    }

    public List<Hand> getHands() {
        return hands;
    }


    public Hand getCurrentHand() {
        if (hands.isEmpty()) {
            throw new RuntimeException("You should't call this method if there are no hands");
        }

        return hands.get(hands.size() - 1);
    }

    public String getName() {
        return name;
    }


    public Hand createNewHand(int bet) {
        Hand newHand = new Hand(bet);
        hands.add(newHand);
        return newHand;
    }

    /**
     * do not change this method
     *
     * @param deck
     */
    public void play(Deck deck) {
        System.out.println("==================");
        System.out.println(name + " is playing");
        int bet = makeABet();
        Hand hand = createNewHand(bet);
        hand.addCard(deck.draw());
        hand.addCard(deck.draw());
        System.out.println("the initial two cards are");
        hand.print();
        System.out.println(name + "'s score is: " + hand.getScore());
        if (hand.countAces() > 0 && this instanceof HumanPlayer) {
            System.out.println("Remember that an ACE can have rank either 1 or 11");
        }
        Participant.Action decision = decideAction();
        while (decision == Participant.Action.HIT) {
            hand.addCard(deck.draw());
            hand.print();
            System.out.println(name + "'s score is: " + hand.getScore());
            if (hand.is21() || hand.isBust()) {
                break;
            }
            decision = decideAction();
        }
        pressEnterKeyToContinue();
    }

    private void pressEnterKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        Utils.scanner.nextLine();
    }

    abstract Action decideAction();

    abstract int makeABet();
}
