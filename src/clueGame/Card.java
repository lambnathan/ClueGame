package clueGame;

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
}
