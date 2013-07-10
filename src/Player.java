
public abstract class Player {
	protected Card[] cards ;
	
	public Player (Card[] dealtCards){
		cards = dealtCards;
		for(int i = 0; i < cards.length; i++) for(int j = i+1; j < cards.length; j++){
			if(cards[i].rankValue() > cards[j].rankValue()){
				Card temp = cards[i];
				cards[i] = cards[j];
				cards[j] = temp;
			}
		}
	}
	
	abstract Card play(Card[] tableCards);
	
	int getLowValue(){
		//calculate lowest card of the player
		int lowValue = -1;
		for(int i = 0; i < cards.length; i++) if(cards[i] != null){
			if(lowValue == -1 || lowValue > cards[i].rankValue()) lowValue = cards[i].rankValue();
		}
		return lowValue;
	}
	
	int getTableValue(Card[] tableCards){
		// calculate current highest card on the table
		int tableValue = -1;
		for(int i = 0; i < tableCards.length; i++) tableValue = Math.max(tableValue, tableCards[i].rankValue());
		return tableValue;			
	}
	
	Card lastCard(){
		for(int i = 0; i < cards.length; i++) if(cards[i] != null) return cards[i];
		return null;
	}
}
