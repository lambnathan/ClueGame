//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ClueGame extends JPanel{
	
	public ClueGame() {		
		setLayout(new GridLayout(2, 0));
		JPanel gameBoardPanel = new JPanel();
		gameBoardPanel.setLayout(new GridLayout(Board.getNumRows(), Board.getNumColumns()));
		
		JPanel topControlPanel = new JPanel();
		topControlPanel.setLayout(new GridLayout(1, 0));
		topControlPanel.add(createTurnPanel());
		topControlPanel.add(createButtonPanel());
		add(topControlPanel);
		
		JPanel bottomControlPanel = new JPanel();
		bottomControlPanel.setLayout(new FlowLayout());
		bottomControlPanel.add(createDieRollPanel());
		bottomControlPanel.add(createGuessPanel());
		bottomControlPanel.add(createGuessResultPanel());
		add(bottomControlPanel);
	}
	
	
	public JPanel createTurnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		JLabel turnLabel = new JLabel("Whose turn?");
		JTextField name = new JTextField(20);
		name.setEditable(false);
		panel.add(turnLabel);
		panel.add(name);
		panel.setBorder(new EtchedBorder());
		return panel;
	}
	
	public JPanel createButtonPanel() {
		//default uses flow layout
		JPanel panel = new JPanel();
		JButton nextPlayer = new JButton("Next player");
		JButton makeAccusation = new JButton("Make accusation");
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;
	}
	
	public JPanel createDieRollPanel() {
		JPanel panel = new JPanel();
		JTextField dieRoll = new JTextField(5);
		dieRoll.setEditable(false);
		panel.add(dieRoll);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die Roll"));
		return panel;
	}
	
	public JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		JTextField guess = new JTextField(30);
		guess.setEditable(false);
		panel.add(guess);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}
	
	public JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		JTextField response = new JTextField(18);
		response.setEditable(false);
		panel.add(response);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Response to Guess"));
		return panel;
	}
	
	
	public static void main(String[] args) {
		// Board is singleton, get the only instance
		Board board;
		board = Board.getInstance();
		// set the file names to use our own files
		board.setConfigFiles("data/BoardLayout.csv", "data/ClueRooms.txt", "data/CluePlayers.txt", "data/ClueWeapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		//creates the main JFrame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 750);
		ClueGame control = new ClueGame();
		frame.add(control, BorderLayout.CENTER);
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		}
		catch(Exception e) {
			System.err.println("Look and feel not set");
		}
		
		//view the JFrame
		frame.setVisible(true);
	}
}
