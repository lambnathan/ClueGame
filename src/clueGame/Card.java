package clueGame;

public class Card {
	private String cardName;
	private CardType cardType;
	
	public Card(String n) {
		this.cardName = n;
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
}
