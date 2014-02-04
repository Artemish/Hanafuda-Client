package hanafuda.client;

public class Deck {
	static int remaining;
	static Card topCard;
	
	static void initialize() {
		remaining = 48;
		topCard = Card.FaceDown;
	}
	
	/**
	 * Shows the supplied card's image face up.
	 * @param c : The top card of the deck.
	 */
	
	static void reveal(Card c) {
		topCard = c;
		//TODO Add rendering support for Deck button
	}
	
	static void conceal() {
		remaining--;
		topCard = Card.Null;
	}
	
	
}
