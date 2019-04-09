//Authors: Elliott McCabe and Nathan Lambert
package tests;

//Assert.assertEquals
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class ClueLayout_Tests {
	//making all of these false to fail
	public static final int LEGEND_SIZE = 11; 
	public static final int NUM_ROWS = 26;
	public static final int NUM_COLUMNS = 26;
	
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our own files
		board.setConfigFiles("data/BoardLayout.csv", "data/ClueRooms.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Conservatory", legend.get('C'));
		assertEquals("Ballroom", legend.get('B'));
		assertEquals("Closet", legend.get('X'));
		assertEquals("Walkway", legend.get('W'));
		assertEquals("Kitchen", legend.get('K'));
		assertEquals("Library", legend.get('L'));
		assertEquals("Arcade room", legend.get('A'));
		assertEquals("Gun room", legend.get('G'));
		assertEquals("Trophy room", legend.get('T'));
		assertEquals("Pantry", legend.get('P'));
		assertEquals("Sauna", legend.get('S'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(5, 1);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		room = board.getCellAt(3, 3);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		room = board.getCellAt(4, 7);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		room = board.getCellAt(18, 13);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		room = board.getCellAt(0, 0);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		BoardCell cell = board.getCellAt(6, 0);
		assertFalse(cell.isDoorway());	
		assertEquals(DoorDirection.NONE, cell.getDoorDirection());

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(14, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		// Test first cell in room
		assertEquals('C', board.getCellAt(0, 0).getInitial());
		assertEquals('A', board.getCellAt(11, 0).getInitial());
		assertEquals('G', board.getCellAt(24, 0).getInitial());
		// Test last cell in room
		assertEquals('B', board.getCellAt(25, 25).getInitial());
		assertEquals('K', board.getCellAt(0, 25).getInitial());
		// Test a walkway
		assertEquals('W', board.getCellAt(6, 0).getInitial());
		// Test the closet
		assertEquals('X', board.getCellAt(13, 8).getInitial());
	}
	
	
}
