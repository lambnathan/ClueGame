//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.util.Objects;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String n, CardType cardType) {
		this.cardName = n;
		this.cardType = cardType;
	}
	
	public boolean equals() {
		
		return false;
	}
	
	public CardType getCardType() {
		return cardType;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Card)) {
			return false;
		}
		else {
			Card c = (Card) o;
			return c.getCardName().equals(this.getCardName());
		}
	}
	
	@Override
    public int hashCode() {
        return Objects.hash(cardName);
    }
	
}
