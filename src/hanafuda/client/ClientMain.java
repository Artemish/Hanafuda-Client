package hanafuda.client;

public class ClientMain {
		
	static Player user, opponent;
	static boolean ourTurn;
	static boolean gameOver;
	static boolean connected;
	static Card handSelection, boardSelection;
	
	static Thread inputThread;
	
	public static void main(String[] args) {
		beginGame();
	}
	
	static void beginGame() {
		SpriteSheet.initialize();
		Card.initialize();
		Deck.initialize();
		Board.initialize();
		Logger.initialize("logs/");
		
		user = new Player();
		opponent = new Player();
		handSelection = boardSelection = Card.Null;
		
		ClientGUI.initialize();
		ClientGUI.updateStatus("GUI initialization complete.");
		
		inputThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!gameOver) {
					ClientSocketLayer.getAndProcessMessage();
				}
			}
		});
		
	}
	
	/**
	 * Updates the user's hand or board selection. Attempts to send a selection-message
	 * through the socket connection if both a selection from the hand and board have been made.
	 * 
	 * @param selection : The Card type held by the button updating the selection.
	 * @param isBoard : Used to determine whether the selection is from the hand or board.
	 */
	
	static void updateSelection(Card selection, boolean isBoard) {
		if (ourTurn) {
			if (isBoard) {
				boardSelection = selection;
			} else if (selection != Card.Null) {
				handSelection = selection;
			}
			
			if (boardSelection != Card.Null && handSelection != Card.Null) {
				ClientSocketLayer.sendSelection(handSelection, boardSelection);
				handSelection = boardSelection = Card.Null;
			}
		}
	}
	
	static void endGame() {
		//TODO add end-game logic
	}
	
}
