//Authors: Elliott McCabe and Nathan Lambert
package experiment;

import java.util.HashMap;
import java.util.HashSet;

public class IntBoard {
	private HashMap<BoardCell, HashSet<BoardCell>> adjmtx;
	private HashSet<BoardCell> visited;
	private HashSet<BoardCell> targets;
	private BoardCell[][] grid;
	//constants for 4x4 grid
	private final int TOTAL_COL = 4;
	private final int TOTAL_ROW = 4;
	
	//constructor
	public IntBoard() {
		super();
		//Declaring various maps and sets
		adjmtx = new HashMap<BoardCell, HashSet<BoardCell>>();
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
				adjmtx.put(grid[i][j], adjList);
			}
		}
	}
	//return set of all adjacent cells
	public HashSet<BoardCell> getAdjList(BoardCell current) {
		return adjmtx.get(current);
	}
	//calculates targets that are pathLength distance from startCell, stored in a set.
	public void calcTargets(BoardCell startCell, int pathLength) {
		//clear each list
		visited.clear();
		targets.clear();
		visited.add(startCell);
		
	}
	public void findAllTargets(BoardCell startCell, int pathLength) {
		HashSet<BoardCell> adj = new HashSet<BoardCell>();
		adj = adjmtx.get(startCell);
		for (BoardCell bd : adj) {
			//if already in visited list, skip
			if(visited.contains(bd)) {
				continue;
			}
			visited.add(bd);
			//if player rolls a 1, adjacent squares are targets.
			if(pathLength == 1) {
				targets.add(bd);
			}
			else {
				findAllTargets(bd, pathLength - 1);
			}
			visited.remove(bd);
		}
	}
	//returns list of targets as a set
	public HashSet<BoardCell> getTargets() {
		return targets;
	}
	
	public BoardCell getCell(int x, int y) {
		return grid[x][y];
	}
}
