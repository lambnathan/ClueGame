//Authors: Nathan Lambert and Elliott McCabe

package clueGame;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	private static int numRows;
	private static int numColumns;
	private BoardCell[][] board;
	private HashSet<BoardCell> visited;
	private HashMap<Character, String> legend;
	private HashMap<BoardCell, HashSet<BoardCell>> adjmtx;
	private HashSet<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<Character, String>();
		adjmtx = new HashMap<BoardCell, HashSet<BoardCell>>();
		targets = new HashSet<BoardCell>();
		visited = new HashSet<BoardCell>();
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	//loads the room config file and the board config file
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		}
		catch(BadConfigFormatException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}
		calcAdjacencies();
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader(roomConfigFile);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("Not a valid file.");
		}
		//read in each line
		while (in.hasNext()) {
			Character symbol = null;
			String room = "";
			String line = in.nextLine();
			if(!line.contains("Card") && !line.contains("Other")) {
				throw new BadConfigFormatException("Invalid legend, needs to be either \"Card\" or \"Other\".");
			}
			//symbol of room
			symbol = new Character(line.charAt(0));
			if(Character.isDigit(symbol)) {
				throw new BadConfigFormatException("The symbol cannot be a number");
			}
			//go to next comma
			int index = line.lastIndexOf(',');
			//room = 3rd index to next comma
			room = line.substring(3, index);
			legend.put(symbol, room);
		}
	}
	
	//loads the playing board and gets the size
	public void loadBoardConfig() throws BadConfigFormatException {
		//tries to open the file
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader(boardConfigFile);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("Not a valid file.");
		}
		
		int row = 0;
		String[] cells = null;
		ArrayList<Integer> columnNums = new ArrayList<Integer>();
		while (in.hasNext()) {
			String line = in.nextLine();
			//splits all of the strings seperated by a common and puts in an array
			cells = line.split(",");
			columnNums.add(cells.length);
			//go over length of array)
			for(int i = 0; i < cells.length; i++) {
				String cell = cells[i];
				//blank cell
				if(cell.length() == 0) {
					throw new BadConfigFormatException("There is a blank cell");
				}
				char symbol = cell.charAt(0);
				//check if cell symbol is in the legend
				if(!(legend.containsKey(symbol))) {
					throw new BadConfigFormatException("The cell symbol does not exist in the legend.");
				}
				BoardCell c = null;
				if(cell.length() > 1) { //if the cell is a string of length > 1, it is a door
					char doorSymbol = cell.charAt(1);
					switch(doorSymbol) {
						case 'U':
							c = new BoardCell(row, i, symbol, DoorDirection.UP);
							c.setDoorwayBool(true);
							break;
						case 'D':
							c = new BoardCell(row, i, symbol, DoorDirection.DOWN);
							c.setDoorwayBool(true);
							break;
						case 'R':
							c = new BoardCell(row, i, symbol, DoorDirection.RIGHT);
							c.setDoorwayBool(true);
							break;
						case 'L':
							c = new BoardCell(row, i, symbol, DoorDirection.LEFT);
							c.setDoorwayBool(true);
							break;
						case 'N':
							c = new BoardCell(row, i, symbol, DoorDirection.NONE);
							c.setDoorwayBool(false);
							break;
						default:
							throw new BadConfigFormatException("Invalid direction for entering a room");
					}
					
				}
				else if (cell.length() > 2){
					throw new BadConfigFormatException("Invalid cell format (too many characters)");
				}
				else {
					c = new BoardCell(row, i, symbol, DoorDirection.NONE);
					c.setDoorwayBool(false);
				}
				//put the BoardCell in the gameboard array
				board[row][i] = c;
			}
			row += 1;
		}
		//set rows and columns after complete
		for(int i = 0; i < columnNums.size(); i++) {
			for(int j = i + 1; j < columnNums.size(); j++) {
				if(columnNums.get(i) != columnNums.get(j)) {
					throw new BadConfigFormatException("The number of columns for each row do not match.");
				}
			}
		}
		numRows = row;
		numColumns = cells.length;
	}
		
	public void setConfigFiles(String boardConfig, String legendConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = legendConfig;
	}
	
	public HashMap<Character, String> getLegend() {		
		return legend;
	}
	
	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int column) {
		return board[row][column];
	}
	
	public void calcAdjacencies() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j <numColumns; j++) {
				HashSet<BoardCell> adjList = new HashSet<BoardCell>();
				BoardCell current = getCellAt(i,j);
				
				if(current.isDoorway()) {
					if(current.getDoorDirection() == DoorDirection.DOWN) {
						adjList.add(getCellAt(i+1,j));
					}
					else if(current.getDoorDirection() == DoorDirection.RIGHT) {
						adjList.add(getCellAt(i,j + 1));
					}
					else if(current.getDoorDirection() == DoorDirection.LEFT) {
						adjList.add(getCellAt(i,j - 1));
					}
					else if(current.getDoorDirection() == DoorDirection.UP) {
						adjList.add(getCellAt(i-1,j));
					}					
				}
				
				else if(current.isRoom() && !current.isDoorway()) {
					adjList.clear();
				}
				
				//if statements for walkway cases
				else if(current.isWalkway()) {
					
					//check below
					if(i >= 0) {
						if(i < numRows - 1) {
							if(getCellAt(i + 1, j).isWalkway()) {
								adjList.add(getCellAt(i + 1,j));
							}
							if(getCellAt(i + 1, j).isDoorway()){
								if(getCellAt(i + 1, j).getDoorDirection() == DoorDirection.UP) {
									adjList.add(getCellAt(i + 1, j));
								}
							}
						}
					}
					//check above
					if(i <= numRows - 1) {
						if(i > 0) {
							if(getCellAt(i - 1, j).isWalkway()) {
								adjList.add(getCellAt(i - 1, j));
							}
							if(getCellAt(i - 1, j).isDoorway()){
								if(getCellAt(i - 1,j).getDoorDirection() == DoorDirection.DOWN) {
									adjList.add(getCellAt(i - 1, j));
								}
							}
						}
					}
					//check right
					if(j >= 0) {
						if(j < numColumns - 1) {
							if(getCellAt(i, j + 1).isWalkway()) {
								adjList.add(getCellAt(i, j + 1));
							}
							if(getCellAt(i, j + 1).isDoorway()){
								if(getCellAt(i, j + 1).getDoorDirection() == DoorDirection.LEFT) {
									adjList.add(getCellAt(i, j + 1));
								}
							}
						}
					}
					//check left
					if(j <= numColumns - 1) {
						if(j > 0) {
							if(getCellAt(i, j - 1).isWalkway()) {
								adjList.add(getCellAt(i, j - 1));
							}
							if(getCellAt(i, j - 1).isDoorway()){
								if(getCellAt(i, j - 1).getDoorDirection() == DoorDirection.RIGHT) {
									adjList.add(getCellAt(i, j - 1));
								}
							}
						}
					}
				}
				adjmtx.put(getCellAt(i,j), adjList);
			}
		}
	}

	public HashSet<BoardCell> getAdjList(int row, int column) {
		return adjmtx.get(getCellAt(row,column));

	}

	public HashSet<BoardCell> getTargets() {
		return targets;
	}

	public void calcTargets(int i, int j, int pathLength) {
		//clear each list
		visited.clear();
		targets.clear();
		visited.add(getCellAt(i,j));
		findAllTargets(getCellAt(i,j), pathLength);
	}

	private void findAllTargets(BoardCell startCell, int pathLength) {
		HashSet<BoardCell> adj = new HashSet<BoardCell>();
		adj = adjmtx.get(startCell);
		for (BoardCell bd : adj) {
			//if already in visited list, skip
			if(visited.contains(bd)) {
				continue;
			}
			if(bd.isDoorway()) {
				targets.add(bd);
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
	
}
