//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.GridLayout;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class PlayerCardsGUI extends JPanel{
	
	public PlayerCardsGUI(Board board) {
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(0, 1));
		
		Set<Card> peopleCards = new HashSet<>();
		Set<Card> roomCards = new HashSet<>();
		Set<Card> weaponCards = new HashSet<>();
		
		for(Player p: board.getPlayerList()) {
			if(p instanceof HumanPlayer) {
				for(Card c : p.getPlayerCards()) {
					if(c.getCardType() == CardType.PERSON) {
						peopleCards.add(c);
					}
					else if(c.getCardType() == CardType.ROOM) {
						roomCards.add(c);
					}
					else if(c.getCardType() == CardType.WEAPON) {
						weaponCards.add(c);
					}
				}
			}
		}
		
		
		JPanel peoplePanel = makePeoplePanel(peopleCards);
		JPanel roomsPanel = makeRoomsPanel(roomCards);
		JPanel weaponsPanel = makeWeaponsPanel(weaponCards);
		
		main.add(peoplePanel);
		main.add(roomsPanel);
		main.add(weaponsPanel);
		main.setBorder(new TitledBorder(new EtchedBorder(), "My Cards"));
		add(main);
	}
	
	
	public JPanel makePeoplePanel(Set<Card> peopleCards) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		for(Card c: peopleCards) {
			JTextArea people = new JTextArea(2, 15);
			people.setEditable(false);
			people.setText(c.getCardName());
			people.setBorder(new EtchedBorder());
			panel.add(people);
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(), "People"));
		return panel;
	}
	
	public JPanel makeRoomsPanel(Set<Card> roomCards) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		for(Card c: roomCards) {
			JTextArea room = new JTextArea(2, 15);
			room.setEditable(false);
			room.setText(c.getCardName());
			room.setBorder(new EtchedBorder());
			panel.add(room);
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Rooms"));
		return panel;
	}
	
	public JPanel makeWeaponsPanel(Set<Card> weaponCards) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));
		for(Card c: weaponCards) {
			JTextArea weapon = new JTextArea(2, 15);
			weapon.setEditable(false);
			weapon.setText(c.getCardName());
			weapon.setBorder(new EtchedBorder());
			panel.add(weapon);
		}
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
		return panel;
	}
	

}
