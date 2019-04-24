//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	protected Set<Card> playerCards;
	
	public Player(String playerName, int row, int column, Color color) {
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
		playerCards = new HashSet<>();
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> disprove = new ArrayList<Card>();
		/*
		 * adds the players cards that are able to disprove the suggestion to a new arraylist to later randomly select a card
		 */
		for(Card c : playerCards) {
			if(c.getCardName().equals(suggestion.getPersonName())) {
				disprove.add(c);
			}
			else if(c.getCardName().equals(suggestion.getRoomName())) {
				disprove.add(c);
			}
			else if(c.getCardName().equals(suggestion.getWeaponName())) {
				disprove.add(c);
			}
		}
		Random rand = new Random();
		//if no cards that can disprove suggestion, return null
		if(disprove.isEmpty()) {
			return null;
		}
		return disprove.get(rand.nextInt(disprove.size()));
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
	
	//draws the players
	public void draw(Graphics g) {
		//makes player small if they are in a room, and chooses a random location in the room to make sure they don't overlap
		if(Board.getInstance().getCellAt(row, column).isDoorway()) {
			int randOffset = new Random().nextInt(Board.CELL_SIZE - 8);
			g.setColor(color);
			g.fillArc(column * Board.CELL_SIZE + randOffset, row * Board.CELL_SIZE + 2, Board.PLAYER_INSIDE_ROOM_RAD, Board.PLAYER_INSIDE_ROOM_RAD, 0, 360);
			g.setColor(Color.BLACK);
			g.drawArc(column * Board.CELL_SIZE + randOffset, row * Board.CELL_SIZE + 2, Board.PLAYER_INSIDE_ROOM_RAD, Board.PLAYER_INSIDE_ROOM_RAD, 0, 360);
		}
		else {
			g.setColor(color);
			g.fillArc(column * Board.CELL_SIZE + 2, row * Board.CELL_SIZE + 2, Board.PLAYER_RADIUS, Board.PLAYER_RADIUS, 0, 360);
			g.setColor(Color.BLACK);
			g.drawArc(column * Board.CELL_SIZE + 2, row * Board.CELL_SIZE + 2, Board.PLAYER_RADIUS, Board.PLAYER_RADIUS, 0, 360);
		}
	}
	
	/*
	 * getters, only for testing
	 */
	public String getPlayerName() {
		return playerName;
	}
	
	public void addCard(Card c) {
		playerCards.add(c);
	}
	
	public Set<Card> getPlayerCards(){
		return playerCards;
	}
	
	public void setLocation(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
}
