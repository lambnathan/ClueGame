//Authors: Elliott McCabe and Nathan Lambert
//Failing tests:

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

public class CTest_BoardAdjTargetTests_Failing {
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
		assertTrue(testList.contains(board.getCellAt(0, 5)));
		assertEquals(1, testList.size());
		
		// Test on bottom edge of board
		testList = board.getAdjList(25, 9);
		assertTrue(testList.contains(board.getCellAt(5, 0)));
		assertTrue(testList.contains(board.getCellAt(6, 1)));
		assertTrue(testList.contains(board.getCellAt(7, 0)));
		assertEquals(3, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(18, 1);
		assertTrue(testList.contains(board.getCellAt(6, 20)));
		assertTrue(testList.contains(board.getCellAt(6, 22)));
		assertEquals(2, testList.size());

		// Test left edge of board
		testList = board.getAdjList(9,0);
		assertTrue(testList.contains(board.getCellAt(15, 8)));
		assertTrue(testList.contains(board.getCellAt(15, 6)));
		assertTrue(testList.contains(board.getCellAt(14, 7)));
		assertTrue(testList.contains(board.getCellAt(16, 7)));
		assertEquals(4, testList.size());
		
		// Test next to corner piece of room
		testList = board.getAdjList(6, 3);
		assertTrue(testList.contains(board.getCellAt(21, 16)));
		assertTrue(testList.contains(board.getCellAt(20, 15)));
		assertEquals(2, testList.size());
		
		// Test next to room piece in upper right corner
		testList = board.getAdjList(4, 20);
		assertTrue(testList.contains(board.getCellAt(14, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 22)));
		assertEquals(2, testList.size());

		// Test next to closet
		testList = board.getAdjList(10, 24);
		assertTrue(testList.contains(board.getCellAt(5, 2)));
		assertTrue(testList.contains(board.getCellAt(5, 4)));
		assertTrue(testList.contains(board.getCellAt(6, 3)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		//entering doorway case
		board.calcTargets(21, 0, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(20, 7)));
		assertTrue(targets.contains(board.getCellAt(21, 6)));	
		
		board.calcTargets(4, 18, 1);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 1)));
		assertTrue(targets.contains(board.getCellAt(13, 0)));	
		assertTrue(targets.contains(board.getCellAt(15, 0)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		//entering doorway case
		board.calcTargets(19, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		
		board.calcTargets(17, 4, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(12, 0)));
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));			
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(6, 5, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 7)));
		assertTrue(targets.contains(board.getCellAt(19, 7)));
		assertTrue(targets.contains(board.getCellAt(18, 6)));
		assertTrue(targets.contains(board.getCellAt(20, 6)));
		
		// Includes a path that doesn't have enough length
		board.calcTargets(0, 12, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 4)));
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		//entering doorway case
		board.calcTargets(16, 11, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(15, 5)));	
		assertTrue(targets.contains(board.getCellAt(15, 3)));	
		assertTrue(targets.contains(board.getCellAt(14, 4)));	
		assertTrue(targets.contains(board.getCellAt(15, 1)));	
		assertTrue(targets.contains(board.getCellAt(14, 2)));	
		assertTrue(targets.contains(board.getCellAt(13, 4)));	
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(4, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(7, targets.size());
		// directly left (can't go right 2 steps)
		assertTrue(targets.contains(board.getCellAt(17, 14)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 16)));
		assertTrue(targets.contains(board.getCellAt(19, 16)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(18, 15)));
		assertTrue(targets.contains(board.getCellAt(16, 17)));
		assertTrue(targets.contains(board.getCellAt(16, 15)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(6, 5, 5);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(12, targets.size());
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(15, 7)));
		assertTrue(targets.contains(board.getCellAt(9, 7)));
		// directly right (can't go left)
		assertTrue(targets.contains(board.getCellAt(12, 10)));
		// right then down
		assertTrue(targets.contains(board.getCellAt(13, 9)));
		assertTrue(targets.contains(board.getCellAt(13, 7)));
		// down then left/right
		assertTrue(targets.contains(board.getCellAt(14, 6)));
		assertTrue(targets.contains(board.getCellAt(14, 8)));
		// right then up
		assertTrue(targets.contains(board.getCellAt(10, 8)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(11, 6)));
		assertTrue(targets.contains(board.getCellAt(10, 6)));		
		// 
		assertTrue(targets.contains(board.getCellAt(11, 7)));		
		assertTrue(targets.contains(board.getCellAt(12, 8)));		
		
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(4, 10, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(4, 19)));
		// Take two steps
		board.calcTargets(4, 10, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(3, 19)));
		assertTrue(targets.contains(board.getCellAt(5, 19)));
		assertTrue(targets.contains(board.getCellAt(4, 18)));
	}

}
