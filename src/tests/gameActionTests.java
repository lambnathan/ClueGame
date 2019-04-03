package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.DoorDirection;
import clueGame.Player;
import clueGame.Solution;

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
		
		//tests if there are no rooms in a list, than a random cell is selected
		ComputerPlayer p = new ComputerPlayer("test", 25, 3, Color.RED);
		board.calcTargets(25, 3, 6);
		Set<BoardCell> targets = board.getTargets();
		Set<BoardCell> visited = new HashSet<BoardCell>(); //keeps track of locations the computer player chooses. for this point, there are 3 possible locations
		//if it is random, we should see that the size of visisted is greater than 1 (it is choosing different locations)
		for(int i = 0; i < 10; i++) {
			BoardCell pickedLocation = p.pickLocation(targets);
			visited.add(pickedLocation);
		}
		assertTrue(visited.size() > 1);
		
		//tests that computer automatically chooses a doorway if it hasn't been visited
		p = new ComputerPlayer("test", 21, 0, Color.RED); //set location to right outside doorway
		board.calcTargets(21, 0, 3);
		targets = board.getTargets();
		BoardCell pickedLocation = p.pickLocation(targets);
		BoardCell b = new BoardCell(22, 0, 'G', DoorDirection.UP);
		assertTrue(pickedLocation.getRow() == b.getRow() && pickedLocation.getColumn() == b.getColumn());
		p = new ComputerPlayer("test", 19, 8, Color.RED);
		board.calcTargets(19, 8, 3);
		targets = board.getTargets();
		pickedLocation = p.pickLocation(targets);
		b = new BoardCell(19, 7, 'S', DoorDirection.RIGHT);
		assertTrue(pickedLocation.getRow() == b.getRow() && pickedLocation.getColumn() == b.getColumn());
		
		//check that is room was just visited, then a random cell is selected
		p = new ComputerPlayer("test", 19, 8, Color.RED);
		board.calcTargets(19, 8, 1); //only 4 possible targets, including doorway
		targets = board.getTargets();
		visited.clear();
		p.addVisitedRoom('S'); //add the room to the list of already visisted rooms for the computer player
		for(int i = 0; i < 500; i++) { //go through 20 times and it will pick a random location of the 4 possible targets
			pickedLocation = p.pickLocation(targets);
			visited.add(pickedLocation);
		}
		assertEquals(visited.size(), 4); //if it is random, ad because we looped many times, there should be all 4 locations
		
	}
	
	@Test
	public void checkAccusation() {
		Card person = null;
		Card weapon = null;
		Card room = null;
		//Checking solution that is correct
		//Set each card type to the correct answer
		for(Card c : board.getAnswer()) {
			if(c.getCardType() == CardType.ROOM) {
				room = c;
			}
			if(c.getCardType() == CardType.PERSON) {
				person = c;
			}
			if(c.getCardType() == CardType.WEAPON) {
				weapon = c;
			}
		}
		
		//set the accusation to the correct solution
		Solution accusation = new Solution(person, room, weapon);
		//test that our checkAccusation functions works as intended
		assertTrue(board.checkAccusation(accusation));
		
		/*
		 * checking solution with wrong person
		 */
		for(Card c : board.getAnswer()) {
			if(c.getCardType() == CardType.ROOM) {
				room = c;
			}

			if(c.getCardType() == CardType.WEAPON) {
				weapon = c;
			}
			person = null;
		}
		
		//set the accusation to the solution with an intended wrong person
		accusation = new Solution(person, room, weapon);
		//test that our checkAccusation functions works as intended
		assertEquals(board.checkAccusation(accusation), false);
		
		/*
		 * checking solution with wrong room
		 */
		for(Card c : board.getAnswer()) {
			if(c.getCardType() == CardType.PERSON) {
				person = c;
			}

			if(c.getCardType() == CardType.WEAPON) {
				weapon = c;
			}
			room = null;
		}
		//set the accusation to the solution with an intended wrong room
		accusation = new Solution(person, room, weapon);
		//test that our checkAccusation functions works as intended
		assertEquals(board.checkAccusation(accusation), false);
		
		/*
		 * checking solution with wrong weapon
		 */
		for(Card c : board.getAnswer()) {
			if(c.getCardType() == CardType.ROOM) {
				room = c;
			}

			if(c.getCardType() == CardType.PERSON) {
				person = c;
			}
			weapon = null;
		}
		//set the accusation to the solution with an intended wrong person
		accusation = new Solution(person, room, weapon);
		//test that our checkAccusation functions works as intended
		assertEquals(board.checkAccusation(accusation), false);
		
	}
	
	@Test
	public void disproveSuggestion() {
		//computer player within the gun room
		ComputerPlayer p = new ComputerPlayer("test", 22, 0, Color.RED);
		
	}
	
	@Test 
	public void handleSuggestion() {
		
	}
	
	@Test
	public void createSuggestion() {
		
		ComputerPlayer accuser = new ComputerPlayer("Miss Scarlet", 22, 0, Color.RED); //setting player to gun room
		//testing room matches current location
		assertEquals(accuser.createSuggestion(board).getRoomName(), "Gun room");
		
		
		//testing if only one weapon not seen, it's selected
		//add all but one weapon to seen
		Card rope = new Card("Rope", CardType.WEAPON);
		Card pipe = new Card("Lead Pipe", CardType.WEAPON);
		Card knife = new Card("Knife", CardType.WEAPON);
		Card wrench = new Card("Wrench", CardType.WEAPON);
		Card candlestick = new Card("Candlestick", CardType.WEAPON);
		accuser.addCard(rope);
		accuser.addCard(pipe);
		accuser.addCard(knife);
		accuser.addCard(wrench);
		accuser.addCard(candlestick);
		
		accuser.addSeenCard(rope);
		accuser.addSeenCard(pipe);
		accuser.addSeenCard(knife);
		accuser.addSeenCard(wrench);
		accuser.addSeenCard(candlestick);
		
		assertEquals(accuser.createSuggestion(board).getWeaponName(), "Revolver");	
		
		
		//testing for one person not seen, then are selected
	}
}
