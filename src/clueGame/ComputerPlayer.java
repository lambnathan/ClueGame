//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player {
	
	private Set<Character> visitedRooms;

	public ComputerPlayer(String playerName, int row, int column, Color color) {
		super(playerName, row, column, color);
		visitedRooms = new HashSet<>();
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
		
	}
	
	public void createSuggestion() {
		
	}
}
