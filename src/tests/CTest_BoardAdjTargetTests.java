//Authors: Elliott McCabe and Nathan Lambert
//Passing tests:

package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CTest_BoardAdjTargetTests {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/BoardLayout.csv", "data/ClueRooms.txt", "data/CluePlayers.txt", "data/ClueWeapons.txt");	
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	// Locations within rooms(should have empty adjacency list)
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test top left corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test top right corner
		testList = board.getAdjList(0, 25);
		assertEquals(0, testList.size());
		// Test bottom right corner
		testList = board.getAdjList(25, 0);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(22, 13);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(23, 0);
		assertEquals(0, testList.size());
		// Test one next to a walkway
		testList = board.getAdjList(23, 4);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	// Locations that are doorways(should only have one adjacent cell)
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(5, 1);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(3, 15);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(3, 16)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	// Locations that are adjacent to a doorway with needed directino.
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction RIGHT
		Set<BoardCell> testList = board.getAdjList(4, 16);
		assertTrue(testList.contains(board.getCellAt(3, 16)));
		assertTrue(testList.contains(board.getCellAt(4, 17)));
		assertTrue(testList.contains(board.getCellAt(5, 16)));
		assertTrue(testList.contains(board.getCellAt(4, 15)));
		assertEquals(4, testList.size());
		// Test beside a door direction DOWN
		testList = board.getAdjList(6, 23);
		assertTrue(testList.contains(board.getCellAt(5, 23)));
		assertTrue(testList.contains(board.getCellAt(6, 24)));
		assertTrue(testList.contains(board.getCellAt(7, 23)));
		assertTrue(testList.contains(board.getCellAt(6, 22)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(21, 18);
		assertTrue(testList.contains(board.getCellAt(20, 18)));
		assertTrue(testList.contains(board.getCellAt(21, 19)));
		assertTrue(testList.contains(board.getCellAt(21, 17)));
		assertTrue(testList.contains(board.getCellAt(22, 18)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(17, 13);
		assertTrue(testList.contains(board.getCellAt(16, 13)));
		assertTrue(testList.contains(board.getCellAt(17, 14)));
		assertTrue(testList.contains(board.getCellAt(17, 12)));
		assertTrue(testList.contains(board.getCellAt(18, 13)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test walkway next to ballroom
		Set<BoardCell> testList = board.getAdjList(19, 18);
		assertTrue(testList.contains(board.getCellAt(18, 18)));
		assertTrue(testList.contains(board.getCellAt(20, 18)));
		assertTrue(testList.contains(board.getCellAt(19, 17)));
		assertEquals(3, testList.size());
		
		// Test on bottom edge of board
		testList = board.getAdjList(25, 9);
		assertTrue(testList.contains(board.getCellAt(25, 10)));
		assertTrue(testList.contains(board.getCellAt(25, 8)));
		assertTrue(testList.contains(board.getCellAt(24, 9)));
		assertEquals(3, testList.size());
		
		// Location with only walkways as adjacent locations
		// Test surrounded by 4 walkways
		testList = board.getAdjList(18, 1);
		assertTrue(testList.contains(board.getCellAt(17, 1)));
		assertTrue(testList.contains(board.getCellAt(19, 1)));
		assertTrue(testList.contains(board.getCellAt(18, 0)));
		assertTrue(testList.contains(board.getCellAt(18, 2)));
		assertEquals(4, testList.size());

		// Test left edge of board
		testList = board.getAdjList(7,0);
		assertTrue(testList.contains(board.getCellAt(7, 1)));
		assertTrue(testList.contains(board.getCellAt(6, 0)));
		assertTrue(testList.contains(board.getCellAt(8, 0)));
		assertEquals(3, testList.size());
		
		// Test next to corner piece of room
		testList = board.getAdjList(6, 3);
		assertTrue(testList.contains(board.getCellAt(6, 4)));
		assertTrue(testList.contains(board.getCellAt(6, 2)));
		assertTrue(testList.contains(board.getCellAt(7, 3)));
		assertEquals(3, testList.size());
		
		// Test next to room piece in upper right corner
		testList = board.getAdjList(4, 20);
		assertTrue(testList.contains(board.getCellAt(3, 20)));
		assertTrue(testList.contains(board.getCellAt(4, 19)));
		assertTrue(testList.contains(board.getCellAt(5, 20)));
		assertEquals(3, testList.size());

		// Test next to closet
		testList = board.getAdjList(10, 24);
		assertTrue(testList.contains(board.getCellAt(10, 25)));
		assertTrue(testList.contains(board.getCellAt(11, 24)));
		assertTrue(testList.contains(board.getCellAt(9, 24)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		// Target that allow the user to enter a room.
		//entering doorway case
		board.calcTargets(21, 0, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(22, 0)));
		assertTrue(targets.contains(board.getCellAt(20, 0)));
		assertTrue(targets.contains(board.getCellAt(21, 1)));	
		
		// Targets along walkways at various distances.
		board.calcTargets(4, 18, 1);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(5, 18)));
		assertTrue(targets.contains(board.getCellAt(3, 18)));	
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		assertTrue(targets.contains(board.getCellAt(4, 17)));	

	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet	
	@Test
	public void testTargetsTwoSteps() {
		//entering doorway case
		// Target that allow the user to enter a room
		board.calcTargets(19, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 8)));
		assertTrue(targets.contains(board.getCellAt(18, 9)));
		assertTrue(targets.contains(board.getCellAt(19, 10)));
		assertTrue(targets.contains(board.getCellAt(20, 9)));
		assertTrue(targets.contains(board.getCellAt(21, 8)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));

		// Target along walkway
		board.calcTargets(15, 25, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(13, 25)));
		assertTrue(targets.contains(board.getCellAt(15, 23)));	
		assertTrue(targets.contains(board.getCellAt(14, 24)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		// Target along walkway
		board.calcTargets(15, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 2)));
		assertTrue(targets.contains(board.getCellAt(16, 3)));
		assertTrue(targets.contains(board.getCellAt(15, 4)));
		assertTrue(targets.contains(board.getCellAt(17, 0)));
		assertTrue(targets.contains(board.getCellAt(16, 1)));
		assertTrue(targets.contains(board.getCellAt(15, 2)));
		assertTrue(targets.contains(board.getCellAt(19, 0)));
		assertTrue(targets.contains(board.getCellAt(18, 1)));

		// Target along walkway
		// Includes a path that doesn't have enough length
		board.calcTargets(25, 16, 4);
		targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(24, 17)));
		assertTrue(targets.contains(board.getCellAt(22, 17)));	
		assertTrue(targets.contains(board.getCellAt(25, 18)));	
		assertTrue(targets.contains(board.getCellAt(21, 16)));	
		assertTrue(targets.contains(board.getCellAt(23, 16)));
		assertTrue(targets.contains(board.getCellAt(23, 18)));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		// Target along walkway
		//entering doorway case
		board.calcTargets(25, 3, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(21, 1)));
		assertTrue(targets.contains(board.getCellAt(20, 2)));	
		assertTrue(targets.contains(board.getCellAt(19, 3)));
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// Target that allow the user to enter a room
		// One room is exactly 2 away
		board.calcTargets(4, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly up
		assertTrue(targets.contains(board.getCellAt(2, 5)));
		// directly left/right
		assertTrue(targets.contains(board.getCellAt(3, 6)));
		assertTrue(targets.contains(board.getCellAt(3, 4)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(4, 7)));
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		assertTrue(targets.contains(board.getCellAt(6, 5)));
		assertTrue(targets.contains(board.getCellAt(5, 4)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		// Target along walkway
		board.calcTargets(6, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(4, 5)));
		assertTrue(targets.contains(board.getCellAt(8, 5)));
		// directly right
		assertTrue(targets.contains(board.getCellAt(6, 7)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(7, 6)));
		// right then up
		assertTrue(targets.contains(board.getCellAt(5, 6)));
		// right left
		assertTrue(targets.contains(board.getCellAt(6, 3)));
		// left then up
		assertTrue(targets.contains(board.getCellAt(5, 4)));
		// left then down
		assertTrue(targets.contains(board.getCellAt(7, 4)));		
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Target calculated when leaving a room
		// Take one step, essentially just the adj list
		board.calcTargets(4, 10, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 11)));
		
		// Target calculated when leaving a room
		// Take two steps
		board.calcTargets(4, 10, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 11)));
		assertTrue(targets.contains(board.getCellAt(5, 11)));
		assertTrue(targets.contains(board.getCellAt(4, 12)));
	}

}