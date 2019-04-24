package clueGame;

import javax.swing.JOptionPane;

public class AccusationSplash extends JOptionPane {
	public AccusationSplash(boolean isWinner) {
		setName("Test");
		setSize(600,250);
		if(isWinner) {
			message = "You did it! You have won the game, now closing.";

		}
		else {
			message = "You are incorrect! You can no longer help in this game, it will continue without you.";
		}
		JOptionPane.showMessageDialog(null, message, "You won!", JOptionPane.INFORMATION_MESSAGE);
	}
	
}
