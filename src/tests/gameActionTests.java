package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
