import java.io.*;

public class HumanPlayer extends Player {

	HumanPlayer(Card[] dealtCards){
		super(dealtCards);
	}
	
	@Override
	Card play(Card[] tableCards) {
		int lowValue = getLowValue();
		int tableValue = getTableValue(tableCards);
		
		// print hand cards to player
		String cardsStr = "";
		for(int i = 0; i < cards.length; i++) if(cards[i] != null){
			cardsStr += cards[i].toString() + " ";
		}
		System.out.println("Cards in your hand: " + cardsStr);
		
		//print table cards for player
		String tableStr = "";
		for(int i = 0; i < tableCards.length; i++){
			tableStr += tableCards[i].toString();
		}
		System.out.println("Cards on the board: " + tableStr);
		
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			// try until player makes valid move
			while(true){
				String line = br.readLine().trim().toLowerCase();
				Card candidate = null;
				int index = 0;
				// check if any card in the hand matches one of the player's card
				for(int i = 0; i < cards.length; i++) if(cards[i] != null){
					if(cards[i].toString().toLowerCase().equals(line)){
						candidate = cards[index = i];
						break;
					}
				}
				// if none matched
				if(candidate == null){
					System.out.println("You do not hold this cards.");
				// if player tried to play ace over ace
				} else if(tableValue == candidate.rankValue() && candidate.rankValue() == Card.aceValue()){
					System.out.println("No ace on ace. Sorry.");
				// if player tried to play lower card, and it is not his lowest card
				} else if(candidate.rankValue() < tableValue && candidate.rankValue() != lowValue){
					System.out.println("Your card must be equal to or higher than the highest table card.");
				} else {
					cards[index] = null;
					return candidate;
				}
			}
		} catch (IOException ioe) {
			System.out.println("IO error!");
		}
	    
		return null;
	}
}
