
public class ComputerPlayer extends Player {

	public ComputerPlayer(Card[] dealtCards) {
		super(dealtCards);
	}
	
	private int cardCount(){
		//if less then 3 cards remain
		int count = 0;
		for(int i = 0; i < cards.length; i++) if(cards[i] != null) ++count;
		return count;
	}

	@Override
	Card play(Card[] tableCards) {
		if(tableCards.length == 0) return lead();
		
		int lowValue = getLowValue();
		int tableValue = getTableValue(tableCards);
		
		int count = cardCount();
				
		// play my lowest higher card
		Card higherCandidate = null;
		if(tableValue < Card.aceValue()) for(int i = 0; i < cards.length; i++) if(cards[i] != null){
			if(cards[i].rankValue() >= tableValue){
				if(higherCandidate == null) higherCandidate = cards[i];
				else {
					//with 3 cards or more play my lowest higher
					if(count >= 4 && higherCandidate.rankValue() > cards[i].rankValue()){
						higherCandidate = cards[i];
					// with 2 cards or less play my highest
					} else if(count < 4 && higherCandidate.rankValue() < cards[i].rankValue()) {
						higherCandidate = cards[i];
					}
				}
			}
		}
		if(higherCandidate != null) {
			for(int i = 0; i < cards.length; i++) if(cards[i] == higherCandidate){
				cards[i] = null;
				return higherCandidate;
			}
		}
		
		// return my smallest card
		for(int i = 0; i < cards.length; i++) if(cards[i] != null && cards[i].rankValue() <= lowValue){
			Card retval = cards[i];
			cards[i] = null;
			return retval;
		}
		return null;
	}
	
	private Card lead(){
		
		Card[] topCards = new Card[4];
		for(int i = 0; i < topCards.length; i++) {
			topCards[i] = null;
		}
		for(int i = 0; i < cards.length; i++) if(cards[i] != null){
			for(int j = 0; j < 4; j++) if(topCards[j] == null || cards[i].rankValue() > topCards[j].rankValue()){
				for(int k = 3; k >= j+1; k--) {
					topCards[k] = topCards[k-1];
				}
				topCards[j] = cards[i];
				break;
			}
		}
		Card retval = null;
		if(topCards[0].rank() == 'A' || cardCount() <= 2){
			retval = topCards[0];
		} else {
			retval = topCards[1];
		}
		for(int i = 0; i < cards.length; i++) if(cards[i] == retval) {
			cards[i] = null;
			return retval;					
		}
		return null;
	}

}
