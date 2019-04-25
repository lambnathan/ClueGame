package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SuggestionWindow extends JFrame{
	JPanel roomLabel, personLabel, weaponLabel;
	JPanel roomComboBox, personComboBox, weaponComboBox;
	JPanel suggestButtonPanel, cancelButtonPanel;
	
	private guessDialog dialog;
	private Board board;
	private String selectedRoomName;
	private String accuserName;
	private JComboBox<String> personBox;
	private JComboBox<String> weaponBox;
	private JComboBox<String> roomBox;
	private boolean isAccusation = false;
	
	public SuggestionWindow(String roomName, String personName) {
		setTitle("Suggestion");
		setSize(250, 200);
		this.selectedRoomName = roomName;
		this.accuserName = personName;
		board = Board.getInstance();
		dialog = new guessDialog();
	}
	
	//only gets called when the player clicks to make accusation
	public SuggestionWindow(boolean isAccusation) {
		this.isAccusation = isAccusation;
		setTitle("Accuse");
		setSize(250, 250);
		board = board.getInstance();
		dialog = new guessDialog();
	}
	
	public class guessDialog extends JDialog {
		public guessDialog() {
			createLayout();
		}
	}
	
	public void createLayout() {
		roomLabel = createRoomLabel();
		personLabel = createPersonLabel();
		weaponLabel = createWeaponLabel();
		
		roomComboBox = createRoomComboBox();
		personComboBox = createPersonComboBox();
		weaponComboBox = createWeaponComboBox();
		
		suggestButtonPanel = createSuggestButtonPanel();
		cancelButtonPanel = createCancelButtonPanel();
		
		JPanel completed = new JPanel();
		completed.setLayout(new GridLayout(4, 2));
		add(completed, BorderLayout.CENTER);
		completed.add(roomLabel);
		completed.add(roomComboBox);
		completed.add(personLabel);
		completed.add(personComboBox);
		completed.add(weaponLabel);
		completed.add(weaponComboBox);
		completed.add(suggestButtonPanel);
		completed.add(cancelButtonPanel);
	}
	
	public JPanel createRoomLabel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Room:"));
		return panel;
	}
	
	public JPanel createPersonLabel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Person:"));
		return panel;
	}
	
	public JPanel createWeaponLabel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Weapon:"));
		return panel;
	}
	
	public JPanel createRoomComboBox() {
		JPanel panel = new JPanel();
		roomBox = new JComboBox<String>();
		roomBox.setPrototypeDisplayValue("Colonel Mustard");
		if(isAccusation) {
			roomBox.addItem("Conservatory");
			roomBox.addItem("Kitchen");
			roomBox.addItem("Ballroom");
			roomBox.addItem("Library");
			roomBox.addItem("Arcade room");
			roomBox.addItem("Gun room");
			roomBox.addItem("Trophy room");
			roomBox.addItem("Pantry");
			roomBox.addItem("Sauna");	
		}
		else {
			roomBox.addItem(selectedRoomName);
		}
		roomBox.setEditable(false);
		roomBox.setEnabled(isAccusation);
		panel.add(roomBox);
		return panel;
	}
	
	public JPanel createPersonComboBox() {
		JPanel panel = new JPanel();
		personBox = new JComboBox<String>();
		personBox.setPrototypeDisplayValue("Colonel Mustard");
		personBox.addItem("Miss Scarlet");
		personBox.addItem("Colonel Mustard");
		personBox.addItem("Mr. Green");
		personBox.addItem("Mrs. White");
		personBox.addItem("Mrs. Peacock");
		personBox.addItem("Professor Plum");
		panel.add(personBox);
		return panel;
	}
	
	public JPanel createWeaponComboBox() {
		JPanel panel = new JPanel();
		weaponBox = new JComboBox<String>();
		weaponBox.setPrototypeDisplayValue("Colonel Mustard");
		weaponBox.addItem("Rope");
		weaponBox.addItem("Lead Pipe");
		weaponBox.addItem("Knife");
		weaponBox.addItem("Wrench");
		weaponBox.addItem("Candlestick");
		weaponBox.addItem("Revolver");	
		panel.add(weaponBox);
		return panel;
	}
	
	public JPanel createSuggestButtonPanel() {
		JPanel panel = new JPanel();
		JButton makeSuggestion = null;
		if(isAccusation) {
			makeSuggestion = new JButton("Make accusation");
		}
		else {
			makeSuggestion = new JButton("Make suggestion");
		}
		makeSuggestion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					//board.repaint();
					String roomSuggestion = roomBox.getSelectedItem().toString();
					String personSuggestion = personBox.getSelectedItem().toString();
					String weaponSuggestion = weaponBox.getSelectedItem().toString();
					Card personSuggestionCard = null;
					Card weaponSuggestionCard = null;
					Card roomSuggestionCard = null;
					Player temp2 = null;
					//creating cards that match what the player suggested
					//first searching in the deck of cards (does not include solution cards)
					//accusation
					if(isAccusation) {
						for(Card c: board.getCardList()) {
							if(c.getCardName().equals(personSuggestion)) {
								personSuggestionCard = c;
							}
							else if(c.getCardName().equals(weaponSuggestion)) {
								weaponSuggestionCard = c;
							}
							else if(c.getCardName().equals(roomSuggestion)) {
								roomSuggestionCard = c;
							}
						}
						for(Card c: board.getAnswer()) {
							if(c.getCardName().equals(personSuggestion)) {
								personSuggestionCard = c;
							}
							else if(c.getCardName().equals(weaponSuggestion)) {
								weaponSuggestionCard = c;
							}
							else if(c.getCardName().equals(roomSuggestion)) {
								roomSuggestionCard = c;
							}
						}
					}
					//if not an accusation create suggestion
					else {
						for(Card c: board.getCardList()) {
							if(c.getCardName().equals(personSuggestion)) {
								personSuggestionCard = c;
							}
							else if(c.getCardName().equals(weaponSuggestion)) {
								weaponSuggestionCard = c;
							}
							else if(c.getCardName().equals(selectedRoomName)) {
								roomSuggestionCard = c;
							}
						}
						for(Card c: board.getAnswer()) {
							if(c.getCardName().equals(personSuggestion)) {
								personSuggestionCard = c;
							}
							else if(c.getCardName().equals(weaponSuggestion)) {
								weaponSuggestionCard = c;
							}
							else if(c.getCardName().equals(selectedRoomName)) {
								roomSuggestionCard = c;
							}
						}
					}
					//setting the player(our current makeMove() function changes the currentplayer before we can access them)
					for(Player p: board.getPlayerList()) {
						if(p.getPlayerName().equals(accuserName)) {
							temp2 = p;
						}
					}
					Solution suggestion = new Solution(personSuggestionCard, roomSuggestionCard, weaponSuggestionCard);
					//if an accusation, check against the answer
					if(isAccusation) { //if the accusation is correct, inform player and close game
						boolean isCorrect = board.checkAccusation(suggestion);						
							if(isCorrect) {
								JOptionPane.showMessageDialog(null, "You did it! You have won the game, now closing.", "Winner", JOptionPane.INFORMATION_MESSAGE);
								dispose();
								ClueGame.exitGame();
							}
							else { //if the accusation is incorrect, inform player and continue game without them
								JOptionPane.showMessageDialog(null, "You are incorrect! You can no longer help in this game, it will continue without you.", "You lost!", JOptionPane.INFORMATION_MESSAGE);
								board.setPlayerHasLost(true);
								dispose();
								//stops player from being able to move after failing an accusation. Automatically goes to next player(Colonel Mustard)
								board.makeMove();
							}											
//----------------------------------------------------------------------------------------------------------------------------------------------------	
//						//ONLY FOR TESTING TO SEE IF WORKING CORRECTLY
//						Set<Card> answer = board.getAnswer();
//						for(Card c: answer) {
//							System.out.println("Current answer: " + c.getCardName());
//						}							
//						//if the player is correct let them know and end game, otherwise they are done playing
//						if(isCorrect) {
//							System.out.println("You did it!");
//						}
//						else {
//							System.out.println("Wrong.");
//						}
//----------------------------------------------------------------------------------------------------------------------------------------------------					
												
					}

					else if(!isAccusation) {
						Card disproveCard = board.handleSuggestion(suggestion, temp2);
						//check if there are no cards that can disprove the suggestion
						if(disproveCard == null) {
							ControlGUI.showResponse("Cannot disprove");
						}
						else {
							String disproveCardName = disproveCard.getCardName();
							ControlGUI.showResponse(disproveCardName);
						}
						String guess = personSuggestion + " in the " + selectedRoomName + " with the " + weaponSuggestion;
						ControlGUI.showGuess(guess);
						dispose();
					}
				}
			}
		});
		panel.add(makeSuggestion);
		return panel;
	}

	public JPanel createCancelButtonPanel() {
		JPanel panel = new JPanel();
		JButton makeCancel = new JButton("Cancel");
		makeCancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					dispose();
				}
			}
		});
		panel.add(makeCancel);
		return panel;
	}
}