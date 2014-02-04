package hanafuda.client;

import java.util.HashMap;

import javax.swing.ImageIcon;

public enum Card {
	
	Null (-2, -2, 1, 6),
	FaceDown (-1, -1, 0, 6),
	Pine1 (0, 0, 0, 0), 
	Pine2 (0, 1, 1, 0), 
	PineRibbon (0, 2, 2, 0), 
	PineBright (0, 3, 3, 0), 
	Plum1 (1, 4, 4, 0), 
	Plum2 (1, 5, 5, 0), 
	PlumRibbon (1, 6, 6, 0), 
	PlumAnimal (1, 7, 7, 0),  
	Sakura1 (2, 8, 0, 1), 
	Sakura2 (2, 9, 1, 1), 
	SakuraRibbon(2, 10, 2, 1), 
	SakuraBright(2, 11, 3, 1), 
	Wisteria1 (3, 12, 4, 1), 
	Wisteria2 (3, 13, 5, 1), 
	WisteriaRibbon (3, 14, 6, 1), 
	WisteriaAnimal (3, 15, 7, 1), 
	Iris1 (4, 16, 0, 2), 
	Iris2 (4, 17, 1, 2), 
	IrisRibbon (4, 18, 2, 2), 
	IrisAnimal (4, 19, 3, 2), 
	Rose1 (5, 20, 4, 2), 
	Rose2 (5, 21, 5, 2), 
	RoseRibbon (5, 22, 6, 2), 
	RoseAnimal (5, 23, 7, 2), 
	Clover1 (6, 24, 0, 3), 
	Clover2 (6, 25, 1, 3), 
	CloverRibbon (6, 26, 2, 3), 
	CloverAnimal (6, 27, 3, 3), 
	Plains1 (7, 28, 4, 3), 
	Plains2 (7, 29, 5, 3), 
	PlainsAnimal (7, 30, 6, 3), 
	PlainsBright (7, 31, 7, 3), 
	Chrys1 (8, 32, 0, 4), 
	Chrys2 (8, 33, 1, 4), 
	ChrysRibbon (8, 34, 2, 4), 
	ChrysAnimal (8, 35, 3, 4), 
	Maple1 (9, 36, 4, 4), 
	Maple2 (9, 37, 5, 4), 
	MapleRibbon (9, 38, 6, 4), 
	MapleAnimal (9, 39, 7, 4), 
	Willow1 (10, 40, 0, 5), 
	WillowRibbon (10, 41, 1, 5), 
	WillowAnimal (10, 42, 2, 5), 
	WillowBright (10, 43, 3, 5), 
	Star1 (11, 44, 4, 5), 
	Star2 (11, 45, 5, 5), 
	Star3 (11, 46, 6, 5), 
	StarBright (11, 47, 7, 5);
	
	public byte suit, cardID;
	
	public ImageIcon cardImage;
	
	/**
	 * @param suit : The suit of the card - an integer by game logic.
	 * @param cardID : A unique ID given to each card.
	 * @param x : The x position of the card in the spritesheet.
	 * @param y : The y position of the card in the spritesheet.
	 */
	
	private Card(int suit, int cardID, int x, int y) {
		this.suit = (byte) suit;
		this.cardID = (byte) cardID;
		cardImage = new ImageIcon(SpriteSheet.getCard(x, y));
	}
	
	
	/**
	 * Initializes the hashmap <cardID, Card> to allow
	 * for grabbing a card by its ID.
	 */
	
	static void initialize() {
		for (Card c : Card.values()) byID.put(c.cardID, c);
	}
	
	static HashMap<Byte, Card> byID = new HashMap<Byte, Card>();
	
	static boolean combos(Card c1, Card c2) {
		//TODO make actual combo logic.
		return (c1.suit == c2.suit) && c2 != null;
	}
	
	static Card getByID(byte id) {
		return byID.get(id);
	}
	
}
