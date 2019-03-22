//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private Set<Card> playerCards;
	
	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		playerCards = new HashSet<>();
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}

	
	/*
	 * getters, only for testing
	 */
	public String getPlayerName() {
		return playerName;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}
	
	public void addCard(Card c) {
		playerCards.add(c);
	}
	
	public Set<Card> getPlayerCards(){
		return playerCards;
	}
	
}
