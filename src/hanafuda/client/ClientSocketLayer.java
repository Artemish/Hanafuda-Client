package hanafuda.client;

import java.util.Timer;
import java.util.TimerTask;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class ClientSocketLayer {
	
	static boolean connected = false;
	static boolean busy = false;
	
	static final int MAX_ATTEMPTS = 5;
	static final int RETRY_DELAY = 2000;
	static final int PORT = 5533;
	static final String HOST = "127.0.0.1";
	
	static final byte GAME_OVER = -1;
	static final byte FULL_STATE_UPDATE_ID = 1;
	static final byte SCORE_UPDATE_ID = 2;
	static final byte HAND_UPDATE_ID = 3;
	static final byte SELECTION_MESSAGE_ID = 4;
	static final byte STRING_MESSAGE_ID = 5;
	
	static final int FULL_STATE_UPDATE_LENGTH = 32;
	
	private static Socket clientSocket = null;
	private static InputStream input = null;
	private static OutputStream output = null;
	
	/**
	 * Attempts to make a connection with the host server,
	 * whose location is specified by the HOST:PORT variables.
	 * Attempts the connection MAX_ATTEMPTS times, waiting
	 * RETRY_DELAY ms after every failed attempt. Writes 
	 * successes and failures to the status box.
	 */
	
	static synchronized void connect () {
		Timer connector = new Timer("Attempting connection.");
		if (!busy) connector.schedule(new TimerTask() {
			int attempts = 1;
			@Override
			public void run() {
				if (attempts < MAX_ATTEMPTS) { 
					try {
						clientSocket = new Socket(HOST, PORT);
						connected = true;
						input = clientSocket.getInputStream();
						output = clientSocket.getOutputStream();
						ClientMain.inputThread.start();
						this.cancel();
						ClientGUI.updateStatus("Client successfully connected!");
						busy = false;
					} catch (IOException e) {
						ClientGUI.updateStatus("Attempt " + attempts++ + " failed, retrying..");
					}
				} else {
					ClientGUI.updateStatus("Could not connect to host.");
					this.cancel();
					busy = false;
				}
			}
		}, 0, RETRY_DELAY);
		
		busy = true;
	}
	
	/**
	 * Attempts to write the given byte array to the socket's output stream.
	 * Returns a status code so the calling function may know the result of the
	 * write. Error handling is intended to be done by the calling function.
	 * 
	 * @param message : The byte array to be sent through the socket connection.
	 * @return : A status code for the result of the write. True on success, false on failure.
	 */
	static synchronized boolean safeWrite(byte[] message) {
		if (connected) {
			try {
				output.write(message);
				return true;
			}
			catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else {
			System.out.println("Connection not established.");
			return false;
		}
	}
	
	static void retryConnection() {
		throw new UnsupportedOperationException();
		//TODO implement fault-tolerance for connection
	}
	
	/**
	 * Called by updateSelection in ClientMain when cards are chosen
	 * from both the field and the hand. Writes a failure message to
	 * the client's status box upon failure.
	 * 
	 * @param handSelection : The Card type of the chosen hand card.
	 * @param boardSelection : The Card type of the chosen board card.
	 */
	
	static void sendSelection(Card handSelection, Card boardSelection) {
		byte[] message = {SELECTION_MESSAGE_ID, handSelection.cardID, boardSelection.cardID};
		if (!safeWrite(message)) {
			ClientGUI.updateStatus("Failed to send card selection.");
		} else {
			ClientGUI.updateStatus("Sent le selection");
		}
		
	}
	
	/**
	 * Reads the first byte from the input stream, determines the form
	 * and intent of the following message, grabs the appropriate bytes,
	 * and executes the instruction. The message IDs are given by the
	 * final static byte identifiers above.
	 */
	static synchronized void getAndProcessMessage() {
		byte command = -128;
		try {
			command = (byte) input.read();
		} catch (IOException e) {
			System.out.println("An IOException?!");
		}
		
		//TODO add end-of-stream detection
		
		switch (command) {
		case GAME_OVER: ClientMain.endGame();
		case FULL_STATE_UPDATE_ID: enactFullUpdate(); 
		case SCORE_UPDATE_ID: enactScoreUpdate();
		case HAND_UPDATE_ID: enactHandUpdate();
		default: return;
		}
		
	}
	
	/**
	 * Given the knowledge that the bytes in the inputstream that follow 
	 * represent the full game state, grab the message and set the appropriate
	 * variables for this game.
	 */
	
	static void enactFullUpdate() {
		ClientGUI.updateStatus("Enacting Full Update.");
		byte[] state = new byte[FULL_STATE_UPDATE_LENGTH];
		byte[] userSideBoard = null, opponentSideBoard = null;
		
		try {
			input.read(state, 0, FULL_STATE_UPDATE_LENGTH);
			int userSideLen = state[0];
			int opponentSideLen = state[1];
			if (userSideLen > 0) {
				userSideBoard = new byte[userSideLen];
				input.read(userSideBoard, 0, userSideLen);
			}
			if (opponentSideLen > 0) {
				opponentSideBoard = new byte[opponentSideLen];
				input.read(opponentSideBoard, 0, opponentSideLen);
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		ClientGUI.updateStatus("Message recieved.");
		
		ClientMain.ourTurn = (state[2] == 1) ? true : false;
		ClientMain.user.score = state[3];
		ClientMain.opponent.score = state[4];
		ClientMain.gameOver = (state[5] == 1) ? true : false;
		
		for (int i = 6; i < 14; i++) {
			ClientMain.user.hand[i-6] = Card.getByID(state[i]);
			ClientMain.opponent.hand[i-6] = Card.getByID(state[i+8]);
			Board.field[i-6] = Card.getByID(state[i+16]);
		}
		
		ClientMain.user.sideBoard.clear();
		ClientMain.opponent.sideBoard.clear();
		
		if (userSideBoard != null) {
			for (byte b : userSideBoard) ClientMain.user.sideBoard.add(
					Card.getByID(b));
		}
		
		if (opponentSideBoard != null) {
			for (byte b : opponentSideBoard) ClientMain.user.sideBoard.add(
					Card.getByID(b));
		}
				
		ClientGUI.updateCards();
	}
	
	static void enactScoreUpdate() {
		
		
	}
	
	static void enactHandUpdate() {
		
		
	}
	
}
