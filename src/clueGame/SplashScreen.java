package clueGame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SplashScreen extends JOptionPane {
	public SplashScreen() {
		setName("Test");
		setSize(600,250);
		
		String message = "You are Miss Scarlet, use the 'next player' button to take your turn.";
		JOptionPane.showMessageDialog(null, message);
	}
	
}
