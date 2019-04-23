//Authors: Elliott McCabe, Nathan Lambert

package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	
	public JPanel persons, weapons, rooms;
	public JPanel personCheckList, weaponCheckList, roomCheckList;
	
	private notesDialog dialog;
	
	//constructor
	public DetectiveNotes() {
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
	public JPanel createPersonGuessPanel() {
		JPanel panel = new JPanel();
		JComboBox<String> comboBox = new JComboBox<String>();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Person Guess"));
		comboBox.addItem("Unsure");
		comboBox.addItem("Miss Scarlet");
		comboBox.addItem("Colonel Mustard");
		comboBox.addItem("Mr. Green");
		comboBox.addItem("Mrs. White");
		comboBox.addItem("Mrs. Peacock");
		comboBox.addItem("Professor Plum");
		panel.add(comboBox);
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
	public JPanel createRoomGuessPanel() {
		JPanel panel = new JPanel();
		JComboBox<String> comboBox = new JComboBox<String>();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Room Guess"));
		comboBox.addItem("Unsure");
		comboBox.addItem("Conservatory");
		comboBox.addItem("Kitchen");
		comboBox.addItem("Ballroom");
		comboBox.addItem("Library");
		comboBox.addItem("Arcade room");
		comboBox.addItem("Gun room");
		comboBox.addItem("Trophy room");
		comboBox.addItem("Pantry");
		comboBox.addItem("Sauna");	
		panel.add(comboBox);
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
	public JPanel createWeaponGuessPanel() {
		JPanel panel = new JPanel();
		JComboBox<String> comboBox = new JComboBox<String>();
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapon Guess"));
		comboBox.addItem("Unsure");
		comboBox.addItem("Rope");
		comboBox.addItem("Lead Pipe");
		comboBox.addItem("Knife");
		comboBox.addItem("Wrench");
		comboBox.addItem("Candlestick");
		comboBox.addItem("Revolver");	
		panel.add(comboBox);
		return panel;
	}	
}
