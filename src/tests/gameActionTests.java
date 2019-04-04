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
import clueGame.HumanPlayer;
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
		//testing one singular right answer
		ComputerPlayer accuser = new ComputerPlayer("Miss Scarlet", 22, 0, Color.RED); //setting player to gun room
		
		Card rope = new Card("Rope", CardType.WEAPON);
		Card pipe = new Card("Lead Pipe", CardType.WEAPON);
		Card knife = new Card("Knife", CardType.WEAPON);
		Card wrench = new Card("Wrench", CardType.WEAPON);
		Card candlestick = new Card("Candlestick", CardType.WEAPON);
		Card revolver = new Card("Revolver", CardType.WEAPON);
		
		Card scarlet = new Card("Miss Scarlet", CardType.PERSON);
		Card mustard = new Card("Colonel Mustard", CardType.PERSON);
		Card green = new Card("Mr. Green", CardType.PERSON);
		Card peacock = new Card("Mrs. Peacock", CardType.PERSON);
		Card white = new Card("Mrs. White", CardType.PERSON);
		Card plum = new Card("Professor Plum", CardType.PERSON);
		
		Card conservatory = new Card("Conservatory", CardType.ROOM);
		Card kitchen = new Card("Kitchen", CardType.ROOM);
		Card ballroom = new Card("Ballroom", CardType.ROOM);
		Card library = new Card("Library", CardType.ROOM);
		Card arcade = new Card("Arcade room", CardType.ROOM);
		Card gun = new Card("Gun room", CardType.ROOM);
		Card trophy = new Card("Trophy room", CardType.ROOM);
		Card pantry = new Card("Pantry", CardType.ROOM);
		Card sauna = new Card("Sauna", CardType.ROOM);
		
		board.clearAnswer();
		board.addCardToAnswer(rope);
		board.addCardToAnswer(scarlet);
		board.addCardToAnswer(sauna);
		accuser.clearCards();
		accuser.addCard(rope);
		Solution s = new Solution(green, conservatory, rope);
		assertEquals(accuser.disproveSuggestion(s), rope);
		
		//testing if player has more than one correct card for the solution, then it is randomly chosen which one is supplied
		
		accuser.addCard(sauna);
		int saunaCounter = 0;
		int ropeCounter = 0;
		s = new Solution(green, sauna, rope); 
		for(int i = 0; i < 100; i++) {
			if(accuser.disproveSuggestion(s).equals(sauna)) {
				saunaCounter++;
			}
			else if(accuser.disproveSuggestion(s).equals(rope)) {
				ropeCounter++;
			}
		}
		//if we generate 10 suggestions and it randomly chooses between these two, then we expect 
		//that the number of times they are choosen will be at least greater than 10
		assertTrue(saunaCounter > 10);
		assertTrue(ropeCounter > 10);
		
		//testing if user has no cards, return null
		accuser.clearCards();
		assertEquals(accuser.disproveSuggestion(s), null);
	}	
	
	@Test 
	public void handleSuggestion() {
		ComputerPlayer accuser = null;
		ComputerPlayer otherAccuser = null;
		HumanPlayer human = null;
		
		Card rope = new Card("Rope", CardType.WEAPON);
		Card green = new Card("Mr. Green", CardType.PERSON);
		Card conservatory = new Card("Conservatory", CardType.ROOM);
		
		Card pipe = new Card("Lead Pipe", CardType.WEAPON);
		Card mustard = new Card("Colonel Mustard", CardType.PERSON);
		Card kitchen = new Card("Kitchen", CardType.ROOM);
		
		Card knife = new Card("Knife", CardType.WEAPON);
		Card scarlet = new Card("Miss Scarlet", CardType.PERSON);
		Card ballroom = new Card("Ballroom", CardType.ROOM);
		
		Solution s = new Solution(green, conservatory, rope);
		//clearing hands of all computer players
		int index = 0;
		for(Player p : board.getPlayerList()) {
			if(p instanceof ComputerPlayer && index == 0) {
				accuser = (ComputerPlayer) p;
				accuser.clearCards();
				index++;
			}
			else if(p instanceof ComputerPlayer && index != 0) {
				otherAccuser = (ComputerPlayer) p;
				otherAccuser.clearCards();
			}
			else if(p instanceof HumanPlayer) {
				human = (HumanPlayer) p;
				human.clearCards();
			}
		} 
		
		accuser.addCard(pipe);
		accuser.addCard(mustard);
		accuser.addCard(kitchen);
		
		human.addCard(knife);
		human.addCard(scarlet);
		human.addCard(ballroom);

		
		
		//testing if no one can disprove return null (no one has any cards, so will return null)
		assertEquals(board.handleSuggestion(s, accuser), null);
		
		//testing only accusing player can disprove returns null
		accuser.addCard(green);
		assertEquals(board.handleSuggestion(s, accuser), null);
		
		//testing only human can disprove, so returns answer
		accuser.clearCards();
		accuser.addCard(pipe);
		accuser.addCard(mustard);
		accuser.addCard(kitchen);
		
		human.addCard(knife);
		human.addCard(scarlet);
		human.addCard(ballroom);
		human.addCard(green);
		assertEquals(board.handleSuggestion(s, accuser), green);
		
		//testing only human can disprove, however, human is also accuser, therefore returns null
		accuser.clearCards();
		accuser.addCard(pipe);
		accuser.addCard(mustard);
		accuser.addCard(kitchen);
		
		human.addCard(knife);
		human.addCard(scarlet);
		human.addCard(ballroom);
		human.addCard(green);
		assertEquals(board.handleSuggestion(s, human), null);
		
		//test a suggestion that a human and computer can disprove, but the first player in the list disproves it
		accuser.clearCards();
		human.clearCards();
		accuser.addCard(green);
		human.addCard(conservatory);
		assertEquals(board.handleSuggestion(s, otherAccuser), green);
		
		//test a suggestion that two computer player can disprove, and the first in the list disporves it
		accuser.clearCards();
		human.clearCards();
		otherAccuser.clearCards();
		accuser.addCard(green);
		otherAccuser.addCard(conservatory);
		assertEquals(board.handleSuggestion(s, human), conservatory);
	}
	
	@Test
	public void createSuggestion() {
		
		ComputerPlayer accuser = new ComputerPlayer("Miss Scarlet", 22, 0, Color.RED); //setting player to gun room
		//testing room matches current location
		assertEquals(accuser.createSuggestion(board).getRoomName(), "Gun room");
		
		Card rope = new Card("Rope", CardType.WEAPON);
		Card pipe = new Card("Lead Pipe", CardType.WEAPON);
		Card knife = new Card("Knife", CardType.WEAPON);
		Card wrench = new Card("Wrench", CardType.WEAPON);
		Card candlestick = new Card("Candlestick", CardType.WEAPON);
		Card revolver = new Card("Revolver", CardType.WEAPON);
		
		Card scarlet = new Card("Miss Scarlet", CardType.PERSON);
		Card mustard = new Card("Colonel Mustard", CardType.PERSON);
		Card green = new Card("Mr. Green", CardType.PERSON);
		Card peacock = new Card("Mrs. Peacock", CardType.PERSON);
		Card white = new Card("Mrs. White", CardType.PERSON);
		Card plum = new Card("Professor Plum", CardType.PERSON);
		
		
		
		//testing if only one weapon not seen, it's selected
		//add all but one weapon to seen
		accuser.clearCards();
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
		
		board.addCardToDeck(revolver);//ensures that the board still has the diesired card (important for createSuggestion method)
		
		assertEquals(accuser.createSuggestion(board).getWeaponName(), "Revolver");	
		
		
		//testing for one person not seen, then are selected
		accuser.clearCards();
		accuser.addCard(scarlet);
		accuser.addCard(mustard);
		accuser.addCard(green);
		accuser.addCard(peacock);
		accuser.addCard(white);
		
		accuser.addSeenCard(scarlet);
		accuser.addSeenCard(mustard);
		accuser.addSeenCard(green);
		accuser.addSeenCard(peacock);
		accuser.addSeenCard(white);
		
		board.addCardToDeck(plum);
		
		assertEquals(accuser.createSuggestion(board).getPersonName(), "Professor Plum");
		
		//test if multple weapons are not seen, then 1 is randomly selected
		//will ramdomly choose between candlestick and revolver
		accuser.clearCards();
		accuser.addCard(rope);
		accuser.addCard(pipe);
		accuser.addCard(knife);
		accuser.addCard(wrench);
		
		accuser.addSeenCard(rope);
		accuser.addSeenCard(pipe);
		accuser.addSeenCard(knife);
		accuser.addSeenCard(wrench);
		
		board.addCardToDeck(revolver);
		board.addCardToDeck(candlestick);
		int candleCounter = 0;
		int revolverCounter = 0;
		for(int i = 0; i < 100; i++) {
			if(accuser.createSuggestion(board).getWeaponName().equals("Revolver")) {
				revolverCounter++;
			}
			else if(accuser.createSuggestion(board).getWeaponName().equals("Candlestick")) {
				candleCounter++;
			}
		}
		//if we generate 10 suggestions and it randomly chooses between these two, then we expect 
		//that the number of times they are choosen will be at least greater than 10
		assertTrue(revolverCounter > 10);
		assertTrue(candleCounter > 10);
		
		//test if multiple people are not seen, then 1 is randomly choosed
		//will randomly chosoe between plum and white
		accuser.clearCards();
		accuser.addCard(scarlet);
		accuser.addCard(mustard);
		accuser.addCard(green);
		accuser.addCard(peacock);
		
		accuser.addSeenCard(scarlet);
		accuser.addSeenCard(mustard);
		accuser.addSeenCard(green);
		accuser.addSeenCard(peacock);
		
		board.addCardToDeck(plum);
		board.addCardToDeck(white);
		int plumCounter = 0;
		int whiteCounter = 0;
		for(int i = 0; i < 100; i++) {
			if(accuser.createSuggestion(board).getPersonName().equals("Professor Plum")) {
				plumCounter++;
			}
			else if(accuser.createSuggestion(board).getPersonName().equals("Mrs. White")) {
				whiteCounter++;
			}
		}
		//if we generate 10 suggestions and it randomly chooses between these two, then we expect 
		//that the number of times they are choosen will be at least greater than 10
		assertTrue(plumCounter > 10);
		assertTrue(whiteCounter > 10);
	}
}
