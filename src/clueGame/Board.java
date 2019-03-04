package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	private static int numRows;
	private static int numColumns;
	private BoardCell[][] board;
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
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	//loads the room config file and the board config file
	public void initialize() {
		loadRoomConfig();
		loadBoardConfig();
	}
	
	public void loadRoomConfig() {
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader(boardConfigFile);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("Not a valid file.");
		}
		//read in each line
		while (in.hasNext()) {
			Character symbol;
			String room = "";
			String line = "";
			line = in.nextLine();
			//symbol of room
			symbol = new Character(line.charAt(0));
			//go to next comma
			int index = line.indexOf(',');
			//room = 3rd index to next comma
			room = line.substring(3, index + 1);
			legend.put(symbol, room);
		}
	}
	
	public void loadBoardConfig() {
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader(roomConfigFile);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("Not a valid file.");
		}
		
		int row = 0;
		while (in.hasNext()) {
			String line = in.nextLine();
			if(Character.isDigit(line.charAt(0))) {
				break;
			}
			String[] cells = line.split(",");
			for(int i = 0; i < cells.length - 1; i++) {
				String cell = cells[i];
				if(cell.length() > 1) {
					/*
					 * it has to be a char, so set the symbol to cell[0],
					 * then check if the length of the cell > 1. If it is,
					 * it has a door, so do the appropriate things
					 */
				}
				char symbol = cell.charAt(0);
				BoardCell c = new BoardCell(row, i);
				c.setInitial(symbol);
				board[row][i] = c;
			}
			row += 1;
		}
		numRows = row;
		numColumns = board[0].length;
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}
	
	public void setConfigFiles(String csv, String txt) {
		boardConfigFile = csv;
		roomConfigFile = txt;
		

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
	
}
