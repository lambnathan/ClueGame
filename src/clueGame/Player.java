package clueGame;

import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
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
	
}