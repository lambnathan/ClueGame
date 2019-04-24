package clueGame;

import javax.swing.JOptionPane;

public class AlreadyAccused extends JOptionPane {
	public AlreadyAccused() {
		setName("Test");
		setSize(600,250);
		String message = "You failed your accusation, you can't make another!";
		JOptionPane.showMessageDialog(null, message, "You won!", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
