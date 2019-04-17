//Authors: Nathan Lambert, Elliott McCabe

package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
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
	public void loadPeople() {
		ArrayList<Player> players = board.getPlayerList();
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
		Set<Card> cards = board.getCardList();
		
		//check if correct total number of cards is loaded
		assertEquals(cards.size(), 18);
		
		int weaponCount = 0;
		int playerCount = 0;
		int roomCount = 0;
		Card room = null;
		Card weapon = null;
		Card player = null;
		for(Card c: cards) {
			if(c.getCardType() == CardType.PERSON) {
				playerCount++;
			}
			else if(c.getCardType() == CardType.ROOM) {
				roomCount++;
			}
			else if(c.getCardType() == CardType.WEAPON) {
				weaponCount++;
			}
			if(c.getCardName().equals("Conservatory")) {
				room = c;
			}
			if(c.getCardName().equals("Wrench")) {
				weapon = c;
			}
			if(c.getCardName().equals("Colonel Mustard")) {
				player = c;
			}
			
			
		}
		
		for(Card c : board.getAnswer()) {
			if(c.getCardType() == CardType.PERSON) {
				playerCount++;
			}
			else if(c.getCardType() == CardType.ROOM) {
				roomCount++;
			}
			else if(c.getCardType() == CardType.WEAPON) {
				weaponCount++;
			}
			if(c.getCardName().equals("Conservatory")) {
				room = c;
			}
			if(c.getCardName().equals("Wrench")) {
				weapon = c;
			}
			if(c.getCardName().equals("Colonel Mustard")) {
				player = c;
			}
		}
		//checks if there is the correct number of player, weapon, and room cards
		assertEquals(weaponCount, 6);
		assertEquals(playerCount, 6);
		assertEquals(roomCount, 9);
		
		//make sure a room is in the deck
		assertEquals(room.getCardName(), "Conservatory");
		
		//make sure weapon is in deck
		assertEquals(weapon.getCardName(), "Wrench");
		
		//make sure player is in deck
		assertEquals(player.getCardName(), "Colonel Mustard");
	}
	
	@Test
	public void dealCards() {
		//checks that all the cards have been dealt
		ArrayList<Player> players = board.getPlayerList();
		Set<Card> cards = board.getCardList();
		int totalCards = 0;
		for(Player p : players) {
			totalCards += p.getPlayerCards().size();
		}
		assertEquals(totalCards, 18);
		
		//make sure each player has roughly the same number of cards
		Player[] playerArr = new Player[players.size()];
		System.arraycopy(players.toArray(), 0, playerArr, 0, players.size());
		for(int i = 0; i < playerArr.length; i++) {
			for(int j = i + 1; j < playerArr.length; j++) {
				assertTrue( Math.abs(playerArr[i].getPlayerCards().size() - playerArr[j].getPlayerCards().size()) < 2);
			}
		}
		
		//makes sure no players have the same card
		for(Card c: cards) {
			int seen = 0;
			for(Player p : players) {
				if(p.getPlayerCards().contains(c)) {
					seen++;
				}
			}
			assertEquals(seen, 1);
		}
	}

}
