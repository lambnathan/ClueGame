//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JOptionPane;

import java.util.ArrayList;

public class ComputerPlayer extends Player {

	private Set<Character> visitedRooms;

	private Set<Card> seenCards; //keeps track of cards that players have used to disprove suggestions/accusations
	
	private boolean shouldMakeAccusation = false;
	
	private Solution accusation = null;

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		visitedRooms = new HashSet<>();
		seenCards = new HashSet<>();
		
		
	}

	/*
	 * if a computer player has a room as a target location and they have not been
	 * in that room already, they will automatically choose that cell as their location
	 * otherwise, they will randomly select a target from the list of possible targets
	 */
	public BoardCell pickLocation(Set<BoardCell> targets) {
		for(BoardCell b : targets) {
			if(b.isDoorway() && !visitedRooms.contains(b.getInitial())) {
				visitedRooms.add(b.getInitial());
				return b;
			}
		}
		//create array from targets set and pick random boardcell
		BoardCell[] boardcells = new BoardCell[targets.size()];
		System.arraycopy(targets.toArray(), 0, boardcells, 0, targets.size());
		int rnd = new Random().nextInt(boardcells.length);
		return boardcells[rnd];
	}

	public void addVisitedRoom(char c) {
		visitedRooms.add(c);
	}

	public void makeAccusation() {
		//computer player makes correct accusation
		//System.out.println(playerName + "accusing " + accusation.getPersonName() + " in the " + accusation.getRoomName() + " with the " + accusation.getWeaponName());
		if(Board.getInstance().checkAccusation(accusation)) {
			JOptionPane.showMessageDialog(null, playerName + " is the winner!", "Winner", JOptionPane.INFORMATION_MESSAGE);
			ClueGame.exitGame();
		}
		else {
			//should never make an accusation if they are not sure
			System.out.println("ERROR");
		}
	}
	
	public void setAccusation(Solution s) {
		accusation = s;
	}
	
	public void shouldAccuse() {
		shouldMakeAccusation = true;
	}
	
	public boolean getShouldAccuse() {
		return shouldMakeAccusation;
	}

	public Set<Card> getSeenCards() {
		return seenCards;
	}
	public void addSeenCard(Card c) {
		seenCards.add(c);
	}

	public Solution createSuggestion(Board board) {
		Card roomGuess = new Card(board.getLegend().get(board.getCellAt(this.getRow(), this.getColumn()).getInitial()), CardType.ROOM);
		Card weaponGuess = null;
		Card personGuess = null;
		ArrayList<Card> weapons = new ArrayList<Card>();
		ArrayList<Card> persons = new ArrayList<Card>();
		for(Card c : board.getCardList()) {
			if(!(seenCards.contains(c)) && !(this.getPlayerCards().contains(c))) {
				if(c.getCardType() == CardType.PERSON) {
					persons.add(c);
				}
				else if(c.getCardType() == CardType.WEAPON) {
					weapons.add(c);
				}
			}
		}
		for(Card c: board.getAnswer()) {
			if(!(seenCards.contains(c)) && !(this.getPlayerCards().contains(c))) {
				if(c.getCardType() == CardType.PERSON) {
					persons.add(c);
				}
				else if(c.getCardType() == CardType.WEAPON) {
					weapons.add(c);
				}
			}
		}
		Random rand = new Random();
		weaponGuess = weapons.get(rand.nextInt(weapons.size()));
		personGuess = persons.get(rand.nextInt(persons.size()));
		Solution s = new Solution(personGuess, roomGuess, weaponGuess);
		return s;
	}
	
	
	//testing only
	public void clearCards() {
		playerCards.clear();
		seenCards.clear();
	}
}
