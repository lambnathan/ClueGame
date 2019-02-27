//Authors: Elliott McCabe and Nathan Lambert
package tests;

import experiment.BoardCell;
import experiment.IntBoard;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;

public class IntBoardTests {
	//HashSetting up IntBoard before testing begins
	IntBoard board;
	@Before
	public void beforeAll() {
		 board = new IntBoard();
	}
	//testing adjacent cells of top left cell are calculated correctly
	@org.junit.Test
	public void testAdjacency0() {
		BoardCell cell = board.getCell(0,0);
		HashSet<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertEquals(2, testList.size());
	}
	//testing adjacent cells of bottom right cell are calculated correctly
	@org.junit.Test
	public void testAdjacency1() {
		BoardCell cell = board.getCell(3,3);
		HashSet<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(2, testList.size());
	}
	//testing adjacent cells of cell (1,1) are calculated correctly
	@org.junit.Test
	public void testAdjacency2() {
		BoardCell cell = board.getCell(1,1);
		HashSet<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(0,1)));
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertEquals(4, testList.size());
	}
	@org.junit.Test
	public void testAdjacency3() {
		BoardCell cell = board.getCell(1,3);
		HashSet<BoardCell> testList = board.getAdjList(cell);
		assertTrue(testList.contains(board.getCell(1,2)));
		assertTrue(testList.contains(board.getCell(0,3)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(3, testList.size());
	}
	
	//testing the list is the correct size and has exactly the right elements.
	@org.junit.Test
	public void testTargets0_3() {
	BoardCell cell = board.getCell(0,0);
	board.calcTargets(cell, 3);
	HashSet<BoardCell> targets = board.getTargets();
	assertEquals(6, targets.size());
	assertTrue(targets.contains(board.getCell(3, 0)));
	assertTrue(targets.contains(board.getCell(2, 1)));
	assertTrue(targets.contains(board.getCell(0, 1)));
	assertTrue(targets.contains(board.getCell(1, 2)));
	assertTrue(targets.contains(board.getCell(0, 3)));
	assertTrue(targets.contains(board.getCell(1, 0)));
	}

}
