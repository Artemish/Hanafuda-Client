package hanafuda.client;

public class Board {
	
	static Card[] field;
	
	static void initialize() {
		field = new Card[16];
		for (int i = 0; i < 16; i++) field[i] = Card.Null;
	}
	
	/**
	 * @param index : The position on the field to place the card c. 
	 * @param c : The Card type for the placed card.
	 */
	
	static void updateCard(int index, Card c) {
		field[index] = c;
	}
}
