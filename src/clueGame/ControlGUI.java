//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel implements MouseListener {
	private static JTextField dieRoll;
	private static JTextField name;
	private static JTextField response;
	private static JTextField guess;
	
	public ControlGUI() {
		
		setLayout(new GridLayout(2, 0));
		
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
		name = new JTextField(20);
		name.setEditable(false);
		panel.add(name);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Whose turn?"));
		return panel;
	}
	
	public JPanel createButtonPanel() {
		//default uses flow layout
		JPanel panel = new JPanel();
		JButton nextPlayer = new JButton("Next player");
		JButton makeAccusation = new JButton("Make accusation");
		nextPlayer.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(Board.getInstance().getIsPlayerMoved()) {
						Board.getInstance().makeMove();
					}
					//if player clicks next player before completing turn
					else {
						JOptionPane.showMessageDialog(null, "You must select a tile to move to first!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		makeAccusation.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1) {
					SuggestionWindow accuseWindow = new SuggestionWindow(true);
					accuseWindow.setVisible(true);
				}
			}
		});	
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;
	}
	
	public JPanel createDieRollPanel() {
		JPanel panel = new JPanel();
		dieRoll = new JTextField(5);
		dieRoll.setEditable(false);
		panel.add(dieRoll);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Die Roll"));
		return panel;
	}
	
	public JPanel createGuessPanel() {
		JPanel panel = new JPanel();
		guess = new JTextField(40);
		guess.setEditable(false);
		panel.add(guess);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Guess"));
		return panel;
	}
	
	public JPanel createGuessResultPanel() {
		JPanel panel = new JPanel();
		response = new JTextField(18);
		response.setEditable(false);
		panel.add(response);
		panel.setBorder(new TitledBorder(new EtchedBorder(), "Response to Guess"));
		return panel;
	}
	
	public static void showTurn(String playerName, int roll) {
		name.setText(playerName);
		dieRoll.setText(String.valueOf(roll));
	}
	
	public static void showResponse(String suggestionDisproveCard) {
		response.setText(suggestionDisproveCard);
	}
	
	public static void showGuess(String g) {
		guess.setText(g);
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	
	//main function for testing, will open a window with only controlGUI, not whole game board
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 150);
		ControlGUI control = new ControlGUI();
		frame.add(control, BorderLayout.CENTER);
		
		//view the JFrame
		frame.setVisible(true);
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {}


	@Override
	public void mouseExited(MouseEvent arg0) {}


	@Override
	public void mousePressed(MouseEvent arg0) {}


	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
