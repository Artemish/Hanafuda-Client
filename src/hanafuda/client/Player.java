package hanafuda.client;

import java.util.ArrayList;

public class Player {
	
	int score;
	int playerID;
	Card[] hand = new Card[8];
	ArrayList<Card> sideBoard;
	
	/**
	 * Generates a new Player. Though not strictly enforced, there
	 * should only be two players, user and opponent in ClientMain,
	 * at any given time. 
	 */
	
	Player() {
		score = 0;
		for (int i = 0; i < 8; i++) hand[i] = Card.FaceDown;
		sideBoard = new ArrayList<Card>();
	}
	
	
	/**
	 * Updates the GUI with an empty slot for the card at the given index.
	 * @param index : The index of the removed card.
	 */
	
	void removeCard(int index) {
		hand[index] = Card.Null;
	}
	
	void setScore(int score) {
		this.score = score;
	}
	
}
