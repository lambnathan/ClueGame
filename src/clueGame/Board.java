//--------------------Board--------------------

//Authors: Nathan Lambert and Elliott McCabe

package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Board extends JPanel{
	public static final int MAX_BOARD_SIZE = 50;
	public static final int CELL_SIZE = 25; //size of a square cell
	public static final int PLAYER_RADIUS = 20;
	public static final int PLAYER_INSIDE_ROOM_RAD = 8;
	//door will either have length or width that is this value less than the cell's width or height, and will be moved either up and down or left and right this ammount
	public static final int DOOR_OFFSET = 21; 
	private boolean isPlayerMoved = true;
	
	private static int numRows;
	private static int numColumns;
	private static BoardCell[][] board;
	private Set<BoardCell> visited;
	private static Map<Character, String> legend; //a map that maps a character to the room it represents
	private Map<BoardCell, HashSet<BoardCell>> adjmtx;
	private HashSet<BoardCell> targets;
	private static String boardConfigFile;
	private static String roomConfigFile;
	private static String playerConfigFile;
	private static String weaponConfigFile;
	private Player currentPlayer;
	private Player currentPlayerBeforeMove;
	private int playerIndex;
	private boolean playerHasLost = false;
	
	private Set<Card> answer; //stores a randomly selected player, weapon and room
	
	private ArrayList<Player> players;
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
		
		players = new ArrayList<>();
		cards = new HashSet<>();
		
		answer = new HashSet<>();
		
		playerIndex = 0;
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
		selectAnswer();
		dealCards();
		
	}
	
	
	
	//loads room config and creates a map that maps a character to the room it represents
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
			
			//creates a room card for each viable room
			if(line.contains("Card")) {
				Card c = new Card(room, CardType.ROOM);
				cards.add(c);
			}
			legend.put(symbol, room);
		}
		in.close();
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
				char symbol = cell.charAt(0); //room symbol is always the first character
				//check if cell symbol is in the legend
				if(!(legend.containsKey(symbol))) {
					throw new BadConfigFormatException("The cell symbol does not exist in the legend.");
				}
				BoardCell c = null;
				if(cell.length() == 2) { //if the cell is a string of length 2, it is a door
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
				else {
					c = new BoardCell(row, i, symbol, DoorDirection.NONE);
					c.setDoorwayBool(false);
					if(cell.contains("DRAW")) {
						c.shouldDrawName(); 
					}
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
		/*
		 * gets each line of player config file, splits the line on the commas (,)
		 * then sets the appropriate values based on their index in the array. All
		 * player config files should have the same format
		 * makes sure to trim the strings in the array
		 */
		while(in.hasNext()) {
			String[] line = null;
			String currentLine = in.nextLine();
			line = currentLine.split(",");
			String name = line[0].trim();
			int row = Integer.valueOf(line[1].trim());
			int column = Integer.valueOf(line[2].trim());
			Color color = convertColor(line[3].trim());
			//determines whether the player is human or computer
			if(line[4].trim().equals("Human")) {
				Player player = new HumanPlayer(name, row, column, color);
				players.add(player);
			}
			else {
				Player player = new ComputerPlayer(name, row, column, color);
				players.add(player);
			}
			//adds a card for each player
			Card c = new Card(name, CardType.PERSON);
			cards.add(c);
			
		}
		in.close();
		//grabs human player
		currentPlayer = null;
		currentPlayerBeforeMove = null;

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
		//creates a weapon card for each weapon
		while(in.hasNext()) {
			String weapon = in.nextLine().trim();
			Card c = new Card(weapon, CardType.WEAPON);
			cards.add(c);
		}
		in.close();
	}
		
	public void setConfigFiles(String boardConfig, String legendConfig, String playerConfig, String weaponConfig) {
		boardConfigFile = boardConfig;
		roomConfigFile = legendConfig;
		playerConfigFile = playerConfig;
		weaponConfigFile = weaponConfig;
	}
	
	public static Map<Character, String> getLegend() {		
		return legend;
	}
	
	public static int getNumRows() {
		return numRows;
	}
	
	public static int getNumColumns() {
		return numColumns;
	}
	
	public static BoardCell getCellAt(int row, int column) {
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
		//keeps track of cards that have been dealt
		Set<Card> seen = new HashSet<>();
		/*
		 * while we have not dealt all of the cards, go over each player and make sure they have one of 
		 * each type of card. if they already have each type of card,
		 * add the current card if it has not already been dealt
		 */
		while(seen.size() != cards.size()) {
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
	}
	
	/*
	 * randomly selects three cards, one of each type, to be the answer for the game
	 * this is called after all the cards have been loaded in before cards are dealt
	 */
	public void selectAnswer() {
		Card[] cardArr = new Card[cards.size()];
		System.arraycopy(cards.toArray(), 0, cardArr, 0, cards.size());
		while(answer.size() < 3) {
			int rand = new Random().nextInt(cards.size());
			Card c = cardArr[rand];
			boolean inside = false;
			for(Card card: answer) {
				if(c.getCardType() == card.getCardType()) {
					inside = true;
					break;
				}
			}
			if(!inside) {
				answer.add(c);
				cards.remove(c);
			}
		}
	}
	/*
	 * Finding the first player in the game that is able to disprove the suggestion
	 * returns null if no one can disprove
	 */
	public Card handleSuggestion(Solution suggestion, Player accuser) {
		Player[] playerArr = new Player[players.size()];
		System.arraycopy(players.toArray(), 0, playerArr, 0, players.size());
		Card disproveCard = null;
		Player accusedPlayer = null;
		int indexOfAccuser = 0;
		
		for(Player p : playerArr) {
			if(p.equals(accuser)) {
				break;
			}
			indexOfAccuser++;
		}
		
		/*
		 * going player by player, if that player has a card that can disprove the suggestion, they are selected
		 */
		if(indexOfAccuser == playerArr.length - 1) {
			indexOfAccuser = -1;
		}
		int i = indexOfAccuser + 1;
		while(i != indexOfAccuser) {
			if(!playerArr[i].equals(accuser) && playerArr[i].disproveSuggestion(suggestion) != null) {
				disproveCard = playerArr[i].disproveSuggestion(suggestion);
				break;
			}	
			
			if(i == playerArr.length - 1) {
				i = -1;
			}
			i++;
		}
		
		//get the accused player
		for(Player p : players) {
			if(p.getPlayerName() == suggestion.getPersonName()) {
				accusedPlayer = p;
				accusedPlayer.setLocation(accuser.getRow(), accuser.getColumn());
				break;
			}
		}
		
		return disproveCard;
	}

	public boolean checkAccusation(Solution accusation) {
		if(answer.contains(accusation.getPerson()) && answer.contains(accusation.getWeapon()) && answer.contains(accusation.getRoom())) {
			return true;
		}
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
	
	//draw everything on the game board
	public void paintComponent(Graphics g) {
		//draw the board (all the board cells and the rooms)
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				getCellAt(i, j).draw(g);
			}
		}
		
		//goes through each player and draws them
		for(Player p : players) {
			p.draw(g);
		}
		
		
	}
	
	//gets called whenever the "Next Player" button is clicked
	public void makeMove() {
		currentPlayer = players.get(playerIndex % players.size()); //current player is set to the playerIndex
		//currentPlayerBeforeMove = players.get(playerIndex % players.size()); //current player is set to the playerIndex
		isPlayerMoved = false; //keeps track if a player has completed their turn
		int diceRoll = getDiceRoll();
		if(currentPlayer instanceof HumanPlayer) {
			//logic for stopping player from being able to move if they have failed the accusation
			if(!playerHasLost) {
				calcTargets(currentPlayer.getRow(), currentPlayer.getColumn(), diceRoll);
				repaint(); //shows highlighted options
			}
			else {
				isPlayerMoved = true;
				playerIndex++;
				ControlGUI.showTurn("Skip Miss Scarlet (Lost)", 0);
				return;
			}
	
		}
		else if (currentPlayer instanceof ComputerPlayer) {
			//check if the computer should make a suggestion; else move on the board
			if(((ComputerPlayer) currentPlayer).getShouldAccuse()) {
				((ComputerPlayer) currentPlayer).makeAccusation();
			}
			else {
				calcTargets(currentPlayer.getRow(), currentPlayer.getColumn(), diceRoll);
				BoardCell cellToMoveTo = ((ComputerPlayer) currentPlayer).pickLocation(targets);
				currentPlayer.setLocation(cellToMoveTo.getRow(), cellToMoveTo.getColumn());
				if(getCellAt(currentPlayer.getRow(), currentPlayer.getColumn()).isDoorway()) {
					Solution computerSuggestion = ((ComputerPlayer) currentPlayer).createSuggestion(this);
					String computerGuess = computerSuggestion.getPersonName() + " in the " + computerSuggestion.getRoomName() + " with the " + computerSuggestion.getWeaponName();
					ControlGUI.showGuess(computerGuess);
					Card disproveCard = handleSuggestion(computerSuggestion, currentPlayer);
					//check if there are no cards that can disprove the suggestion
					if(disproveCard == null && !currentPlayer.getPlayerCards().contains(computerSuggestion.getPerson()) && !currentPlayer.getPlayerCards().contains(computerSuggestion.getRoom()) && !currentPlayer.getPlayerCards().contains(computerSuggestion.getWeapon())) {
						ControlGUI.showResponse("No cards to disprove suggestion");
						((ComputerPlayer) currentPlayer).setAccusation(computerSuggestion);
						((ComputerPlayer) currentPlayer).shouldAccuse();
					}
					//card to disprove in players hand
					else if(disproveCard == null) {
						//do nothing
					}
					else {
						for(Player p: players) {
							//add the card that disproves the suggestion to each players list of seen cards
							if(p instanceof ComputerPlayer) {
								((ComputerPlayer)p).addSeenCard(disproveCard);
							}
						}
						String disproveCardName;
						disproveCardName = disproveCard.getCardName();
						ControlGUI.showResponse(disproveCardName);
					}
				}
				isPlayerMoved = true;
				repaint();	//calls repaint to show updated computer player locations
			}
		}
		ControlGUI.showTurn(currentPlayer.getPlayerName(), diceRoll); //fills in dialog boxes in control gui		
		if(playerHasLost) {
			playerIndex++; //moves to next player index
		}
	} 
	
	//mouse listener for the board
	private class ClickDetection implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			if(currentPlayer instanceof HumanPlayer && !playerHasLost) { //only detects mouse clicks for human players
				boolean clickedValidTile = false;
				for(BoardCell cell : targets) {
					//creates new rectangle that is the size of the cell, with the origin in the upper left
					Rectangle rect = new Rectangle(cell.getColumn() * CELL_SIZE, cell.getRow() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
					if(rect.contains(e.getPoint())) { //if the correct rectangle is clicked on
						clickedValidTile = true;
						currentPlayer.setLocation(cell.getRow(), cell.getColumn()); //move player to selected tile
						if(cell.isDoorway()) {
							String roomName = legend.get(cell.getInitial());
							String personName = currentPlayer.getPlayerName();
							SuggestionWindow sg = new SuggestionWindow(roomName, personName);
							sg.setVisible(true);
						}
						currentPlayer = players.get(playerIndex % players.size()); //update the current player and call repaint to get rid of highlighted tiles
						repaint();
						isPlayerMoved = true; //player has moved
						break;
					}
				}
				//if the player did not select a valid cell
				if(!clickedValidTile) {
					JOptionPane.showMessageDialog(null, "You must select a highlighted tile!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}
		
	}
	
	//creates a new click detector for the board
	public ClickDetection getClickDetector() {
		ClickDetection cd = new ClickDetection();
		return cd;
	}
	
	//simulates a random dice roll
	public int getDiceRoll() {
		Random rand = new Random();
		return 1 + rand.nextInt(6);
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	//for testing
	public void addCardToDeck(Card c) {
		cards.add(c);
	}
	
	public void clearAnswer() {
		answer.clear();
	}
	
	public void addCardToAnswer(Card c) {
		answer.add(c);
	}
	
	public boolean getIsPlayerMoved() {
		return isPlayerMoved;
	}
	
	public ArrayList<Player> getPlayerList(){
		return players;
	}
	
	public Set<Card> getCardList(){
		return cards;
	}
	
	public Set<Card> getAnswer() {
		return answer;
	}	
	
	public boolean getPlayerHasLost() {
		return playerHasLost;
	}
	
	public void setPlayerHasLost(boolean g) {
		this.playerHasLost = g;
	}
}