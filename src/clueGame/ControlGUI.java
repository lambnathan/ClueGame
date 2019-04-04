package clueGame;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControlGUI extends JPanel{
	
	public ControlGUI() {
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = createTurnPanel();
		topPanel.add(createButtonPanel());
		add(topPanel);
		JPanel bottomPanel = createDieRollPanel();
		bottomPanel.add(createGuessPanel());
		bottomPanel.add(createGuessResultPanel());
		add(bottomPanel);
	}
	
	
	public JPanel createTurnPanel() {
		
	}
	
	public JPanel createButtonPanel() {
		
	}
	
	public JPanel createDieRollPanel() {
		
	}
	
	public JPanel createGuessPanel() {
		
	}
	
	public JPanel createGuessResultPanel() {
		
	}

	
	public static void main(String[] args) {
		//creates the main JFrame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(550, 200);
		ControlGUI control = new ControlGUI();
		//frame.add(control, );
		
		//view the JFrame
		frame.setVisible(true);
	}
}
