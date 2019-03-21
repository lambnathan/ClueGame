package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
	private static Board board;
	
	@BeforeClass
	public void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use our own files
		board.setConfigFiles("data/BoardLayout.csv", "data/ClueRooms.txt", "data/CluePlayers.txt", "data/ClueWeapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void loadPeople() {
		Set<Player> players = board.getPlayerList();
		int humanPlayers = 0;
		Player human = null;
		Player last = null;
		Player middle = null;
		for(Player p : players) {
			if(p instanceof HumanPlayer) {
				humanPlayers++;
				human = p;
			}
			if(p.getPlayerName().equals("Professor Plum")) {
				last = p;
			}
			if(p.getPlayerName().equals("Mr. Green")) {
				middle = p;
			}
			
		}
		//tests if there is only one human player
		assertEquals(humanPlayers, 1);
		
		//tests for correct values for human player, and first in file
		assertEquals(human.getPlayerName(), "Miss Scarlet");
		assertEquals(human.getRow(), 6);
		assertEquals(human.getColumn(), 0);
		assertEquals(human.getColor(), Color.RED);
		
		//tests correct values for last in file
		assertEquals(last.getPlayerName(), "Professor Plum");
		assertEquals(last.getRow(), 20);
		assertEquals(last.getColumn(), 0);
		assertEquals(last.getColor(), Color.PINK);
		
		//tests correct values for player in middle of file
		assertEquals(middle.getPlayerName(), "Mr. Green");
		assertEquals(middle.getRow(), 6);
		assertEquals(middle.getColumn(), 25);
		assertEquals(middle.getColor(), Color.GREEN);
		
	}
	
	@Test
	public void loadCards() {
		
	}
	
	@Test
	public void dealCards() {
		
	}

}
