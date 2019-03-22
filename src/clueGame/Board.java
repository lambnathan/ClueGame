//--------------------Board--------------------

//Authors: Nathan Lambert and Elliott McCabe

package clueGame;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	public static final int MAX_BOARD_SIZE = 50;
	private static int numRows;
	private static int numColumns;
	private static BoardCell[][] board;
	private Set<BoardCell> visited;
	private static Map<Character, String> legend;
	private Map<BoardCell, HashSet<BoardCell>> adjmtx;
	private HashSet<BoardCell> targets;
	private static String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	
	private Set<Player> players;
	private Set<Card> cards;
	
	// variable used for singleton pattern
	private static Board theInstance = new Board();
	
	// constructor is private to ensure only one can be created
	private Board() {
		board = new BoardCell[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
		legend = new HashMap<>();
		adjmtx = new HashMap<>();
		targets = new HashSet<>();
		visited = new HashSet<>();
		
		players = new HashSet<>();
		cards = new HashSet<>();
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
			loadPlayerConfig();
			loadWeaponConfig();
		}
		catch(BadConfigFormatException e) {
			System.out.println(e);
			System.out.println(e.getMessage());
		}
		calcAdjacencies();
		dealCards();
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
			if(line.contains("Card")) {
				Card c = new Card(room, CardType.ROOM);
				cards.add(c);
			}
			legend.put(symbol, room);
		}
	}
	
	//loads the playing board and gets the size
	public static void loadBoardConfig() throws BadConfigFormatException {
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
		ArrayList<Integer> columnNums = new ArrayList<>();
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
		in.close();
	}
	/*
	 * reads all the players from the player config file and 
	 * adds them to the set of all players, with their correct properties
	 */
	public void loadPlayerConfig() throws BadConfigFormatException{
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader(playerConfigFile);
			in = new Scanner(reader);
		} catch (FileNotFoundException e) {
			System.out.println("Not a valid file.");
		}
		
		while(in.hasNext()) {
			String[] line = null;
			String currentLine = in.nextLine();
			line = currentLine.split(",");
			String name = line[0].trim();
			int row = Integer.parseInt(line[1]);
			int column = Integer.parseInt(line[2]);
			Color color = convertColor(line[3]);
			//
			if(line[4].equals("Human")) {
				Player player = new HumanPlayer(name, row, column, color);
				players.add(player);
			}
			else {
				Player player = new ComputerPlayer(name, row, column, color);
				players.add(player);
			}
			Card c = new Card(name, CardType.PERSON);
			cards.add(c);
			
		}
	}
	
	/*
	 * reads the weapon config file
	 */
	public void loadWeaponConfig() throws BadConfigFormatException{
		FileReader reader = null;
		Scanner in = null;
		try {
			reader = new FileReader(weaponConfigFile);
			in = new Scanner(reader);
		}
		catch(FileNotFoundException e) {
			System.out.println("Not a valid file.");
		}
		
		while(in.hasNext()) {
			String weapon = in.nextLine().trim();
			Card c = new Card(weapon, CardType.WEAPON);
			cards.add(c);
		}
	}
		
	public void setConfigFiles(String boardConfig, String legendConfig, String playerConfig, String weaponConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = legendConfig;
		playerConfigFile = playerConfig;
		weaponConfigFile = weaponConfig;
	}
	
	public Map<Character, String> getLegend() {		
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
				HashSet<BoardCell> adjList = new HashSet<>();
				BoardCell current = getCellAt(i,j);
				//doorway cases
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
				//room cases
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

	public Set<BoardCell> getAdjList(int row, int column) {
		return adjmtx.get(getCellAt(row,column));

	}

	public Set<BoardCell> getTargets() {
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
	    HashSet<BoardCell> adj;
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
	
	public void dealCards() {
		Set<Card> seen = new HashSet<>();
		for(Player p : players) {
			int weaponCount = 0;
			int playerCount = 0;
			int roomCount = 0;
			for(Card c: cards) {
				//if card has not been dealt
				if(!seen.contains(c)) {
					if(c.getCardType() == CardType.PERSON && playerCount == 0) {
						p.addCard(c);
						seen.add(c);
						playerCount++;
						break;
					}
					else if(c.getCardType() == CardType.ROOM && roomCount == 0) {
						p.addCard(c);
						seen.add(c);
						roomCount++;
						break;
					}
					else if(c.getCardType() == CardType.WEAPON && weaponCount == 0) {
						p.addCard(c);
						seen.add(c);
						weaponCount++;
						break;
					}
					
					//if player already has each type of card, add the next unseen card
					if(weaponCount + playerCount + roomCount == 3) {
						p.addCard(c);
						seen.add(c);
						break;
					}
				}
			}
		}
	}
	
	public void selectAnswer() {
		
	}
	
	public Card handleSuggestion() {
		return null;
	}
	
	public boolean checkAccusation(Solution accusation) {
		return false;
	}
	
	/*
	 * returns color object from string
	 */
	public Color convertColor(String strColor) {
		Color color;
		try {
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		}
		catch(Exception e) {
			color = null; //not defined
		}
		
		return color;
	}
	
	public Set<Player> getPlayerList(){
		return players;
	}
	
	public Set<Card> getCardList(){
		return cards;
	}
	
}
