//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.Color;

public class HumanPlayer extends Player {
	
	public HumanPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
	}
	
	//testing only
	public void clearCards() {
		playerCards.clear();
	}
}
