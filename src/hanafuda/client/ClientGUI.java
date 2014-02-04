package hanafuda.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ClientGUI {
	
	static final String TITLE = "Hanafuda v1.0a";
	
	static JTextArea statusText;
	static JFrame guiFrame; 
	static JPanel displayPanel;
	static JPanel optionsPanel;
	
	static JLabel opponentScore;
	static JLabel userScore;
	
	static CardButton[] userCards, opponentCards, boardCards;
	
	/**
	 * Sets up the GUI for the client and adds the necessary buttons.
	 */
	
	static void initialize() {
		guiFrame = new JFrame();
		displayPanel = new JPanel();
		displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
		optionsPanel = new JPanel(new BorderLayout());
		
		JButton connectButton = new JButton("Initialize connection");
		connectButton.setPreferredSize(new Dimension(100, 60));
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!ClientSocketLayer.connected) {
					ClientSocketLayer.connect();
				} else {
					
				}
			}
		});
		
		statusText = new JTextArea();
		JScrollPane statusScroll = new JScrollPane(statusText);
		statusScroll.setPreferredSize(new Dimension(210, 400));
		
		
		optionsPanel.add(connectButton, BorderLayout.PAGE_START);
		optionsPanel.add(statusScroll, BorderLayout.PAGE_END);
		
		
		optionsPanel.setSize(200, 450);
		displayPanel.setSize(560, 450);
		
		initializeCards();
		
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Hanafuda!");
        
        guiFrame.setLocationRelativeTo(null);
        
        guiFrame.add(optionsPanel, BorderLayout.LINE_START);
        guiFrame.add(displayPanel, BorderLayout.LINE_END);
        
        guiFrame.pack();
        guiFrame.setVisible(true);
	}
	
	static void initializeCards() {
		userCards = new CardButton[8];
		opponentCards = new CardButton[8];
		boardCards = new CardButton[16];
		
		JPanel opponentPane = new JPanel();
		JPanel upperBoardPane = new JPanel();
		JPanel lowerBoardPane = new JPanel();
		JPanel userPane = new JPanel();
		
		userScore = new JLabel("0!");
		opponentScore = new JLabel("0!");
		opponentPane.add(opponentScore);
		CardButton button;
		for (int i = 0; i < 8; i++) {
			button = new CardButton(ClientMain.user.hand[i], i, true, false);
	        userCards[i] = button;
			userPane.add(button);
			button = new CardButton(ClientMain.opponent.hand[i], i, false, false);
			opponentCards[i] = button;
			opponentPane.add(button);
		}
		userPane.add(userScore);
		
		for (int j = 0; j < 16; j++) {
			button = new CardButton(Board.field[j], j, true, true);
			button.setBorder(BorderFactory.createEmptyBorder());
	        button.setContentAreaFilled(false);
	        button.setFocusPainted(false);
	        boardCards[j] = button;
			if (j < 8) upperBoardPane.add(button);
			else lowerBoardPane.add(button);
		}
		
		displayPanel.add(opponentPane);
		displayPanel.add(upperBoardPane);
		displayPanel.add(lowerBoardPane);
		displayPanel.add(userPane);
		
	}
	
	/**
	 * @param message : the message to append to the display and log.
	 */
	
	static void updateStatus(String message) {
		statusText.append(message + "\n");
		Logger.log(message);
	}
	
	/**
	 * Refreshes the user's display of the card buttons.
	 */
	
	static void updateCards() {
		for (int i = 0; i < 8; i++) {
			userCards[i].updateCard(ClientMain.user.hand[i]);
			opponentCards[i].updateCard(ClientMain.opponent.hand[i]);
		}
		
		for (int j = 0; j < 16; j++) {
			boardCards[j].updateCard(Board.field[j]);
		}
	}
	
	
	static void updateSelection() {
		
	}
	
}

@SuppressWarnings("serial")
class CardButton extends JButton implements ActionListener {
	
	final int cardIndex;
	final boolean clickable, isBoard;
	Card current;
	
	public CardButton(Card c, int index, boolean clickable, boolean isBoard) {
		super(c.cardImage);
		cardIndex = index;
		current = c;
		this.clickable = clickable;
		this.isBoard = isBoard;
		this.addActionListener(this);
		setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setFocusPainted(false);
	}
	
	public void updateCard(Card c) {
		current = c;
		this.setIcon(c.cardImage);
		this.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (clickable) {
			ClientMain.updateSelection(current, isBoard);
		}
		
	}
	
	
}

@SuppressWarnings("serial")
class connectButton extends JButton {
	
	
}