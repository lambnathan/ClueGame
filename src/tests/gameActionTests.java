package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;

public class gameActionTests {

	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our own files
		board.setConfigFiles("data/BoardLayout.csv", "data/ClueRooms.txt", "data/CluePlayers.txt", "data/ClueWeapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void selectTarget() {
		//gets an arrayList that contains only computer players
		Set<Player> players = board.getPlayerList();
		ArrayList<Player> compPlayers = new ArrayList<Player>();
		for(Player p: players) {
			if(p instanceof ComputerPlayer) {
				compPlayers.add(p);
			}
		}
		
		//tests if there are no rooms in a list, than a random cell is selected
		ComputerPlayer p = (ComputerPlayer) compPlayers.get(0); //chooses first computer player in list
		p.setLocation(25, 3); //sets location to where it it not possible to get to a doorway
		board.calcTargets(25, 3, 6);
		Set<BoardCell> targets = board.getTargets();
		ArrayList<BoardCell> visited = new ArrayList<BoardCell>(); //keeps track of locations the computer player chooses. for this point, there are 3 possible locations
		//if it is random, we should see that the size of visisted is greater than 1 (it is choosing different locations)
		for(int i = 0; i < 10; i++) {
			BoardCell pickedLocation = p.pickLocation(targets);
			visited.add(pickedLocation);
		}
		assertTrue(visited.size() > 1);
	}
	
	@Test
	public void checkAccusation() {
		
	}
	
	@Test
	public void disproveSuggestion() {
		
	}
	
	@Test 
	public void handleSuggestion() {
		
	}
	
	@Test
	public void createSuggestion() {
		
	}

}
