package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

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
	
	public SuggestionWindow(String roomName, String personName) {
		
		setTitle("Suggestion");
		setSize(250, 200);
		this.selectedRoomName = roomName;
		this.accuserName = personName;
		board = Board.getInstance();
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
		JComboBox<String> roomBox = new JComboBox<String>();
		roomBox.setPrototypeDisplayValue("Colonel Mustard");
		roomBox.addItem(selectedRoomName);
		roomBox.setEnabled(false);
		roomBox.setEditable(false);
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
		JButton makeSuggestion = new JButton("Make suggestion");
		makeSuggestion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					board.repaint();
					String personSuggestion = personBox.getSelectedItem().toString();
					String weaponSuggestion = weaponBox.getSelectedItem().toString();
					Card personSuggestionCard = null;
					Card weaponSuggestionCard = null;
					Card roomSuggestionCard = null;
					Card temp = null;
					Player temp2 = null;
					//creating cards that match what the player suggested
					//first searching in the deck of cards (does not include solution cards)
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
					//setting the player(our current makeMove() function changes the currentplayer before we can access them)
					for(Player p: board.getPlayerList()) {
						if(p.getPlayerName().equals(accuserName)) {
							temp2 = p;
						}
					}
					Solution suggestion = new Solution(personSuggestionCard, roomSuggestionCard, weaponSuggestionCard);
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
