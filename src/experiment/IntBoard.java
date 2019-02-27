//Authors: Elliott McCabe and Nathan Lambert
package experiment;

import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjmtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	
	//constructor
	public IntBoard() {
		super();
		calcAdjacencies(adjmtx);
	}
	//determine the adjacent cells
	public void calcAdjacencies(Map<BoardCell, Set<BoardCell>> adjmtx) {
		
	}
	//return set of all adjacent cells
	public Map<BoardCell, Set<BoardCell>> getAdjList() {
		return adjmtx;
	}
	//calculates targets that are pathLength distance from startCell, stored in a set.
	public void calcTargets(Set startCell, int pathLength) {
		
	}
	//returns list of targets as a set
	public Set<BoardCell> getTargets() {
		return targets;
	}
}
