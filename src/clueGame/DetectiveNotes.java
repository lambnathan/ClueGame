package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class DetectiveNotes extends JFrame {
	
	public JComboBox persons, weapons, rooms;
	public JPanel personCheckList, weaponCheckList, roomCheckList;
	
	private notesDialog dialog;
	
	//constructor
	public DetectiveNotes() {
		//setTitle("Detective Notes");
		setTitle("Detective Notes");
		setSize(600,750);

		dialog = new notesDialog();
	}
	
	//create popup window
	public class notesDialog extends JDialog {
		public notesDialog() {
			createLayout();
		}
	}
	
	//calls each individual panel creation, then adds them to one final panel called completed
	public void createLayout() {
		persons = createPersonGuessPanel();
		weapons = createWeaponGuessPanel();
		rooms = createRoomGuessPanel();
		
		personCheckList = createPeoplePanel();
		weaponCheckList =createWeaponsPanel(); 
		roomCheckList = createRoomsPanel();
		
		JPanel completed = new JPanel();
		completed.setLayout(new GridLayout(3, 2));
		add(completed, BorderLayout.CENTER);
		completed.add(personCheckList);
		completed.add(persons);
		completed.add(weaponCheckList);
		completed.add(weapons);
		completed.add(roomCheckList);
		completed.add(rooms);
	}
	
	//People panel that holds all character names in tick-box form
	public JPanel createPeoplePanel() {
		//setup
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		panel.add(new JCheckBox("Miss Scarlet"));
		panel.add(new JCheckBox("Colonel Mustard"));
		panel.add(new JCheckBox("Mr. Green"));
		panel.add(new JCheckBox("Mrs. White"));
		panel.add(new JCheckBox("Mrs. Peacock"));
		panel.add(new JCheckBox("Professor Plum"));
		return panel;		
	}
	
	//Person Guess panel which holds all possible names for people to guess on in drop down menu
	public JComboBox<String> createPersonGuessPanel() {
		JComboBox<String> panel = new JComboBox<String>();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		panel.addItem("Unsure");
		panel.addItem("Miss Scarlet");
		panel.addItem("Colonel Mustard");
		panel.addItem("Mr. Green");
		panel.addItem("Mrs. White");
		panel.addItem("Mrs. Peacock");
		panel.addItem("Professor Plum");
		return panel;
	}
	
	//Room panel that holds all room in tick-box form
	public JPanel createRoomsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		panel.add(new JCheckBox("Conservatory"));
		panel.add(new JCheckBox("Kitchen"));
		panel.add(new JCheckBox("Ballroom"));
		panel.add(new JCheckBox("Library"));
		panel.add(new JCheckBox("Arcade room"));
		panel.add(new JCheckBox("Gun room"));
		panel.add(new JCheckBox("Trophy room"));
		panel.add(new JCheckBox("Pantry"));
		panel.add(new JCheckBox("Sauna"));
		return panel;
	}
	
	//Room guess panel that holds all possible names for rooms to guess on in drop down menu
	public JComboBox<String> createRoomGuessPanel() {
		JComboBox<String> panel = new JComboBox<String>();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		panel.addItem("Unsure");
		panel.addItem("Conservatory");
		panel.addItem("Kitchen");
		panel.addItem("Ballroom");
		panel.addItem("Library");
		panel.addItem("Arcade room");
		panel.addItem("Gun room");
		panel.addItem("Trophy room");
		panel.addItem("Pantry");
		panel.addItem("Sauna");				
		return panel;
	}
	
	//Weapon panel that holds all weapon in tick-box form
	public JPanel createWeaponsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		panel.add(new JCheckBox("Rope"));
		panel.add(new JCheckBox("Lead Pipe"));
		panel.add(new JCheckBox("Knife"));
		panel.add(new JCheckBox("Wrench"));
		panel.add(new JCheckBox("Candlestick"));
		panel.add(new JCheckBox("Revolver"));
		return panel;
	}
	
	//Weapon guess panel that holds all possible names for weapons to guess on in drop down menu
	public JComboBox<String> createWeaponGuessPanel() {
		JComboBox<String> panel = new JComboBox<String>();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		panel.addItem("Unsure");
		panel.addItem("Rope");
		panel.addItem("Lead Pipe");
		panel.addItem("Knife");
		panel.addItem("Wrench");
		panel.addItem("Candlestick");
		panel.addItem("Revolver");		
		return panel;
	}	
}
