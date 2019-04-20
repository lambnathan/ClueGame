package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private String selectedRoomName;
	private JComboBox personBox;
	private JComboBox weaponBox;
	
	public SuggestionWindow(String roomName) {
		setTitle("Make a Suggestion");
		setSize(250, 200);
		this.selectedRoomName = roomName;
		
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
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.addItem(selectedRoomName);
		comboBox.setEnabled(false);
		comboBox.setEditable(false);
		panel.add(comboBox);
		return panel;
	}
	
	public JPanel createPersonComboBox() {
		JPanel panel = new JPanel();
		personBox = new JComboBox<String>();
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
					String personSuggestion = personBox.getSelectedItem().toString();
					String weaponSuggestion = weaponBox.getSelectedItem().toString();
					System.out.println(personSuggestion + " " + weaponSuggestion);
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
