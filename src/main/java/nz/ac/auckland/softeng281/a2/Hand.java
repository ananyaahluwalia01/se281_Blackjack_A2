package nz.ac.auckland.softeng281.a2;

import java.util.ArrayList;
import java.util.List;

/**
 * you cannot modify existing methods but you can add new methods/fields
 */
public class Hand {

	private List<Card> cards;
	private int bet;
	private int score;

	public Hand(int bet) {
		cards = new ArrayList<>();
		this.bet = bet;
		score = 0;
	}

	public int getScore() {
		return score;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void addCard(Card c) {
		cards.add(c);
		// every time I add a card I update the score
		score = score + c.getRank().value;
		checkIfAcesShouldBeOne();
	}

	public int countAces() {
		int count = 0;
		for (Card c : cards) {
			if (c.getRank() == Card.Rank.ACE) {
				count++;
			}
		}
		return count;
	}

	private void checkIfAcesShouldBeOne() {
		if (score > 21) {
			int numAces = countAces();
			// see if there are opportunities to make ACES to have score 1
			if (numAces > 0) {
				score = 0;
				// we need to recount from scratch because some ACEs might be counted as 11
				for (Card card : cards) {
					score = score + card.getRank().value;
				}
				while (score > 21 && numAces > 0) {
					// ACES can have score 11 or 1
					score = score - 10;
					numAces--;
				}
			}
		}
		// note that the score wll be update after the method ends
	}

	public boolean isBust() {
		if (getScore() > 21) {
			System.out.println("Bust! :( ");
			return true;
		}
		return false;
	}

	public boolean isBlackJack() {
		return score == 21 && cards.size() == 2;
	}

	public boolean is21() {
		if (isBlackJack()) {
			System.out.println("Black Jack! :D ");
			return true;
		} else if (score == 21) {
			System.out.println("21! :) ");
			return true;
		}
		return false;
	}

	private void addNumberWhiteSpace(StringBuilder sb, int num) {
		for (int i = 0; i < num; i++) {
			sb.append(" ");
		}
	}

	public void print() {
		List<StringBuilder> listSb = new ArrayList<>();
		// + 1 because we also want to print the name of the card
		for (int i = 0; i < Card.template[0].length + 1; i++) {
			listSb.add(new StringBuilder());
		}
		for (Card card : cards) {
			String textCard = card.getRank() + " " + card.getSuit();
			int numWhiteSpaces = (((textCard.length() - 7)) / 2) + 1;
			listSb.get(0).append(textCard);
			listSb.get(0).append("  -  ");
			int i = 1;
			for (char[] line : card.getCardToPrint()) {
				addNumberWhiteSpace(listSb.get(i), numWhiteSpaces);
				listSb.get(i).append(line);
				addNumberWhiteSpace(listSb.get(i), "  -  ".length() + numWhiteSpaces / 2);
				i++;
			}
		}
		for (int i = 0; i < Card.template[0].length + 1; i++) {
			System.out.println(listSb.get(i).toString());
		}
	}
}
