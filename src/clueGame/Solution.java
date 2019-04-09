//Authors: Nathan Lambert, Elliott McCabe

package clueGame;


/*
 * class that can represent an accusation or a suggestion for the correct solution
 * contains a card for each type (player, weapon, room)
 */
public class Solution {
	public Card person;
	public Card room;
	public Card weapon;
	
	public Solution(Card person, Card room, Card weapon) {
		super();
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}

	public Card getPerson() {
		return person;
	}

	public Card getRoom() {
		return room;
	}

	public Card getWeapon() {
		return weapon;
	}
	public String getPersonName() {
		return person.getCardName();
	}

	public String getRoomName() {
		return room.getCardName();
	}

	public String getWeaponName() {
		return weapon.getCardName();
	}
	
	
}
