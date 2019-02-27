//Authors: Elliott McCabe and Nathan Lambert
package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map<BoardCell, Set<BoardCell>> adjmtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private BoardCell[][] grid;
	//constants for 4x4 grid
	private final int TOTAL_COL = 4;
	private final int TOTAL_ROW = 4;
	
	//constructor
	public IntBoard() {
		super();
		//Declaring various maps and sets
		adjmtx = new HashMap<BoardCell, Set<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[TOTAL_ROW][TOTAL_COL];
		//Instantiating grid with cells
		for(int i = 0; i <TOTAL_ROW; i++) {
			for(int j = 0; j <TOTAL_COL; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}	
		calcAdjacencies();
	}
	//determine the adjacent cells
	public void calcAdjacencies() {
		//going through columns
		for(int i = 0; i < TOTAL_ROW; i++) {
			//going through rows
			for(int j = 0; j < TOTAL_COL; j++) {
				HashSet<BoardCell> adjList = new HashSet<BoardCell>();
				//corner cases
				//(0,0) case
				if(i == 0 && j == 0) {
					adjList.add(grid[i + 1][j]);
					adjList.add(grid[i][j + 1]);
				}
				//(3,3) case
				else if(i == 3 && j == 3 ) {
					adjList.add(grid[i][j - 1]);
					adjList.add(grid[i - 1][j]);
				}
				//(0,3) case
				else if(i == 0 && j == 3 ) {
					adjList.add(grid[i][j - 1]);
					adjList.add(grid[i + 1][j]);
				}
				//(3,0) case
				else if(i == 3 && j == 0 ) {
					adjList.add(grid[i - 1][j]);
					adjList.add(grid[i][j + 1]);
				}
				//wall cases
				//Top wall
				else if (i == 0) {
					adjList.add(grid[i][j + 1]);
					adjList.add(grid[i][j - 1]);
					adjList.add(grid[i + 1][j]);
				}
				//bottom wall
				else if (i == 0) {
					adjList.add(grid[i][j + 1]);
					adjList.add(grid[i][j - 1]);
					adjList.add(grid[i - 1][j]);
				}
				//Leftmost wall
				else if (j == 0) {
					adjList.add(grid[i + 1][j]);
					adjList.add(grid[i][j + 1]);
					adjList.add(grid[i - 1][j]);
				}
				//Rightmost wall
				else if (j == 3) {
					adjList.add(grid[i - 1][j]);
					adjList.add(grid[i][j - 1]);
					adjList.add(grid[i + 1][j]);
				}
				//middle
				else {
					adjList.add(grid[i + 1][j]);
					adjList.add(grid[i - 1][j]);
					adjList.add(grid[i][j - 1]);
					adjList.add(grid[i][j + 1]);
				}
			}
		}
	}
	//return set of all adjacent cells
	public Map<BoardCell, Set<BoardCell>> getAdjList() {
		return adjmtx;
	}
	//calculates targets that are pathLength distance from startCell, stored in a set.
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	//returns list of targets as a set
	public Set<BoardCell> getTargets() {
		return targets;
	}
}
